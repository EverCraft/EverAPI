/*
 * This file is part of EverWorldGuard.
 *
 * EverWorldGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverWorldGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverWorldGuard.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.services.worldguard.regions;

import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

public interface Domain {
	
	/*
	 * Players
	 */
	
	void addPlayer(UUID uniqueId);
	void addPlayer(User player);
	void removePlayer(UUID uniqueId);
	void removePlayer(User player);
	Set<UUID> getPlayers();
	boolean containsPlayers(UUID uniqueId);
	
	/*
	 * Groups
	 */
	
	void addGroup(String group);
	void addGroup(Subject group);
	void removeGroup(String group);
	void removeGroup(Subject group);
	Set<String> getGroups();
	boolean containsGroups(String group);
	
	/*
	 * Accesseurs
	 */
	
	int size();
	void clear();
	void addAll(Domain other);
	void removeAll(Domain other);
	boolean contains(User player, Set<Context> contexts);
	default boolean contains(User player) {
		return this.contains(player, player.getActiveContexts());
	}
}
