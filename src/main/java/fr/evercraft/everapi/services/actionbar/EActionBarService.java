/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.services.actionbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
import fr.evercraft.everapi.services.actionbar.event.EAddActionBarEvent;
import fr.evercraft.everapi.services.actionbar.event.ERemoveActionBarEvent;
import fr.evercraft.everapi.services.actionbar.event.EReplaceActionBarEvent;

public class EActionBarService implements ActionBarService {
	private final static int UPDATE = 1000;
	private final static int LAST_UPDATE = 2000;
	
	private final EverAPI plugin;
	
	private Task task;
	private final ConcurrentMap<UUID, ActionBarMessage> players;
	
	public EActionBarService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.players = new ConcurrentHashMap<UUID, ActionBarMessage>();
	}
	
	public void reload() {
		if(this.task != null) {
			this.task.cancel();
		}
		
		HashMap<UUID, ActionBarMessage> actionBars = new HashMap<UUID, ActionBarMessage>(this.players);
		this.players.clear();
		
		for(Entry<UUID, ActionBarMessage> actionBar : actionBars.entrySet()) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(actionBar.getKey());
			if(player.isPresent()) {
				player.get().sendMessage(ChatTypes.ACTION_BAR, Text.EMPTY);
				
				// Event
				this.postRemove(player.get(), actionBar.getValue());
			}
		}
	}
	
	@Override
	public boolean send(EPlayer player, String identifier, long stay, Text message) {
		return this.send(player, identifier, this.getPriority(identifier), stay, message);
	}
	
	@Override
	public boolean send(EPlayer player, String identifier, int priority, long stay, Text message) {
		ActionBarMessage actionBar = this.players.get(player.getUniqueId());
		// Vérifie la priorité
		if(actionBar == null || this.getPriority(actionBar.getIdentifier()) <= priority) {
			ActionBarMessage newActionBar = new ActionBarMessage(player.getUniqueId(), identifier, System.currentTimeMillis() + stay, message);
			// Envoie la ActionBar
			newActionBar.send(player);
			// On ajoute la nouveau ActionBar
			this.players.put(player.getUniqueId(), newActionBar);
			
			// Event
			if(actionBar == null) {
				this.postAdd(player, newActionBar);
			} else {
				this.postReplace(player, actionBar, newActionBar);
			}
			
			// On réactive le cooldown
			start();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(EPlayer player, String identifier) {
		ActionBarMessage actionBar = this.players.get(player.getUniqueId());
		if(actionBar != null && actionBar.getIdentifier().equalsIgnoreCase(identifier)) {
			// Supprime
			player.get().sendMessage(ChatTypes.ACTION_BAR, Text.EMPTY);
			this.players.remove(player.getUniqueId());
			
			//Event
			this.postRemove(player, actionBar);
			return true;
		}
		return false;
	}
	
	public void update() {
		if(this.players.isEmpty()) {
			stop();
		} else {
			final List<UUID> removes = new ArrayList<UUID>();
			for(ActionBarMessage actionBar : this.players.values()) {
				Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(actionBar.getPlayer());
				if(player.isPresent() && System.currentTimeMillis() + LAST_UPDATE <= actionBar.getTime()) {
					actionBar.send(player.get());
				} else {
					removes.add(actionBar.getPlayer());
				}
			}
			
			if(!removes.isEmpty()) {
				this.plugin.getGame().getScheduler().createTaskBuilder()
					.execute(() -> this.updateSync(removes))
					.name("ActionBarService")
					.submit(this.plugin);
			}
		}
	}
	
	public void updateSync(List<UUID> players) {
		for(UUID uuid : players) {
			ActionBarMessage actionBar = this.players.get(uuid);
			if(actionBar != null && System.currentTimeMillis() + LAST_UPDATE > actionBar.getTime()) {
				this.players.remove(uuid);
				
				Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
				if(player.isPresent()) {
					player.get().sendMessage(ChatTypes.ACTION_BAR, Text.EMPTY);
					
					//Event
					this.postRemove(player.get(), actionBar);
				}
			}
		}
		
		
		if(this.players.isEmpty()) {
			stop();
		} else {
			start();
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
		return this.players.containsKey(uuid);
	}

	@Override
	public Optional<ActionBarMessage> get(UUID uuid) {
		return Optional.ofNullable(this.players.get(uuid));
	}
	
	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getActionBar(identifier);
		}
		return PriorityService.DEFAULT;
	}
	
	/*
	 * Event
	 */
	
	private void postAdd(EPlayer player, ActionBarMessage actionbar) {
		this.plugin.getLogger().debug("Event ActionBarEvent.Add : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "actionbar='" + actionbar.getMessage().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(new EAddActionBarEvent(player, actionbar, Cause.source(this.plugin).build()));
	}
	
	private void postRemove(EPlayer player, ActionBarMessage actionbar) {
		this.plugin.getLogger().debug("Event ActionBarEvent.Remove : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "actionbar='" + actionbar.getMessage().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(new ERemoveActionBarEvent(player, actionbar, Cause.source(this.plugin).build()));
	}
	
	private void postReplace(EPlayer player, ActionBarMessage actionbar, ActionBarMessage new_actionbar) {
		this.plugin.getLogger().debug("Event ActionBarEvent.Replace : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "actionbar='" + actionbar.getMessage().toPlain() + "';"
				+ "new_actionbar='" + new_actionbar.getMessage().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(new EReplaceActionBarEvent(player, actionbar, new_actionbar, Cause.source(this.plugin).build()));
	}
}
