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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class SetFlag<T> extends EFlag<Set<T>> {

	public SetFlag(String name) {
		super(name);
	}
	
	public abstract String subSerialize(T value);
	public abstract T subDeserialize(String value) throws IllegalArgumentException;

	@Override
	public String serialize(Set<T> values) {
		List<String> values_string = new ArrayList<String>();
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
			values.add(this.subDeserialize(value_string));
		}
		return values;
	}
}
