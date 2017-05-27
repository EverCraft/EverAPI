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
package fr.evercraft.everapi.services.ice;

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

public class IceService implements AdditionalCatalogRegistryModule<IceType> {
	
	public final EverAPI plugin;
	
	private final ConcurrentHashMap<String, IceType> ices;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;

	public IceService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.ices = new ConcurrentHashMap<String, IceType>();
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		this.plugin.getGame().getRegistry().registerModule(IceType.class, this);
		
		this.load();
	}
	
	public void load() {
		for (Field field : IceTypes.class.getFields()) {
			try {
				Object value = field.get(null);
				if (value instanceof IceType) {
					this.registerAdditionalCatalog((IceType) value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Optional<IceType> getById(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		IceType snow;
		
		this.read_lock.lock();
		try {
			snow = this.ices.get(identifier.toLowerCase());
		} finally {
			this.read_lock.unlock();
		}
		
		return Optional.ofNullable(snow);
	}
	
	@Override
	public void registerAdditionalCatalog(IceType ice) {
		Preconditions.checkNotNull(ice, "ice");
		
		this.write_lock.lock();
		try {
			this.ices.put(ice.getId().toLowerCase(), ice);
		} finally {
			this.write_lock.unlock();
		}
	}
	
	@Override
	public Set<IceType> getAll() {
		Builder<IceType> builder = ImmutableSet.builder();
		
		this.read_lock.lock();
		try {
			builder.addAll(this.ices.values());
		} finally {
			this.read_lock.unlock();
		}
		
		return builder.build();
	}
}
