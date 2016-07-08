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
import fr.evercraft.everapi.services.essentials.WarpService;

public class ServerWarp extends ServerSpawn {
	private WarpService service;
	
	public ServerWarp(EverAPI plugin){
		super(plugin);
	}

	private boolean isPresent() {
		if(this.service == null && this.plugin.getManagerService().getWarp().isPresent()) {
			this.service = this.plugin.getManagerService().getWarp().get();
		}
		return this.service != null;
	}
	
	public Map<String, Transform<World>> getWarps() {
		if(this.isPresent()) {
			return this.service.getAll();
		}
		return new HashMap<String, Transform<World>>();
	}

	public Optional<Transform<World>> getWarp(String identifier) {
		if(this.isPresent()) {
			return this.service.get(identifier);
		}
		return Optional.empty();
	}

	public boolean hasWarp(String identifier) {
		if(this.isPresent()) {
			return this.service.has(identifier);
		}
		return false;
	}

	public boolean addWarp(String identifier, Transform<World> location) {
		if(this.isPresent()) {
			return this.service.add(identifier, location);
		}
		return false;
	}
	
	public boolean updateWarp(String identifier, Transform<World> location) {
		if(this.isPresent()) {
			return this.service.update(identifier, location);
		}
		return false;
	}


	public boolean removeWarp(String identifier) {
		if(this.isPresent()) {
			return this.service.remove(identifier);
		}
		return false;
	}

	public boolean clearWarp() {
		if(this.isPresent()) {
			return this.service.clearAll();
		}
		return false;
	}
}