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