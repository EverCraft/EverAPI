package fr.evercraft.everapi.services.worldguard.regions;

import java.util.Set;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;

import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public class EmptySetProtectedRegion implements SetProtectedRegion {

	@Override
	public <V> V getFlagDefault(Flag<V> flag) {
		return flag.getDefault();
	}
	
	@Override
	public <V> V getFlag(User user, Set<Context> context, Flag<V> flag) {
		return flag.getDefault();
	}

	@Override
	public Set<ProtectedRegion> getAll() {
		return ImmutableSet.of();
	}

}
