package fr.evercraft.everapi.services.worldguard.regions;

import java.util.Set;

import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.flag.FlagValue;

public interface SetProtectedRegion {

	<T extends Flag<V>, V> FlagValue<V> getFlag(T flag);
	
	Set<ProtectedRegion> getAll();
	
	static final SetProtectedRegion EMPTY = new EmptySetProtectedRegion();
	
	static SetProtectedRegion empty() {
		return EMPTY;
	}

}
