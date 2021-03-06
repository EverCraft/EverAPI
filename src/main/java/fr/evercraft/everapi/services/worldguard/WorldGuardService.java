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
package fr.evercraft.everapi.services.worldguard;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.world.World;

public interface WorldGuardService {
	public static final String GLOBAL_REGION = "__global__";
	
	public interface Priorities {
		public static final String FLAG = "everworldguard";
	}

	// Subject
	Optional<WorldGuardSubject> get(UUID uuid);
	boolean hasRegistered(UUID uuid);

	// Flag
	Optional<Flag<?>> getFlag(String name);
	void registerFlag(Flag<?> flag);
	void registerFlag(Set<Flag<?>> flags);
	boolean hasRegisteredFlag(Flag<?> flag);
	Set<Flag<?>> getFlags();
	boolean hasPermissionFlag(Subject subject, Flag<?> flag);
	
	// World
	CompletableFuture<WorldGuardWorld> getOrCreateWorld(World world);
	Set<WorldGuardWorld> getAll();
}
