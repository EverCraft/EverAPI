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

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.register.ECatalogType;
import fr.evercraft.everapi.services.selection.exception.RegionOperationException;

public interface SelectionRegion {
	
	public class Type extends ECatalogType {
		public Type(String name) {
			super(name);
	    }
	}
	
	public interface Types {
		static final Type CUBOID = new Type("CUBOID");
		static final Type POLYGONAL = new Type("POLYGONAL");
		static final Type CYLINDER = new Type("TEMPLATE");
		static final Type EXTEND = new Type("EXTEND");
		static final Type TEMPLATE = new Type("TEMPLATE");
		static final Type ELLIPSOID = new Type("ELLIPSOID");
		static final Type SPHERE = new Type("SPHERE");
	}
	
	
	Optional<World> getWorld();
	void setWorld(@Nullable World world);
	
	Vector3i getPrimaryPosition();
	List<Vector3i> getPositions();
	
	Type getType();
	Vector3i getMinimumPoint();
	Vector3i getMaximumPoint();
	Vector3i getCenter();
	int getArea();
	int getWidth();
	int getHeight();
	int getLength();
	
	boolean expand(Vector3i... changes) throws RegionOperationException;
	boolean contract(Vector3i... changes) throws RegionOperationException;
	boolean shift(Vector3i change);
	
	boolean containsPosition(Vector3i position);
	
	interface Cuboid extends SelectionRegion {
		Vector3i getSecondaryPosition();
		
		default Type getType() {
			return Types.CUBOID;
		}
	}
	
	interface Extend extends SelectionRegion {
		Vector3i getSecondaryPosition();
		
		default Type getType() {
			return Types.EXTEND;
		}
	}
	
	interface Polygonal extends SelectionRegion {
		List<Vector3i> getPositions();
		
		default Type getType() {
			return Types.POLYGONAL;
		}
	}
	
	interface Cylinder extends SelectionRegion {
		Vector3i getCenter();
		Vector3d getRadius();
		int getMinimumY();
		int getMaximumY();
		
		default Type getType() {
			return Types.CYLINDER;
		}
	}
	
	interface Ellipsoid extends SelectionRegion {
		Vector3i getCenter();
		Vector3d getRadius();
		
		default Type getType() {
			return Types.ELLIPSOID;
		}
	}
}
