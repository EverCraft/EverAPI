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
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.world.World;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.server.location.VirtualTransform;
import fr.evercraft.everapi.server.user.EUser;

public interface SpawnService {	
	
	public final static String DEFAULT = "Default";
	
	public interface Priorities {
		
		public final static String BED = "bed";
		
		public final static String SPAWN = "spawn";
		
		public final static String HOME = "home";
		
	}
	
	public Map<String, Transform<World>> getAll();
	
	public boolean has(String identifier);
	public Optional<VirtualTransform> get(String identifier);
	
	public boolean add(String identifier, Transform<World> location);
	public boolean update(String identifier, Transform<World> location);
	public boolean remove(String identifier);
	
	public boolean clearAll();
	
	public Transform<World> getDefault();
	
	default public Transform<World> get(final EUser player) {
		Preconditions.checkNotNull(player, "player");
		
		Optional<SubjectReference> group = player.getGroup();
		if (group.isPresent()) {
			return this.get(group.get());
		}
		return this.getDefault();
	}
	
	default public Transform<World> get(final SubjectReference group) {
		Preconditions.checkNotNull(group, "group");
		
		Optional<VirtualTransform> spawn = this.get(group.getSubjectIdentifier());
		if (!spawn.isPresent()) return this.getDefault();
		
		return spawn.get().getTransform().orElseGet(() -> this.getDefault());
	}
}
