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
package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public abstract class SetFlag<T> extends EFlag<Set<T>> {

	public SetFlag(String name) {
		super(name);
	}
	
	public abstract String subSerialize(T value);
	public abstract Set<T> subDeserialize(String value) throws IllegalArgumentException;

	@Override
	public String serialize(Set<T> values) {
		Set<String> values_string = new HashSet<String>();
		for (T value : values) {
			values_string.add(this.subSerialize(value));
		}
		return String.join(",", values_string);
	}

	@Override
	public Set<T> deserialize(String value) throws IllegalArgumentException {
		Set<T> values = new HashSet<T>();
		if (value.isEmpty()) return values;
			
		for (String value_string : value.split(",")) {
			values.addAll(this.subDeserialize(value_string));
		}
		return values;
	}
	
	@Override
	public Set<T> parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) {
		Set<T> addValues = null;
		if (values.isEmpty()) {
			addValues = this.deserialize("");
		} else {
			addValues = this.deserialize(String.join(",", values));
		}
		
		Optional<Set<T>> optFlagValues = region.getFlag(this).get(group);
		if (optFlagValues.isPresent()) {
			Set<T> flagValues = new HashSet<T>(optFlagValues.get());
			flagValues.addAll(addValues);
			return flagValues;
		} else {
			return addValues;
		}
	}
	
	@Override
	public Optional<Set<T>> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) {
		Set<T> removeValues = null;
		if (values.isEmpty()) {
			removeValues = this.deserialize("");
		} else {
			removeValues = this.deserialize(String.join(",", values));
		}
		
		Optional<Set<T>> optFlagValues = region.getFlag(this).get(group);
		if (optFlagValues.isPresent()) {
			Set<T> flagValues = new HashSet<T>(optFlagValues.get());
			flagValues.removeAll(removeValues);
			
			if (!flagValues.isEmpty()) {
				return Optional.of(flagValues);
			}
		} else if (!removeValues.isEmpty()) {
			return Optional.of(removeValues);
		}
		return Optional.empty();
	}
}
