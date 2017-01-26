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
package fr.evercraft.everapi.services.pagination;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.plugin.EPlugin;

public abstract class CommandPagination<T extends EPlugin<?>> {
	
	protected final T plugin;
	
	private final String name;
	
	public CommandPagination(final T plugin, final String name) {
		this.plugin = plugin;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract Text help(CommandSource source);
	
	public abstract Text description(CommandSource source);
	
	/*
	 * Tab
	 */
	
	public Set<String> getAllUsers() {
		if (this.plugin.getEverAPI().getManagerService().getUserStorage().isPresent()) {
			Set<String> users = new HashSet<String>();
			
			for(GameProfile profile : this.plugin.getEverAPI().getManagerService().getUserStorage().get().getAll()) {
				if (profile.getName().isPresent()) {
					users.add(profile.getName().get());
				}
			}
			
			return users;
		} else {
			return this.getAllPlayers();
		}
	}
	
	public Set<String> getAllUsers(CommandSource player) {
		Set<String> users = this.getAllUsers();
		users.remove(player.getName());
		return users;
	}
	
	public Set<String> getAllPlayers() {
		Set<String> users = new HashSet<String>();
		for(Player player : this.plugin.getEServer().getOnlinePlayers()) {
			users.add(player.getName());
		}
		return users;
	}
	
	public Set<String> getAllPlayers(CommandSource player) {
		Set<String> users = this.getAllPlayers();
		users.remove(player.getName());
		return users;
	}
	
	public Set<String> getAllWorlds() {
		Set<String> worlds = new HashSet<String>();
		for(World world : this.plugin.getEServer().getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}
	
	public Set<String> getAllGroups(World world) {
		Set<String> groups = new HashSet<String>();
		this.plugin.getEverAPI().getManagerService().getPermission().ifPresent(service ->
			service.getGroupSubjects().getAllSubjects().forEach(subject -> groups.add(subject.getIdentifier())));
		return groups;
	}
}
