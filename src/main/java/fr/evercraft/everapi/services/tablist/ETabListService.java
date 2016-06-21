package fr.evercraft.everapi.services.tablist;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.TabListService;
import fr.evercraft.everapi.services.tablist.event.EAddTabListEvent;
import fr.evercraft.everapi.services.tablist.event.ERemoveTabListEvent;
import fr.evercraft.everapi.services.tablist.event.EReplaceTabListEvent;

public class ETabListService implements TabListService {
	
	private final EverAPI plugin;
	
	private final ConcurrentMap<UUID, String> tablist;
	
	public ETabListService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.tablist = new ConcurrentHashMap<UUID, String>();
	}
	
	public void reload() {
		Set<Entry<UUID, String>> nameTags = this.tablist.entrySet();
		this.tablist.clear();
		
		for(Entry<UUID, String> nameTag : nameTags) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(nameTag.getKey());
			if(player.isPresent()) {
				player.get().getTabList().setHeaderAndFooter(null, null);
				this.plugin.getGame().getEventManager().post(new ERemoveTabListEvent(player.get(), nameTag.getValue(), Cause.source(this.plugin).build()));
			}
		}
	}
	
	@Override
	public boolean sendTabList(EPlayer player, String identifier) {
		return this.sendTabList(player, identifier, this.getPriority(identifier));
	}
	
	@Override
	public boolean sendTabList(EPlayer player, String identifier, int priority) {
		// Avec un TabList
		if(this.tablist.containsKey(player.getUniqueId())) {
			String player_identifier = this.tablist.get(player.getUniqueId());
			// Egale
			if(player_identifier.equalsIgnoreCase(identifier)) {
				return true;
			// Différent mais inférieur
			} else if(this.getPriority(player_identifier) <= this.getPriority(identifier)) {
				// Supprime
				player.getTabList().setHeaderAndFooter(null, null);
				
				// Ajoute
				this.tablist.putIfAbsent(player.getUniqueId(), identifier);
				
				// Event
				this.plugin.getGame().getEventManager().post(new EReplaceTabListEvent(player, player_identifier, identifier, Cause.source(this.plugin).build()));
				
				return true;
			}
		// Aucun TabList
		} else {
			// Ajoute
			this.tablist.putIfAbsent(player.getUniqueId(), identifier);
			this.plugin.getGame().getEventManager().post(new EAddTabListEvent(player, identifier, Cause.source(this.plugin).build()));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeTabList(EPlayer player, String identifier) {
		if(this.tablist.containsKey(player.getUniqueId()) && this.tablist.get(player.getUniqueId()).equalsIgnoreCase(identifier)) {
			player.getTabList().setHeaderAndFooter(null, null);
			this.plugin.getGame().getEventManager().post(new ERemoveTabListEvent(player, identifier, Cause.source(this.plugin).build()));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean has(final UUID uuid) {
		return this.tablist.containsKey(uuid);
	}
	
	@Override
	public boolean hasTabList(EPlayer player, String identifier) {
		String player_identifier = this.tablist.get(player.getUniqueId());
		return player_identifier != null && player_identifier.equalsIgnoreCase(identifier);
	}

	@Override
	public Optional<String> get(final UUID uuid) {
		return Optional.ofNullable(this.tablist.get(uuid));
	}

	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getTabList(identifier);
		}
		return PriorityService.DEFAULT;
	}
}
