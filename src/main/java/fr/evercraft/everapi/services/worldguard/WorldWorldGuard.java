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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.service.permission.Subject;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion.RemoveType;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public interface WorldWorldGuard {

	// Create
	ProtectedRegion.Cuboid createRegionCuboid(String region, Vector3i pos1, Vector3i pos2, Set<EUser> owner_players, Set<Subject> owner_groups);
	ProtectedRegion.Polygonal createRegionPolygonal(String region, List<Vector3i> positions, Set<EUser> owner_players, Set<Subject> owner_groups);
	ProtectedRegion.Template createRegionTemplate(String region, Set<EUser> owner_players, Set<Subject> owner_groups);
	
	Optional<ProtectedRegion> getRegion(String region);
	Optional<ProtectedRegion> removeRegion(String region, RemoveType type);
	
	SetProtectedRegion getRegions(Vector3i position);
	default SetProtectedRegion getRegions(Vector3d position) {
		return this.getRegions(position.toInt());
	}
	Set<ProtectedRegion> getAll();
}
