package fr.evercraft.everapi.services.worldguard.region;

import java.util.Set;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;
import fr.evercraft.everapi.services.worldguard.flag.FlagValue;
import fr.evercraft.everapi.services.worldguard.regions.EmptySetProtectedRegion;

public interface SetProtectedRegion {

	<T extends EFlag<V>, V> FlagValue<V> getFlag(T flag);
	
	Set<ProtectedRegion> getAll();
	
	static final SetProtectedRegion EMPTY = new EmptySetProtectedRegion();
	
	static SetProtectedRegion empty() {
		return EMPTY;
	}

}
