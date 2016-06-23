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
package fr.evercraft.everapi.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.essentials.SpawnService;

public class ServerSpawn extends ServerSponge implements SpawnService {
	private SpawnService service;
	
	public ServerSpawn(EverAPI plugin){
		super(plugin);
	}

	private boolean isPresent() {
		if(this.service == null && this.plugin.getManagerService().getSpawn().isPresent()) {
			this.service = this.plugin.getManagerService().getSpawn().get();
		}
		return this.service != null;
	}
	
	@Override
	public Map<String, Transform<World>> getSpawns() {
		if(this.isPresent()) {
			return this.service.getSpawns();
		}
		return new HashMap<String, Transform<World>>();
	}

	@Override
	public Optional<Transform<World>> getSpawn(String identifier) {
		if(this.isPresent()) {
			return this.service.getSpawn(identifier);
		}
		return Optional.empty();
	}

	@Override
	public boolean hasSpawn(String identifier) {
		if(this.isPresent()) {
			return this.service.hasSpawn(identifier);
		}
		return false;
	}

	@Override
	public boolean addSpawn(String identifier, Transform<World> location) {
		if(this.isPresent()) {
			return this.service.addSpawn(identifier, location);
		}
		return false;
	}

	@Override
	public boolean removeSpawn(String identifier) {
		if(this.isPresent()) {
			return this.service.removeSpawn(identifier);
		}
		return false;
	}

	@Override
	public boolean clearSpawns() {
		if(this.isPresent()) {
			return this.service.clearSpawns();
		}
		return false;
	}
}