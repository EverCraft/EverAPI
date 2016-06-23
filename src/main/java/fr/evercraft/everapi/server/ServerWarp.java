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

public class ServerWarp extends ServerSponge implements WarpService {
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
	
	@Override
	public Map<String, Transform<World>> getWarps() {
		if(this.isPresent()) {
			return this.service.getWarps();
		}
		return new HashMap<String, Transform<World>>();
	}

	@Override
	public Optional<Transform<World>> getWarp(String identifier) {
		if(this.isPresent()) {
			return this.service.getWarp(identifier);
		}
		return Optional.empty();
	}

	@Override
	public boolean hasWarp(String identifier) {
		if(this.isPresent()) {
			return this.service.hasWarp(identifier);
		}
		return false;
	}

	@Override
	public boolean addWarp(String identifier, Transform<World> location) {
		if(this.isPresent()) {
			return this.service.addWarp(identifier, location);
		}
		return false;
	}

	@Override
	public boolean removeWarp(String identifier) {
		if(this.isPresent()) {
			return this.service.removeWarp(identifier);
		}
		return false;
	}

	@Override
	public boolean clearWarps() {
		if(this.isPresent()) {
			return this.service.clearWarps();
		}
		return false;
	}
}