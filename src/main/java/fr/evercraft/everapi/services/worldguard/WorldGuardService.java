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

import org.spongepowered.api.world.World;

import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public interface WorldGuardService {

	Optional<SubjectWorldGuard> get(UUID uuid);
	boolean hasRegistered(UUID uuid);

	Optional<Flag<?>> getFlag(String name);
	void registerFlag(Flag<?> flag);
	void registerFlag(Set<Flag<?>> flags);
	boolean hasRegisteredFlag(Flag<?> flag);
	Set<Flag<?>> getFlags();
	void clearFlags();
	
	Set<ProtectedRegion> getRegion(World world);
}
