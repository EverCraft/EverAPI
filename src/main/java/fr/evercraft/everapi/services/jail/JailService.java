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
package fr.evercraft.everapi.services.jail;

import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public interface JailService {
	public Collection<Jail> getAll();
	public Collection<String> getAllNames();
	
	public boolean has(String identifier);
	public Optional<Jail> get(String identifier);
	
	public Optional<Jail> add(String identifier, Transform<World> location, Optional<Integer> radius);
	public boolean remove(String identifier);
	
	public boolean clearAll();
}
