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
package fr.evercraft.everapi.services.tablist;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.entity.living.player.tab.TabListEntry;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.ESpongeEventFactory;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.TabListService;

public class ETabListService implements TabListService {
	
	private final EverAPI plugin;
	
	private final ConcurrentMap<UUID, String> players;
	
	public ETabListService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.players = new ConcurrentHashMap<UUID, String>();
	}
	
	public void reload() {
		HashMap<UUID, String> tablists = new HashMap<UUID, String>(this.players);
		this.players.clear();
		
		for (Entry<UUID, String> tablist : tablists.entrySet()) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(tablist.getKey());
			if (player.isPresent()) {
				player.get().getTabList().setHeaderAndFooter(null, null);
				
				// Event
				this.postRemove(player.get(), tablist.getValue());
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
		if (this.players.containsKey(player.getUniqueId())) {
			String player_identifier = this.players.get(player.getUniqueId());
			// Egale
			if (player_identifier.equalsIgnoreCase(identifier)) {
				return true;
			// Différent mais inférieur
			} else if (this.getPriority(player_identifier) <= this.getPriority(identifier)) {
				// Supprime
				player.getTabList().setHeaderAndFooter(null, null);
				
				// Ajoute
				this.players.putIfAbsent(player.getUniqueId(), identifier);
				
				// Event
				this.postReplace(player, player_identifier, identifier);
				
				return true;
			}
		// Aucun TabList
		} else {
			// Ajoute
			this.players.putIfAbsent(player.getUniqueId(), identifier);
			
			// Event
			this.postAdd(player, identifier);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeTabList(EPlayer player, String identifier) {
		if (this.players.containsKey(player.getUniqueId()) && this.players.get(player.getUniqueId()).equalsIgnoreCase(identifier)) {
			TabList tablist = player.getTabList();
			
			tablist.setHeaderAndFooter(null, null);
			tablist.getEntries().stream()
				.map(entry -> entry.getProfile().getUniqueId())
				.collect(Collectors.toList())
				.forEach(entry -> tablist.removeEntry(entry));
			
			for (EPlayer other : this.plugin.getEServer().getOnlineEPlayers()) {
				tablist.addEntry(TabListEntry.builder()
						.profile(other.getProfile())
						.gameMode(other.getGameMode())
						.list(tablist)
						.build());
			}
			
			// Event
			this.postRemove(player, identifier);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean has(final UUID uuid) {
		return this.players.containsKey(uuid);
	}
	
	@Override
	public boolean hasTabList(EPlayer player, String identifier) {
		String player_identifier = this.players.get(player.getUniqueId());
		return player_identifier != null && player_identifier.equalsIgnoreCase(identifier);
	}

	@Override
	public Optional<String> get(final UUID uuid) {
		return Optional.ofNullable(this.players.get(uuid));
	}

	private int getPriority(String identifier) {
		if (this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getTabList(identifier);
		}
		return PriorityService.DEFAULT;
	}
	
	/*
	 * Event
	 */
	
	private void postAdd(EPlayer player, String identifier) {
		this.plugin.getELogger().debug("Event TabListEvent.Add : ("
				+ "uuid='" + player.getUniqueId() + "';"
				+ "tablist='" + identifier + "')");
		this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createTabListEventAdd(player, identifier, Cause.source(this.plugin).build()));
	}
	
	private void postRemove(EPlayer player, String identifier) {
		this.plugin.getELogger().debug("Event TabListEvent.Remove : ("
				+ "uuid='" + player.getUniqueId() + "';"
				+ "tablist='" + identifier + "')");
		this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createTabListEventRemove(player, identifier, Cause.source(this.plugin).build()));
	}
	
	private void postReplace(EPlayer player, String identifier, String new_identifier) {
		this.plugin.getELogger().debug("Event TabListEvent.Replace : ("
				+ "uuid='" + player.getUniqueId() + "';"
				+ "tablist='" + identifier + "';"
				+ "new_tablist='" + new_identifier + "')");
		this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createTabListEventReplace(player, identifier, new_identifier, Cause.source(this.plugin).build()));
	}
}
