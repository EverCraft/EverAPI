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

import javax.annotation.Nullable;

import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.services.worldguard.regions.Region;
import fr.evercraft.everapi.services.worldguard.regions.SetProtectedRegion;

public interface SubjectWorldGuard {
	/*
	 * Region
	 */
	
	SetProtectedRegion getRegions();
	
	/*
	 * Select
	 */
	
	Optional<Vector3i> getSelectPos1();
	boolean setSelectPos1(@Nullable Vector3i pos);
	
	Optional<Vector3i> getSelectPos2();
	boolean setSelectPos2(@Nullable Vector3i pos);
	
	List<Vector3i> getSelectPoints();
	boolean addSelectPoint(Vector3i pos);
	boolean removeSelectPoint(Vector3i pos);
	boolean removeSelectPoint(int num);
	boolean clearSelectPoints();
	
	SelectType getSelectType();
	boolean setSelectType(@Nullable SelectType type);
	Optional<Integer> getSelectArea();
	Optional<Region> getSelectRegion();
}
