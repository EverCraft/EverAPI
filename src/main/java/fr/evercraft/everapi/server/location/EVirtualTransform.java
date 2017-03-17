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
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import fr.evercraft.everapi.plugin.EPlugin;

public class EVirtualTransform extends EVirtualLocation {

	private Vector3d rotation;
	
	public EVirtualTransform(EPlugin<?> plugin, Transform<World> transform) {
		this(plugin, transform.getExtent().getUniqueId().toString(), transform.getPosition(), transform.getRotation());
	}
	
	public EVirtualTransform(EPlugin<?> plugin, String world, Double x, Double y, Double z, Double yaw, Double pitch) {
		this(plugin, world, Vector3d.from(x, y, z), Vector3d.from(pitch, yaw, 0));
	}
	
	public EVirtualTransform(EPlugin<?> plugin, String world, Vector3d position, Vector3d rotation) {
		super(plugin, world, position);
		
		this.rotation = rotation;
	}

	public Vector3d getRotation() {
		return this.rotation;
	}
	
	public double getPitch() {
		return this.rotation.getX();
	}
	
	public double getYaw() {
		return this.rotation.getY();
	}
	
	public Transform<World> getTransform(Transform<World> transform) {
		Optional<World> world = this.getWorld();
		if (!world.isPresent()) {
			return super.getTransform(transform);
		}
		return new Transform<World>(world.get(), this.getPosition(), this.rotation, transform.getScale());
	}
	
	public Optional<Transform<World>> getTransform() {
		Optional<World> world = this.getWorld();
		if (!world.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new Transform<World>(world.get(), this.getPosition(), this.rotation));
	}
}
