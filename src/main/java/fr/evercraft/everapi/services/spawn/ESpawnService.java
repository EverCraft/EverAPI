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
package fr.evercraft.everapi.services.spawn;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.util.RespawnLocation;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.SpawnService;

public class ESpawnService implements SpawnService {

	public final EverAPI plugin;
	
	private final ConcurrentHashMap<String, Function<EUser, Optional<Transform<World>>>> spawns;

	public ESpawnService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.spawns = new ConcurrentHashMap<String, Function<EUser, Optional<Transform<World>>>>();

		this.register(Priorities.BED, user -> {
			if (user instanceof EPlayer) {
				EPlayer player = (EPlayer) user;
				Optional<Map<UUID,RespawnLocation>> spawns = user.get(Keys.RESPAWN_LOCATIONS);
				if (spawns.isPresent()) {
					RespawnLocation spawn = spawns.get().get(player.getWorld().getUniqueId());
					if (spawn != null) {
						Optional<Location<World>> location = spawn.asLocation();
						if (location.isPresent()) {
							return Optional.of(new Transform<World>(location.get()));
						}
					}
				}
			}
			return Optional.empty();
		});
		this.register(Priorities.WORLD, user -> {
			if (user instanceof EPlayer) {
				EPlayer player = (EPlayer) user;
				return Optional.of(new Transform<World>(player.getWorld().getSpawnLocation()));
			}
			return Optional.empty();
		});
	}
	
	public void reload() {}

	@Override
	public Map<String, Function<EUser, Optional<Transform<World>>>> getAll() {
		return ImmutableMap.copyOf(this.spawns);
	}

	@Override
	public void register(String identifier, Function<EUser, Optional<Transform<World>>> fun) {
		this.spawns.put(identifier.toLowerCase(), fun);
	}
	
	@Override
	public void remove(String identifier) {
		this.spawns.remove(identifier.toLowerCase());
	}

	@Override
	public boolean has(String identifier) {
		return this.spawns.containsKey(identifier.toLowerCase());
	}

	@Override
	public Optional<Function<EUser, Optional<Transform<World>>>> get(String identifier) {
		return Optional.ofNullable(this.spawns.get(identifier.toLowerCase()));
	}

	@Override
	public Transform<World> getSpawn(EUser player) {
		List<String> priorities = this.plugin.getManagerService().getPriority().get(PriorityService.SPAWN);
		for (String priority : priorities) {
			Function<EUser, Optional<Transform<World>>> fun = this.spawns.get(priority);
			if (fun != null) {
				Optional<Transform<World>> spawn = fun.apply(player);
				if (spawn.isPresent()) {
					return spawn.get();
				}
			}
		}
		return this.plugin.getEServer().getSpawn();
	}
}
