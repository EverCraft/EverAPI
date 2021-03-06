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
package fr.evercraft.everapi.services.entity;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.EntityService;
import fr.evercraft.everapi.services.entity.property.EPropertyRegister;

public class EEntityService implements EntityService, AdditionalCatalogRegistryModule<EntityTemplate> {
	
	public final EverAPI plugin;
	
	private final EPropertyRegister properties;
	private final EEntityConfig config;
	
	private final ConcurrentHashMap<String, EntityTemplate> entities;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;

	public EEntityService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.properties = new EPropertyRegister(plugin);
		
		this.entities = new ConcurrentHashMap<String, EntityTemplate>();
		this.config = new EEntityConfig(plugin);
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		this.plugin.getGame().getRegistry().registerModule(EntityTemplate.class, this);
		this.config.getEntities().forEach(entity -> this.registerAdditionalCatalog(entity));
		
		this.load();
	}
	
	public void load() {
		for (Field field : EntityTemplates.class.getFields()) {
			try {
				Object value = field.get(null);
				if (value instanceof EntityTemplate) {
					this.registerAdditionalCatalog((EntityTemplate) value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Optional<EntityTemplate> getById(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		EntityTemplate entity;
		
		this.read_lock.lock();
		try {
			entity = this.entities.get(identifier.toLowerCase());
		} finally {
			this.read_lock.unlock();
		}
		
		return Optional.ofNullable(entity);
	}
	
	@Override
	public Optional<EntityTemplate> getForAll(String identifier) {
		Preconditions.checkNotNull(identifier);
		
		EntityTemplate entity = this.entities.get(identifier.toLowerCase());
		if (entity != null) {
			return Optional.of(entity);
		}
		
		Optional<EntityType> type = this.plugin.getGame().getRegistry().getType(EntityType.class, identifier);
		if (type.isPresent()) {
			return Optional.of(EntityTemplate.of(type.get()));
		}
		
		return Optional.empty();
	}
	
	@Override
	public void registerAdditionalCatalog(EntityTemplate entity) {
		Preconditions.checkNotNull(entity, "entity");
		
		this.write_lock.lock();
		try {
			this.entities.put(entity.getId().toLowerCase(), entity);
		} finally {
			this.write_lock.unlock();
		}
	}
	
	@Override
	public Set<EntityTemplate> getAll() {
		Builder<EntityTemplate> builder = ImmutableSet.builder();
		
		this.read_lock.lock();
		try {
			builder.addAll(this.entities.values());
		} finally {
			this.read_lock.unlock();
		}
		
		return builder.build();
	}

	public EPropertyRegister getProperties() {
		return this.properties;
	}
}
