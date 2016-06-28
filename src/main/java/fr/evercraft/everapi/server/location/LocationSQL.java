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
import java.util.UUID;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.plugin.EPlugin;

public class LocationSQL{
	private final EPlugin plugin;
	
	private final String world;
	private final double x;
	private final double y;
	private final double z;
	private final double yaw;
	private final double pitch;
	
	public LocationSQL(final EPlugin plugin, final String world, final double x, final double y, final double z, final double yaw, final double pitch) {
		this.plugin = plugin;

        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
	}

	/**
	 * Créer une LocationSQL à partir d'une location
	 * @param location La localisation
	 * @throws ServerDisableException 
	 */
	public LocationSQL(final EPlugin plugin, final Transform<World> transform) {
		this(plugin, transform.getLocation(), transform.getRotation());
	}
	
	/**
	 * Créer une ELocation à partir d'une location
	 * @param location La localisation
	 * @throws ServerDisableException 
	 */
	public LocationSQL(final EPlugin plugin, final Location<World> location) {
		this(plugin, location, null);
	}
	
	public LocationSQL(final EPlugin plugin, final Location<World> location, final Vector3d rotation) {
		this(plugin, location.getExtent().getUniqueId().toString(), location.getX(), location.getY(), location.getZ(), rotation.getX(), rotation.getY());
	}
	
	/**
	 * Interface
	 */
	
	/**
	 * Retourne le monde
	 * @param server Le serveur
	 * @return Le monde
	 */
	public Optional<World> getWorld() {
		return this.plugin.getEServer().getWorld(UUID.fromString(this.world));
	}
	
	/**
	 * Retourne le nom du monde
	 * @return
	 */
	public String getWorldName() {
		Optional<World> world = this.getWorld();
		String world_name = "...";
		if(world.isPresent()){
			world_name = world.get().getName();
		}
		return world_name;
	}
	
	/**
	 * Retourne la location
	 * @param server Le serveur
	 * @return La location
	 */
	public Optional<Location<World>> getLocation() {
		Optional<World> world = this.getWorld();
		if(world.isPresent()) {
			return Optional.ofNullable(world.get().getLocation(this.x, this.y, this.z));
		}
		return Optional.empty();
	}
	
	/**
	 * Retourne un Transform
	 * @param server Le serveur
	 * @return Transform
	 */
	public Optional<Transform<World>> getTransform() {
		Optional<World> world = this.getWorld();
		if(world.isPresent()){
			return Optional.of(new Transform<World>(world.get(), this.getPosition(), this.getRotation()));
		}
		return Optional.empty();
	}

	/**
	 * Retourne la location en Vector
	 * @return La location
	 */
	public Vector3d getPosition() {
		return new Vector3d(this.x, this.y, this.z);
	}
	
	/**
	 * Retourne la rotation en vecteur
	 * @return Le vecteur
	 */
	public Vector3d getRotation() {
		return new Vector3d(this.yaw, this.pitch, 0);
	}
    
    public Integer getX(){
		return (int) this.x;
    }
    
    public Integer getY(){
		return (int) this.y;
    }
    
    public Integer getZ(){
		return (int) this.z;
    }
    
    public Double getYaw(){
		return this.yaw;
    }
    
    public Double getPitch(){
    	return this.pitch;
    }
    
    public String getWorldUUID(){
    	return this.world;
    }

	@Override
	public String toString() {
		return "LocationSQL [world=" + this.world + ", x=" + this.x + ", y=" + this.y
				+ ", z=" + this.z + ", yaw=" + this.yaw + ", pitch=" + this.pitch + "]";
	}
}
