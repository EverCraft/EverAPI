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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.services.selection.SelectionRegion;

public interface Selector {
	
	static Selector empty() {
		return Empty.EMPTY;
	}
	
	public Optional<SelectionRegion> getRegion();
	
	public Optional<World> getWorld();
	public void setWorld(@Nullable World world);
	
	public boolean selectPrimary(@Nullable Vector3i position);
	public boolean selectSecondary(@Nullable Vector3i position);
	public void clear();
	public int getVolume();
	
	public boolean expand(Vector3i... changes);
	public boolean contract(Vector3i... changes);
	public boolean shift(Vector3i change);
	
	public Optional<Vector3i> getPrimaryPosition();
	public List<Vector3i> getPositions();
	SelectionRegion.Type getType();
	
	public interface Cuboid extends Selector {
		public Vector3i getSecondaryPosition();
		
		default SelectionRegion.Type getType() {
			return SelectionRegion.Type.CUBOID;
		}
	}
	
	public interface Polygonal extends Selector {		
		default SelectionRegion.Type getType() {
			return SelectionRegion.Type.POLYGONAL;
		}
	}
	
	public interface Cylinder extends Selector {
		public Vector3i getSecondaryPosition();
		
		default SelectionRegion.Type getType() {
			return SelectionRegion.Type.CYLINDER;
		}
	}
	
	public class Empty implements Selector {
		private static Empty EMPTY = new Empty();

		@Override
		public Optional<SelectionRegion> getRegion() {
			return Optional.empty();
		}

		@Override
		public Optional<World> getWorld() {
			return Optional.empty();
		}

		@Override
		public void setWorld(World world) {
		}

		@Override
		public boolean selectPrimary(Vector3i position) {
			return false;
		}

		@Override
		public boolean selectSecondary(Vector3i position) {
			return false;
		}

		@Override
		public void clear() {
		}

		@Override
		public int getVolume() {
			return 0;
		}

		@Override
		public Optional<Vector3i> getPrimaryPosition() {
			return Optional.empty();
		}

		@Override
		public SelectionRegion.Type getType() {
			return SelectionRegion.Type.CUBOID;
		}

		@Override
		public boolean expand(Vector3i... changes) {
			return false;
		}

		@Override
		public boolean contract(Vector3i... changes) {
			return false;
		}

		@Override
		public boolean shift(Vector3i change) {
			return false;
		}

		@Override
		public List<Vector3i> getPositions() {
			return Arrays.asList();
		}
		
	}
}
