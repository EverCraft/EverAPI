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
	Set<UUID> getPlayers();
	boolean containsPlayer(UUID uniqueId);
	boolean containsPlayer(User player);
	
	/*
	 * Groups
	 */
	Set<String> getGroups();
	boolean containsGroup(String group);
	boolean containsGroup(Subject group);
	
	/*
	 * Accesseurs
	 */
	
	int size();
	void clear();
	boolean contains(User player, Set<Context> contexts);
	default boolean contains(User player) {
		return this.contains(player, player.getActiveContexts());
	}
}
