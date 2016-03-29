package fr.evercraft.everapi.services.essentials;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public interface WarpService {	
	public Map<String, Transform<World>> getWarps();
	
	public boolean hasWarp(String identifier);
	public Optional<Transform<World>> getWarp(String identifier);
	
	public boolean addWarp(String identifier, Transform<World> location);
	public boolean removeWarp(String identifier);
	
	public boolean clearWarps();
}
