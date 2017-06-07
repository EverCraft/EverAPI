/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.services.worldguard.region;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.services.worldguard.Flag;
import fr.evercraft.everapi.sponge.UtilsContexts;

public interface SetProtectedRegion {
	
	static final SetProtectedRegion EMPTY = new EmptySetProtectedRegion();
	
	static SetProtectedRegion empty() {
		return EMPTY;
	}
	
	/*
	 * Regions
	 */
	
	Set<ProtectedRegion> getAll();
	
	/*
	 * Flag
	 */

	<V> V getFlag(ProtectedRegion.Group group, Flag<V> flag);
	<V> V getFlag(User user, Set<Context> context, Flag<V> flag);
	
	@Deprecated
	default <V> V getFlag(User user, Flag<V> flag) {
		return this.getFlag(user, user.getActiveContexts(), flag);
	}
	default <V> V getFlag(User user, Location<World> location, Flag<V> flag) {
		return this.getFlag(user, UtilsContexts.get(location.getExtent().getName()), flag);
	}
	default <V> V getFlagDefault(Flag<V> flag) {
		return this.getFlag(ProtectedRegion.Groups.DEFAULT, flag);
	}
	
	/*
	 * FlagIfPresent
	 */
	
	<V> Optional<ProtectedRegion> getRegion(ProtectedRegion.Group group, Flag<V> flag);
	<V> Optional<V> getFlagIfPresent(ProtectedRegion.Group group, Flag<V> flag);
	<V> Optional<V> getFlagIfPresent(User user, Set<Context> context, Flag<V> flag);
	
	@Deprecated
	default <V> Optional<V> getFlagIfPresent(User user, Flag<V> flag) {
		return this.getFlagIfPresent(user, user.getActiveContexts(), flag);
	}
	default <V> Optional<V> getFlagIfPresent(User user, Location<World> location, Flag<V> flag) {
		return this.getFlagIfPresent(user, UtilsContexts.get(location.getExtent().getName()), flag);
	}
	default <V> Optional<V> getFlagDefaultIfPresent(Flag<V> flag) {
		return this.getFlagIfPresent(ProtectedRegion.Groups.DEFAULT, flag);
	}
}
