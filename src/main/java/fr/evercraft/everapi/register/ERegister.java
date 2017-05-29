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
package fr.evercraft.everapi.register;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import fr.evercraft.everapi.EverAPI;

public class ERegister<T extends CatalogType> implements AdditionalCatalogRegistryModule<T> {
	
	public final EverAPI plugin;
	
	private final ConcurrentHashMap<String, T> types;
	private final Class<T> type;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;

	public ERegister(final EverAPI plugin, Class<T> type, Class<?> list) {
		this.plugin = plugin;
		
		this.types = new ConcurrentHashMap<String, T>();
		this.type = type;
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		this.plugin.getGame().getRegistry().registerModule(type, this);
		
		this.load(list);
	}
	
	@SuppressWarnings("unchecked")
	public void load(Class<?> list) {
		for (Field field : list.getFields()) {
			try {
				Object value = field.get(null);
				if (this.type.isAssignableFrom(value.getClass())) {
					this.registerAdditionalCatalog((T) value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Optional<T> getById(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		T value;
		
		this.read_lock.lock();
		try {
			value = this.types.get(identifier.toLowerCase());
		} finally {
			this.read_lock.unlock();
		}
		
		return Optional.ofNullable(value);
	}
	
	@Override
	public void registerAdditionalCatalog(T value) {
		Preconditions.checkNotNull(value, "value");
		
		this.write_lock.lock();
		try {
			this.types.put(value.getId().toLowerCase(), value);
		} finally {
			this.write_lock.unlock();
		}
	}
	
	@Override
	public Set<T> getAll() {
		Builder<T> builder = ImmutableSet.builder();
		
		this.read_lock.lock();
		try {
			builder.addAll(this.types.values());
		} finally {
			this.read_lock.unlock();
		}
		
		return builder.build();
	}
}
