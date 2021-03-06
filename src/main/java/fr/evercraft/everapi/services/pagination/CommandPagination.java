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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.permission.SubjectCollection;
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
		Set<String> users = new HashSet<String>();
		
		this.plugin.getEverAPI().getManagerService().getUserStorage().getAll().stream()
			.filter(predicate)
			.forEach(profile -> users.add(profile.getName().get()));
		
		return users;
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
	
	protected Set<String> getAllUsers(List<String> args, CommandSource player) {
		if (args.isEmpty()) return this.getAllPlayers();
		
		Set<String> users = this.getAllUsers(args.get(args.size()-1));		
		users.removeAll(args);
		return users;
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
		return this.plugin.getEverAPI().getManagerService().getPermission().getGroupSubjects().getLoadedSubjects().stream()
			.map(subject -> subject.getFriendlyIdentifier().orElse(subject.getIdentifier()))
			.collect(Collectors.toSet());
	}
	
	protected Set<String> getAllGroups(List<String> args) {
		Set<String> groups = this.getAllGroups();
		groups.removeAll(args);
		return groups;
	}
	
	protected Set<String> getAllGroups(String world) {
		// TODO
		return this.plugin.getEverAPI().getManagerService().getPermission().getGroupSubjects().getLoadedSubjects().stream()
				.map(subject -> subject.getFriendlyIdentifier().orElse(subject.getIdentifier()))
				.collect(Collectors.toSet());
	}
	
	protected Set<String> getAllCollections() {
		return this.plugin.getEverAPI().getManagerService().getPermission().getLoadedCollections().values().stream()
				.map(collection -> collection.getIdentifier())
				.collect(Collectors.toSet());
	}
	
	protected Set<String> getAllSubjects(String identifierCollection) {
		Optional<SubjectCollection> collection = this.plugin.getEverAPI().getManagerService().getPermission().getCollection(identifierCollection);
		if (!collection.isPresent()) return new HashSet<String>();
		
		return collection.get().getLoadedSubjects().stream()
				.map(subject -> subject.getFriendlyIdentifier().orElse(subject.getIdentifier()))
				.collect(Collectors.toSet());
	}
	
	protected Set<String> getAllPermissions() {
		TreeSet<String> suggests = new TreeSet<String>();
		this.plugin.getEverAPI().getManagerService().getPermission().getDescriptions()
			.forEach(permission -> suggests.add(permission.getId()));
		
		if (suggests.isEmpty()) {
			suggests.add("ever...");
		}
		return suggests;
	}
	
	protected Collection<String> getAllOptions() {
		return Arrays.asList("prefix", "suffix");
	}
	
	protected <V extends CatalogType> Set<String> getAll(Class<V> type) {
		return this.plugin.getGame().getRegistry().getAllOf(type).stream()
				.map(group -> group.getName())
				.collect(Collectors.toSet());
	}
}
