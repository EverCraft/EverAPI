package fr.evercraft.everapi.services.worldguard.region;

import java.util.Set;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;

import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.regions.EmptySetProtectedRegion;

public interface SetProtectedRegion {

	<V> V getFlagDefault(Flag<V> flag);
	<V> V getFlag(User user, Set<Context> context, Flag<V> flag);
	
	default <V> V getFlag(User user, Flag<V> flag) {
		return this.getFlag(user, user.getActiveContexts(), flag);
	}
	
	Set<ProtectedRegion> getAll();
	
	static final SetProtectedRegion EMPTY = new EmptySetProtectedRegion();
	
	static SetProtectedRegion empty() {
		return EMPTY;
	}

}
