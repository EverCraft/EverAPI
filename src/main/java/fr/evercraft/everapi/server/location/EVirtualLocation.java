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

public class EVirtualLocation extends EVirtualPosition {
	
	protected final EPlugin<?> plugin;
	private final String world;
	
	public EVirtualLocation(EPlugin<?> plugin, Location<World> location) {
		this(plugin, location.getExtent().getUniqueId().toString(), location.getPosition());
	}
	
	public EVirtualLocation(EPlugin<?> plugin, String world, double x, double y, double z) {
		this(plugin, world, Vector3d.from(x, y, z));
	}
	
	public EVirtualLocation(EPlugin<?> plugin, String world, Vector3d position) {
		super(position);
		
		this.plugin = plugin;
		this.world = world;
	}	
	
	public boolean isEmpty() {
		return false;
	}
	
	public Optional<World> getWorld() {
		return this.plugin.getEServer().getEWorld(this.world);
	}
	
	public Optional<String> getWorldName() {
		Optional<World> world = this.getWorld();
		if (!world.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(world.get().getName());
	}
	
	public String getWorldIdentifier() {
		return this.world;
	}
	
	public Optional<Location<World>> getLocation() {
		Optional<World> world = this.getWorld();
		if (!world.isPresent()) {
			return Optional.empty();
		}
			
		return Optional.ofNullable(world.get().getLocation(this.getPosition()));
	}
	
	public Location<World> getLocation(Location<World> location) {
		Optional<World> world = this.getWorld();
		if (!world.isPresent()) {
			return super.getLocation(location);
		}
			
		return world.get().getLocation(this.getPosition());
	}
	
	public Transform<World> getTransform(Transform<World> transform) {
		Optional<World> world = this.getWorld();
		if (!world.isPresent()) {
			return super.getTransform(transform);
		}
		return new Transform<World>(world.get(), this.getPosition(), transform.getRotation(), transform.getScale());
	}
}
