/**
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
package fr.evercraft.everapi.services.tablist;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.entity.living.player.Player;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.priority.PriorityService;
import fr.evercraft.everapi.services.tablist.event.TabListEvent;
import fr.evercraft.everapi.services.tablist.event.TabListEvent.Action;

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
			Optional<Player> player = this.plugin.getGame().getServer().getPlayer(nameTag.getKey());
			if(player.isPresent()) {
				player.get().getTabList().setHeaderAndFooter(null, null);
				this.plugin.getGame().getEventManager().post(new TabListEvent(this.plugin, player.get(), nameTag.getValue(), Action.REMOVE));
			}
		}
	}
	
	@Override
	public boolean sendTabList(Player player, String identifier) {
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
				this.plugin.getGame().getEventManager().post(new TabListEvent(this.plugin, player, player_identifier, Action.REPLACE));
				
				// Ajoute
				this.tablist.putIfAbsent(player.getUniqueId(), identifier);
				this.plugin.getGame().getEventManager().post(new TabListEvent(this.plugin, player, identifier, Action.ADD));
				
				return true;
			}
		// Aucun TabList
		} else {
			// Ajoute
			this.tablist.putIfAbsent(player.getUniqueId(), identifier);
			this.plugin.getGame().getEventManager().post(new TabListEvent(this.plugin, player, identifier, Action.ADD));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeTabList(Player player, String identifier) {
		if(this.tablist.containsKey(player.getUniqueId()) && this.tablist.get(player.getUniqueId()).equalsIgnoreCase(identifier)) {
			player.getTabList().setHeaderAndFooter(null, null);
			this.plugin.getGame().getEventManager().post(new TabListEvent(this.plugin, player, identifier, Action.REMOVE));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean has(final UUID uuid) {
		return this.tablist.containsKey(uuid);
	}

	@Override
	public Optional<String> get(final UUID uuid) {
		return Optional.ofNullable(this.tablist.get(uuid));
	}

	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getNameTag(identifier);
		}
		return PriorityService.DEFAULT;
	}
}
