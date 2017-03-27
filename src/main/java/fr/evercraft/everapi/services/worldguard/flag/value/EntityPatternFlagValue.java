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
package fr.evercraft.everapi.services.worldguard.flag.value;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.entity.living.player.Player;

import com.google.common.base.Preconditions;

public class EntityPatternFlagValue<T extends PatternFlagValue<V>, V> extends EntryFlagValue<T> {
	
	public EntityPatternFlagValue() {
		super();
	}
	
	public EntityPatternFlagValue(Set<String> keys, Set<T> values) {
		super(keys, values);
	}
	
	private EntityPatternFlagValue(EntityPatternFlagValue<T, V> entry1, EntityPatternFlagValue<T, V> entry2) {
		super(entry1, entry2);
	}
	
	private EntityPatternFlagValue(EntityPatternFlagValue<T, V> entry1, EntityPatternFlagValue<T, V> entry2, boolean remove) {
		super(entry1, entry2, remove);
	}

	public boolean contains(V value) {
		return this.get(value).isPresent();
	}
	
	public Optional<T> get(V value) {
		return this.values.stream()
			.filter(element -> element.contains(value))
			.findAny();
	}
	
	public boolean contains(V value, Player player) {
		return this.get(value, player).isPresent();
	}
	
	public Optional<T> get(V value, Player player) {
		return this.values.stream()
			.filter(element -> element.contains(value, player))
			.findAny();
	}
	
	public EntityPatternFlagValue<T, V> addAll(EntityPatternFlagValue<T, V> entry) {
		Preconditions.checkNotNull(entry, "entry");
		
		return new EntityPatternFlagValue<T, V>(this, entry);
	}
	
	public EntityPatternFlagValue<T, V> removeAll(EntityPatternFlagValue<T, V> entry) {
		Preconditions.checkNotNull(entry, "entry");
		
		return new EntityPatternFlagValue<T, V>(this, entry, true);
	}
}
