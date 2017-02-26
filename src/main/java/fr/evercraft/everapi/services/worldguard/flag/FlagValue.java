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
package fr.evercraft.everapi.services.worldguard.flag;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion.Group;

public interface FlagValue<T> {

	public Optional<T> get(Group group);
	public Optional<T> getInherit(Group group);
	public Map<Group, T> getAll();	
	public boolean isEmpty();
	
	/*
	 * Empty
	 */
	
	public static <T> FlagValue<T> empty() {
		return (FlagValue<T>) new Empty<T>();
	}
	
	class Empty<T> implements FlagValue<T> {
		
		@Override
		public Optional<T> get(Group group) {
			return Optional.empty();
		}
		
		@Override
		public Optional<T> getInherit(Group group) {
			return Optional.empty();
		}

		@Override
		public Map<Group, T> getAll() {
			return ImmutableMap.of();
		}

		@Override
		public boolean isEmpty() {
			return true;
		}
		
	}
}
