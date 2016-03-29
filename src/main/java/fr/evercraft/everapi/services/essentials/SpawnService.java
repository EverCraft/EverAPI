package fr.evercraft.everapi.services.essentials;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public interface SpawnService {	
	public Map<String, Transform<World>> getSpawns();
	
	public boolean hasSpawn(String identifier);
	public Optional<Transform<World>> getSpawn(String identifier);
	
	public boolean addSpawn(String identifier, Transform<World> location);
	public boolean removeSpawn(String identifier);
	
	public boolean clearSpawns();
}
