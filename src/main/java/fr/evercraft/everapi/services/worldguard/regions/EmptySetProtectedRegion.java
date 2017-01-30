package fr.evercraft.everapi.services.worldguard.regions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.flag.FlagValue;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public class EmptySetProtectedRegion implements SetProtectedRegion {

	@Override
	public <T extends Flag<V>, V> FlagValue<V> getFlag(T flag) {
		return FlagValue.empty();
	}

	@Override
	public Set<ProtectedRegion> getAll() {
		return ImmutableSet.of();
	}

}
