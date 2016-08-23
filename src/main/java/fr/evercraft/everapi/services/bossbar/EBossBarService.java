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
package fr.evercraft.everapi.services.bossbar;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.BossBarService;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.bossbar.event.EAddBossBarEvent;
import fr.evercraft.everapi.services.bossbar.event.ERemoveBossBarEvent;
import fr.evercraft.everapi.services.bossbar.event.EReplaceBossBarEvent;

public class EBossBarService implements BossBarService {	
	private final EverAPI plugin;
		
	private final ConcurrentMap<UUID, EBossBar> players;
	
	public EBossBarService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.players = new ConcurrentHashMap<UUID, EBossBar>();
	}
	
	public void reload() {
		HashMap<UUID, EBossBar> bossbars = new HashMap<UUID, EBossBar>(this.players);
		this.players.clear();
		
		for (Entry<UUID, EBossBar> bossbar : bossbars.entrySet()) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(bossbar.getKey());
			if (player.isPresent()) {
				bossbar.getValue().removePlayer(player.get());
				this.postRemove(player.get(), bossbar.getValue());
			}
		}
	}
	
	
	
	@Override
	public boolean add(EPlayer player, String identifier, ServerBossBar bossbar) {
		return this.add(player, identifier, this.getPriority(identifier), bossbar);
	}
	
	@Override
	public boolean add(EPlayer player, String identifier, int priority, ServerBossBar bossbar) {
		EBossBar bossbar_player = this.players.get(player.getUniqueId());
		// Vérifie la priorité
		if (bossbar_player == null) {
			// Ajoute
			bossbar_player = new EBossBar(identifier, bossbar);
			this.players.put(player.getUniqueId(), bossbar_player);
			bossbar.addPlayer(player.get());
			
			//Event
			this.postAdd(player, bossbar_player);
			
			return true;
		} else if (this.getPriority(bossbar_player.getIdentifier()) <= priority && !bossbar_player.getServerBossBar().equals(bossbar)) {
			// Supprime
			bossbar_player.getServerBossBar().removePlayer(player.get());
			
			// Ajoute
			EBossBar new_bossbar_player = new EBossBar(identifier, bossbar);
			this.players.put(player.getUniqueId(), new_bossbar_player);
			bossbar.addPlayer(player.get());
			
			//Event
			this.postReplace(player, bossbar_player, new_bossbar_player);
			
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(EPlayer player, String identifier) {
		EBossBar bossbar = this.players.get(player.getUniqueId());
		if (bossbar != null && bossbar.getIdentifier().equalsIgnoreCase(identifier)) {
			// Supprime
			bossbar.getServerBossBar().removePlayer(player.get());
			this.players.remove(player.getUniqueId());
			
			//Event
			this.postRemove(player, bossbar);
			return true;
		}
		return false;
	}
	
	private int getPriority(String identifier) {
		if (this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getBossBar(identifier);
		}
		return PriorityService.DEFAULT;
	}

	@Override
	public Optional<ServerBossBar> get(EPlayer player, String identifier) {
		EBossBar bossbar = this.players.get(player.getUniqueId());
		if (bossbar != null && bossbar.getIdentifier().equalsIgnoreCase(identifier)) {
			return Optional.of(bossbar.getServerBossBar());
		}
		return Optional.empty();
	}
	
	/*
	 * Event
	 */
	
	private void postAdd(EPlayer player, EBossBar bossbar) {
		this.plugin.getLogger().debug("Event BossBarEvent.Add : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "boosbar='" + bossbar.getServerBossBar().getName().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(new EAddBossBarEvent(player, bossbar, Cause.source(this.plugin).build()));
	}
	
	private void postRemove(EPlayer player, EBossBar bossbar) {
		this.plugin.getLogger().debug("Event BossBarEvent.Remove : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "boosbar='" + bossbar.getServerBossBar().getName().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(new ERemoveBossBarEvent(player, bossbar, Cause.source(this.plugin).build()));
	}
	
	private void postReplace(EPlayer player, EBossBar bossbar, EBossBar new_bossbar) {
		this.plugin.getLogger().debug("Event BossBarEvent.Replace : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "boosbar='" + bossbar.getServerBossBar().getName().toPlain() + "';"
				+ "new_boosbar='" + new_bossbar.getServerBossBar().getName().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(new EReplaceBossBarEvent(player, bossbar, new_bossbar, Cause.source(this.plugin).build()));
	}
}
