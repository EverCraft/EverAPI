package fr.evercraft.everapi.services.worldguard.region;

import java.util.Set;

import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.regions.EmptySetProtectedRegion;

public interface SetProtectedRegion {

	<V> V getFlag(EUser user, Flag<V> flag);
	
	Set<ProtectedRegion> getAll();
	
	static final SetProtectedRegion EMPTY = new EmptySetProtectedRegion();
	
	static SetProtectedRegion empty() {
		return EMPTY;
	}

}
