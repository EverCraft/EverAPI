/*
 * This file is part of EverWorldGuard.
 *
 * EverWorldGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverWorldGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverWorldGuard.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.services.worldguard.flag;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion.Group;

public class FlagValue<T> {	
	@SuppressWarnings("unchecked")
	public static <T> FlagValue<T> empty() {
		return (FlagValue<T>) new FlagValue<Object>();
	}
	
	private ConcurrentMap<Group, T> values;
	
	public FlagValue() {
		this.values = new ConcurrentHashMap<Group, T>();
	}
	
	public void set(Group association, T value) {
		if (value == null) {
			this.values.remove(association);
		} else {
			this.values.put(association, value);
		}
	}

	public Optional<T> get(Group association) {
		return Optional.ofNullable(this.values.get(association));
	}
	
	public Map<Group, T> getAll() {
		return ImmutableMap.copyOf(this.values);
	}
}
