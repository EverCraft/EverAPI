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
package fr.evercraft.everapi.services.snow;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import fr.evercraft.everapi.EverAPI;

public class SnowService implements AdditionalCatalogRegistryModule<SnowType> {
	
	public final EverAPI plugin;
	
	private final ConcurrentHashMap<String, SnowType> snows;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;

	public SnowService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.snows = new ConcurrentHashMap<String, SnowType>();
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		this.plugin.getGame().getRegistry().registerModule(SnowType.class, this);
		
		this.load();
	}
	
	public void load() {
		for (Field field : SnowTypes.class.getFields()) {
			try {
				Object value = field.get(null);
				if (value instanceof SnowType) {
					this.registerAdditionalCatalog((SnowType) value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Optional<SnowType> getById(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		SnowType snow;
		
		this.read_lock.lock();
		try {
			snow = this.snows.get(identifier.toLowerCase());
		} finally {
			this.read_lock.unlock();
		}
		
		return Optional.ofNullable(snow);
	}
	
	@Override
	public void registerAdditionalCatalog(SnowType snow) {
		Preconditions.checkNotNull(snow, "snow");
		
		this.write_lock.lock();
		try {
			this.snows.put(snow.getId().toLowerCase(), snow);
		} finally {
			this.write_lock.unlock();
		}
	}
	
	@Override
	public Set<SnowType> getAll() {
		Builder<SnowType> builder = ImmutableSet.builder();
		
		this.read_lock.lock();
		try {
			builder.addAll(this.snows.values());
		} finally {
			this.read_lock.unlock();
		}
		
		return builder.build();
	}
}
