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
