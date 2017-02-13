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
import java.util.TreeSet;
import java.util.function.Predicate;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public abstract class CommandPagination<T extends EPlugin<?>> {
	
	private final int TAB_LENGTH = 2;
	
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
	
	public Set<String> getAllGameProfile() {
		Set<String> users = new HashSet<String>();
		
		for(GameProfile profile : this.plugin.getEServer().getGameProfileManager().getCache().getProfiles()) {
			if (profile.getName().isPresent()) {
				users.add(profile.getName().get());
			}
		}
		
		return users;
	}
	
	private Set<String> getAllUsers(Predicate<? super GameProfile> predicate) {
		if (this.plugin.getEverAPI().getManagerService().getUserStorage().isPresent()) {
			Set<String> users = new HashSet<String>();
			
			this.plugin.getEverAPI().getManagerService().getUserStorage().get().getAll().stream()
				.filter(predicate)
				.forEach(profile -> users.add(profile.getName().get()));
			
			return users;
		} else {
			return this.getAllPlayers();
		}
	}
	
	protected Set<String> getAllUsers() {
		return this.getAllUsers(profile -> profile.getName().isPresent());
	}
	
	protected Set<String> getAllUsers(String arg) {
		if (arg.length() < TAB_LENGTH) {
			return this.getAllPlayers();
		} else {
			String prefix = arg.toLowerCase();
			return this.getAllUsers(profile -> profile.getName().isPresent() && profile.getName().get().toLowerCase().startsWith(prefix));
		}
	}
	
	protected Set<String> getAllUsers(String arg, CommandSource player) {
		if (arg.length() < TAB_LENGTH) {
			return this.getAllPlayers(player, false);
		} else {
			String prefix = arg.toLowerCase();
			return this.getAllUsers(profile -> profile.getName().isPresent() && 
					profile.getName().get().toLowerCase().startsWith(prefix) &&
					!profile.getUniqueId().toString().equalsIgnoreCase(player.getIdentifier()));
		}
	}
	
	public Set<String> getAllPlayers() {
		Set<String> users = new HashSet<String>();
		this.plugin.getEServer().getOnlineEPlayers()
			.forEach(other -> users.add(other.getName()));
		return users;
	}
	
	protected Set<String> getAllPlayers(EPlayer player, boolean remove) {
		Set<String> users = new HashSet<String>();
		this.plugin.getEServer().getOnlineEPlayers().stream()
			.filter(others -> player.canSeePlayer(others) && (!remove || !player.equals(others)))
			.forEach(other -> users.add(other.getName()));
		return users;
	}
	
	protected Set<String> getAllPlayers(CommandSource source, boolean remove) {
		if (source instanceof EPlayer) {
			return this.getAllPlayers((EPlayer) source, remove);
		} else if (source instanceof Player) {
			EPlayer player = this.plugin.getEServer().getEPlayer((Player) source);
			return this.getAllPlayers(player, remove);
		}
		
		return this.getAllPlayers();
	}
	
	protected Set<String> getAllWorlds() {
		Set<String> worlds = new HashSet<String>();
		for(World world : this.plugin.getEServer().getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}
	
	protected Set<String> getAllGroups() {
		Set<String> groups = new HashSet<String>();
		this.plugin.getEverAPI().getManagerService().getPermission().ifPresent(service ->
			service.getGroupSubjects().getAllSubjects()
				.forEach(subject -> groups.add(subject.getIdentifier())));
		return groups;
	}
	
	protected Set<String> getAllGroups(World world) {
		Set<String> groups = new HashSet<String>();
		this.plugin.getEverAPI().getManagerService().getPermission().ifPresent(service ->
			service.getGroupSubjects().getAllSubjects().forEach(subject -> groups.add(subject.getIdentifier())));
		return groups;
	}
	
	protected Set<String> getAllPermissions() {
		TreeSet<String> suggests = new TreeSet<String>();
		this.plugin.getEverAPI().getManagerService().getPermission().ifPresent(service -> 
			service.getDescriptions().forEach(permission -> suggests.add(permission.getId())));
		
		if (suggests.isEmpty()) {
			suggests.add("ever...");
		}
		return suggests;
	}
}
