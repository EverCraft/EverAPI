package fr.evercraft.everapi.services.actionbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.ActionBarService;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.actionbar.event.EActionBarEvent;
import fr.evercraft.everapi.services.actionbar.event.EAddActionBarEvent;
import fr.evercraft.everapi.services.actionbar.event.ERemoveActionBarEvent;
import fr.evercraft.everapi.services.actionbar.event.EReplaceActionBarEvent;

public class EActionBarService implements ActionBarService {
	private final static int UPDATE = 1000;
	private final static int LAST_UPDATE = 2000;
	
	private final EverAPI plugin;
	
	private Task task;
	private final ConcurrentMap<UUID, ActionBarMessage> actionBars;
	
	public EActionBarService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.actionBars = new ConcurrentHashMap<UUID, ActionBarMessage>();
	}
	
	public void reload() {
		if(this.task != null) {
			this.task.cancel();
		}
		
		Set<Entry<UUID, ActionBarMessage>> actionBars = this.actionBars.entrySet();
		this.actionBars.clear();
		
		for(Entry<UUID, ActionBarMessage> actionBar : actionBars) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(actionBar.getKey());
			if(player.isPresent()) {
				player.get().sendMessage(ChatTypes.ACTION_BAR, Text.EMPTY);
				this.plugin.getGame().getEventManager().post(new ERemoveActionBarEvent(player.get(), actionBar.getValue(), Cause.source(this.plugin).build()));
			}
		}
	}
	
	@Override
	public boolean send(EPlayer player, String identifier, long stay, Text message) {
		return this.send(player, identifier, this.getPriority(identifier), stay, message);
	}
	
	@Override
	public boolean send(EPlayer player, String identifier, int priority, long stay, Text message) {
		ActionBarMessage actionBar = this.actionBars.get(player.getUniqueId());
		// Vérifie la priorité
		if(actionBar == null || this.getPriority(actionBar.getIdentifier()) <= priority) {
			ActionBarMessage newActionBar = new ActionBarMessage(player.getUniqueId(), identifier, System.currentTimeMillis() + stay, message);
			// Si l'ActionBar fonctionne
			if(newActionBar.send(player)) {
				// On ajoute la nouveau ActionBar
				this.actionBars.put(player.getUniqueId(), newActionBar);
				
				// Event
				if(actionBar != null) {
					this.plugin.getGame().getEventManager().post(new EReplaceActionBarEvent(player, actionBar, newActionBar, Cause.source(this.plugin).build()));
				} else {
					this.plugin.getGame().getEventManager().post(new EAddActionBarEvent(player, newActionBar, Cause.source(this.plugin).build()));
				}
				
				// On réactive le cooldown
				start();
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		if(this.actionBars.isEmpty()) {
			stop();
		} else {
			List<EActionBarEvent> events = new ArrayList<EActionBarEvent>();
			List<UUID> removes = new ArrayList<UUID>();
			for(ActionBarMessage actionBar : this.actionBars.values()) {
				Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(actionBar.getPlayer());
				if(player.isPresent()) {
					if(System.currentTimeMillis() + LAST_UPDATE > actionBar.getTime() || !actionBar.send(player.get())) {
						events.add(new ERemoveActionBarEvent(player.get(), actionBar, Cause.source(this.plugin).build()));
					}
				} else {
					removes.add(actionBar.getPlayer());
				}
			}
			
			for(EActionBarEvent event : events) {
				this.actionBars.remove(event.getPlayer().getUniqueId());
				this.plugin.getGame().getEventManager().post(event);
			}
			
			for(UUID remove : removes) {
				this.actionBars.remove(remove);
			}
			
			if(this.actionBars.isEmpty()) {
				stop();
			} else {
				start();
			}
		}
	}
	
	public void start() {
		if(!isEnable()) {
			this.task = this.plugin.getGame().getScheduler().createTaskBuilder()
							.execute(() -> this.update())
							.delay(UPDATE, TimeUnit.MILLISECONDS)
							.interval(UPDATE, TimeUnit.MILLISECONDS)
							.async()
							.name("ActionBarService")
							.submit(this.plugin);
		}
	}
	
	public void stop() {
		if(isEnable()) {
			this.task.cancel();
			this.task = null;
		}
	}

	public boolean isEnable() {
		return this.task != null;
	}

	@Override
	public boolean has(UUID uuid) {
		return this.actionBars.containsKey(uuid);
	}

	@Override
	public Optional<ActionBarMessage> get(UUID uuid) {
		return Optional.ofNullable(this.actionBars.get(uuid));
	}
	
	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getActionBar(identifier);
		}
		return PriorityService.DEFAULT;
	}
}
