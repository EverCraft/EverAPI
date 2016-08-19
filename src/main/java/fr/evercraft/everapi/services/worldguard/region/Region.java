package fr.evercraft.everapi.services.worldguard.region;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.world.World;

import fr.evercraft.everapi.services.worldguard.flag.Flag;

public interface Region {
	
	public String getName();
	
	public World getWorld();
	
	public int getPriority();
	public Optional<Region> getParent();
	
	public Set<UUID> getOwners();
	public Set<UUID> getMembers();
	public Set<String> getGroups();
	
	public Set<Flag> getFlags();
	
	public RegionType getType();
}
