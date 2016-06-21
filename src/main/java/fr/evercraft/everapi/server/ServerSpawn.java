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