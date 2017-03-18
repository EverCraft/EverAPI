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

import fr.evercraft.everapi.plugin.EPlugin;

public interface VirtualTransform {

	public static VirtualTransform empty() {
		return EVirtualEmpty.EMPTY;
	}
	
	public boolean isEmpty();
	
	public Optional<World> getWorld();
	
	public String getWorldIdentifier();
	public Optional<String> getWorldName();
	
	Vector3d getPosition();
	Vector3d getRotation();
	double getPitch();
	double getYaw();
	
	Optional<Location<World>> getLocation();
	Location<World> getLocation(Location<World> location);
	
	Optional<Transform<World>> getTransform();
	Transform<World> getTransform(Transform<World> transform);
	
	/*
	 * Builder
	 */
	
	public static VirtualTransform of(Vector3d position) {
		return new EVirtualPosition(position);
	}
	
	public static VirtualTransform of(double x, double y, double z) {
		return new EVirtualPosition(x, y, z);
	}
	
	public static VirtualTransform of(EPlugin<?> plugin, Location<World> location) {
		return new EVirtualLocation(plugin, location);
	}
	
	public static VirtualTransform of(EPlugin<?> plugin, String world, double x, double y, double z) {
		return new EVirtualLocation(plugin, world, x, y, z);
	}
	
	public static VirtualTransform of(EPlugin<?> plugin, Transform<World> transform) {
		return new EVirtualTransform(plugin, transform);
	}
	
	public static VirtualTransform of(EPlugin<?> plugin, String world, double x, double y, double z, double yaw, double pitch) {
		return new EVirtualTransform(plugin, world, x, y, z, yaw, pitch);
	}
}
