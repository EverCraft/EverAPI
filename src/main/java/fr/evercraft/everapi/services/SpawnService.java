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
package fr.evercraft.everapi.services;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

import com.google.common.base.Function;

import fr.evercraft.everapi.server.user.EUser;

public interface SpawnService {
	
	public interface Priorities {
		
		public final static String BED = "bed";
		
		public final static String WORLD = "world";
	}
	
	public Map<String, Function<EUser, Optional<Transform<World>>>> getAll();
	public void register(String identifier, Function<EUser, Optional<Transform<World>>> fun);
	public void remove(String identifier);
	
	public boolean has(String identifier);
	public Optional<Function<EUser, Optional<Transform<World>>>> get(String identifier);
	
	public Transform<World> getSpawn(final EUser player);
}
