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
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.location.VirtualTransform;
import fr.evercraft.everapi.server.user.EUser;

public interface SpawnSubjectService {	
	
	public final static String DEFAULT = "default";
	
	public final static String NEWBIE = "newbie";
	
	public interface Priorities {
		
		public final static String SPAWN = "spawn";
		
		public final static String NEWBIE = "newbie";
		
	}

	Map<SubjectReference, Transform<World>> getAll();
	Map<SubjectReference, VirtualTransform> getAllVirtual();
	
	Optional<VirtualTransform> getDefault();
	Optional<VirtualTransform> getNewbie();
	Optional<VirtualTransform> get(SubjectReference identifier);

	CompletableFuture<Boolean> setDefault(@Nullable Transform<World> location);
	CompletableFuture<Boolean> setNewbie(@Nullable Transform<World> location);
	CompletableFuture<Boolean> set(SubjectReference identifier, @Nullable Transform<World> location);

	CompletableFuture<Boolean> clearAll();
	
	Optional<Transform<World>> getSpawn(EUser user);
	Optional<Transform<World>> getSpawn(SubjectReference reference);
	
	
}
