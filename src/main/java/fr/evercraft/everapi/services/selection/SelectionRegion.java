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
package fr.evercraft.everapi.services.selection;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

public interface SelectionRegion {
	public enum Type {
		CUBOID(),
		POLYGONAL(),
		CYLINDER();
		
		public String getName() {
	        return this.name();
	    }
		
		public static Optional<SelectionRegion.Type> getSelectType(final String name) {
			Type result = null;
			int cpt = 0;
			Type[] type = Type.values();
			while(cpt < type.length && result == null){
				if (type[cpt].getName().equalsIgnoreCase(name)) {
					result = type[cpt];
				}
				cpt++;
			}
			return Optional.ofNullable(result);
		}
	}

	public Optional<World> getWorld();
	public void setWorld(@Nullable World world);
	
	public boolean selectPrimary(@Nullable Vector3i position);
	public boolean selectSecondary(@Nullable Vector3i position);
	public void clear();
	
	public Vector3i getPrimaryPosition();
	public Vector3i getSecondaryPosition();
	public List<Vector3i> getPositions();
	
	public Type getType();
	public Vector3i getMinimumPoint();
	public Vector3i getMaximumPoint();
	public Vector3i getCenter();
	public int getVolume();
	public int getWidth();
	public int getHeight();
	public int getLength();
	
	public void expand(Vector3i... changes) throws RegionOperationException;
	public void contract(Vector3i... changes) throws RegionOperationException;
	public void shift(Vector3i change) throws RegionOperationException;
	
	public boolean containsPosition(Vector3i position);
	
	public interface Cuboid extends SelectionRegion {
		default SelectionRegion.Type getType() {
			return SelectionRegion.Type.CUBOID;
		}
	}
	
	public interface Polygonal extends SelectionRegion {
		default SelectionRegion.Type getType() {
			return SelectionRegion.Type.POLYGONAL;
		}
	}
	
	public interface Cylinder extends SelectionRegion {
		default SelectionRegion.Type getType() {
			return SelectionRegion.Type.CYLINDER;
		}
	}
}
