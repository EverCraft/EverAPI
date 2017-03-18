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
package fr.evercraft.everapi.server.location;

import java.util.Optional;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class EVirtualPosition implements VirtualTransform {	
	private final Vector3d position;
	
	public EVirtualPosition(double x, double y, double z) {
		this(Vector3d.from(x, y, z));
	}
	
	public EVirtualPosition(Vector3d position) {
		this.position = position;
	}
	
	
	public boolean isEmpty() {
		return false;
	}
	
	public Optional<World> getWorld() {
		return Optional.empty();
	}
	
	public String getWorldIdentifier() {
		return "";
	}
	
	public Optional<String> getWorldName() {
		return Optional.empty();
	}
	
	public Location<World> getLocation(Location<World> location) {
		return location.setPosition(this.position);
	}
	
	public Optional<Location<World>> getLocation() {
		return Optional.empty();
	}
	
	public Transform<World> getTransform(Transform<World> transform) {
		return transform.setPosition(this.position);
	}
	
	public Optional<Transform<World>> getTransform() {
		return Optional.empty();
	}

	public Vector3d getPosition() {
		return this.position;
	}
	
	public Vector3d getRotation() {
		return Vector3d.ZERO;
	}
	
	@Override
	public double getPitch() {
		return 0;
	}

	@Override
	public double getYaw() {
		return 0;
	}

	@Override
	public String toString() {
		return "VirtualPosition [position=" + this.position + "]";
	}
}
