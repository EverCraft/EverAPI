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

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

public class EntryFlagValue<V> {

	protected final Set<String> keys;
	protected final Set<V> values;
	
	public EntryFlagValue() {
		this.keys = Sets.newConcurrentHashSet();
		this.values = Sets.newConcurrentHashSet();
	}
	
	public EntryFlagValue(Set<String> keys, Set<V> values) {
		this();
		this.keys.addAll(keys);
		this.values.addAll(values);
	}
	
	protected EntryFlagValue(EntryFlagValue<V> entry1, EntryFlagValue<V> entry2) {
		this();
		this.keys.addAll(entry1.getKeys());
		this.keys.addAll(entry2.getKeys());
		this.values.addAll(entry1.getValues());
		this.values.addAll(entry2.getValues());
	}
	
	protected EntryFlagValue(EntryFlagValue<V> entry1, EntryFlagValue<V> entry2, boolean remove) {
		this();
		this.keys.addAll(entry1.getKeys());
		this.keys.removeAll(entry2.getKeys());
		this.values.addAll(entry1.getValues());
		this.values.removeAll(entry2.getValues());
	}
	
	public Set<String> getKeys() {
		return this.keys;
	}
	
	public Set<V> getValues() {
		return this.values;
	}
	
	public boolean containsKeys(String key) {
		return this.keys.contains(key);
	}
	
	public boolean containsValue(V value) {
		return this.values.contains(value);
	}
	
	public EntryFlagValue<V> addAll(EntryFlagValue<V> entry) {
		Preconditions.checkNotNull(entry, "entry");
		
		return new EntryFlagValue<V>(this, entry);
	}
	
	public EntryFlagValue<V> removeAll(EntryFlagValue<V> entry) {
		Preconditions.checkNotNull(entry, "entry");
		
		return new EntryFlagValue<V>(this, entry, true);
	}
}
