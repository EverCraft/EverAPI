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
package fr.evercraft.everapi.services.nametag;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.Visibilities;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.NameTagService;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.nametag.event.EAddNameTagEvent;
import fr.evercraft.everapi.services.nametag.event.ERemoveNameTagEvent;
import fr.evercraft.everapi.services.nametag.event.EReplaceNameTagEvent;

public class ENameTagService implements NameTagService {
	
	private final EverAPI plugin;
	
	private final ConcurrentMap<UUID, String> nameTags;
	
	public ENameTagService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.nameTags = new ConcurrentHashMap<UUID, String>();
	}
	
	public void reload() {
		Set<Entry<UUID, String>> nameTags = this.nameTags.entrySet();
		this.nameTags.clear();
		
		for(Entry<UUID, String> nameTag : nameTags) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(nameTag.getKey());
			if(player.isPresent()) {
				this.clearNameTag(player.get(), nameTag.getValue());
				this.plugin.getGame().getEventManager().post(new ERemoveNameTagEvent(player.get(), nameTag.getValue(), Cause.source(this.plugin).build()));
			}
		}
	}
	
	@Override
	public boolean sendNameTag(EPlayer player, String identifier, Text teamRepresentation ,Text prefix, Text suffix) {
		if(this.nameTags.containsKey(player.getUniqueId())) {
			String player_identifier = this.nameTags.get(player.getUniqueId());
			if(player_identifier.equalsIgnoreCase(identifier)) {
				this.sendNameTag(player, teamRepresentation, prefix, suffix);
				return true;
			} else if(this.getPriority(player_identifier) <= this.getPriority(identifier)) {
				this.removeAllNameTag(player);
				
				this.nameTags.putIfAbsent(player.getUniqueId(), identifier);
				
				this.sendNameTag(player, teamRepresentation, prefix, suffix);
				this.plugin.getGame().getEventManager().post(new EReplaceNameTagEvent(player, player_identifier, identifier, Cause.source(this.plugin).build()));
				return true;
			}
		} else {
			this.nameTags.putIfAbsent(player.getUniqueId(), identifier);
			this.sendNameTag(player, teamRepresentation, prefix, suffix);
			this.plugin.getGame().getEventManager().post(new EAddNameTagEvent(player, identifier, Cause.source(this.plugin).build()));
			return true;
		}
		return false;
	}
	
	private boolean sendNameTag(EPlayer player, Text teamRepresentation ,Text prefix, Text suffix) {
		Team team = Team.builder()
				.prefix(prefix)
				.suffix(suffix)
				.name(teamRepresentation.toPlain())
				.nameTagVisibility(Visibilities.ALL)
				.displayName(teamRepresentation)
				.color(TextColors.RED)
				.build();
		team.addMember(teamRepresentation);
		player.getScoreboard().registerTeam(team);
		return true;
	}
	
	@Override
	public boolean removeNameTag(EPlayer player, String identifier, Text teamRepresentation) {
		if(this.nameTags.containsKey(player.getUniqueId()) && this.nameTags.get(player.getUniqueId()).equalsIgnoreCase(identifier)) {
			Optional<Team> team = player.getScoreboard().getMemberTeam(teamRepresentation);
			if(team.isPresent()) {
				team.get().unregister();
			}
			
			if(player.getScoreboard().getTeams().isEmpty()) {
				this.plugin.getGame().getEventManager().post(new ERemoveNameTagEvent(player, identifier, Cause.source(this.plugin).build()));
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean clearNameTag(EPlayer player, String identifier) {
		if(this.nameTags.containsKey(player.getUniqueId()) && this.nameTags.get(player.getUniqueId()).equalsIgnoreCase(identifier)) {
			this.removeAllNameTag(player);
			return true;
		}
		return false;
	}
		
	private void removeAllNameTag(Player player) {
		for(Team team : player.getScoreboard().getTeams()) {
			team.unregister();
		}
	}
	
	@Override
	public boolean has(final UUID uuid) {
		return this.nameTags.containsKey(uuid);
	}

	@Override
	public Optional<String> get(final UUID uuid) {
		return Optional.ofNullable(this.nameTags.get(uuid));
	}

	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getNameTag(identifier);
		}
		return PriorityService.DEFAULT;
	}
}
