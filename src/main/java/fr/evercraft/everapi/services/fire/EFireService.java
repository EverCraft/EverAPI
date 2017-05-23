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
package fr.evercraft.everapi.services.fire;

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
import fr.evercraft.everapi.services.FireService;

public class EFireService implements FireService, AdditionalCatalogRegistryModule<FireType> {
	
	public final EverAPI plugin;
	
	private final EFireConfig config;
	
	private final ConcurrentHashMap<String, FireType> fires;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;

	public EFireService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.fires = new ConcurrentHashMap<String, FireType>();
		this.config = new EFireConfig(plugin);
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		this.plugin.getGame().getRegistry().registerModule(FireType.class, this);
		this.config.getFires().forEach(entity -> this.registerAdditionalCatalog(entity));
		
		this.load();
	}
	
	public void load() {
		for (Field field : FireTypes.class.getFields()) {
			try {
				Object value = field.get(null);
				if (value instanceof FireType) {
					this.registerAdditionalCatalog((FireType) value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Optional<FireType> getById(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		FireType entity;
		
		this.read_lock.lock();
		try {
			entity = this.fires.get(identifier.toLowerCase());
		} finally {
			this.read_lock.unlock();
		}
		
		return Optional.ofNullable(entity);
	}
	
	@Override
	public void registerAdditionalCatalog(FireType entity) {
		Preconditions.checkNotNull(entity, "entity");
		
		this.write_lock.lock();
		try {
			this.fires.put(entity.getId().toLowerCase(), entity);
		} finally {
			this.write_lock.unlock();
		}
	}
	
	@Override
	public Set<FireType> getAll() {
		Builder<FireType> builder = ImmutableSet.builder();
		
		this.read_lock.lock();
		try {
			builder.addAll(this.fires.values());
		} finally {
			this.read_lock.unlock();
		}
		
		return builder.build();
	}
}
