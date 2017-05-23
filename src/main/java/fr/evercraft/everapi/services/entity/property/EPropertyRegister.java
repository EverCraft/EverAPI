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
package fr.evercraft.everapi.services.entity.property;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.entity.EntityTemplate;
import fr.evercraft.everapi.services.worldguard.exception.FlagRegisterException;

@SuppressWarnings("rawtypes")
public class EPropertyRegister implements AdditionalCatalogRegistryModule<EntityTemplate.Property<?>> {
	
	private final ConcurrentMap<String, EntityTemplate.Property<?>> properties;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;
	
	@SuppressWarnings("unchecked")
	public EPropertyRegister(EverAPI plugin) {
		this.properties = new ConcurrentHashMap<String, EntityTemplate.Property<?>>();

		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		plugin.getGame().getRegistry().registerModule(EntityTemplate.Property.class, (AdditionalCatalogRegistryModule) this);
		this.load();
	}
	
	public void load() {
		for (Field field : EntityTemplate.Properties.class.getFields()) {
			try {
				Object value = field.get(null);
				if (value instanceof EntityTemplate.Property) {
					this.registerAdditionalCatalog((EntityTemplate.Property) value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerAdditionalCatalog(EntityTemplate.Property property) throws FlagRegisterException {
		Preconditions.checkNotNull(property, "property");
		
		this.write_lock.lock();
		try {
			this.properties.put(property.getId().toLowerCase(), property);
		} finally {
			this.write_lock.unlock();
		}
	}
	
	public Optional<EntityTemplate.Property<?>> getById(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		EntityTemplate.Property<?> property;
		
		this.read_lock.lock();
		try {
			property = this.properties.get(identifier.toLowerCase());
		} finally {
			this.read_lock.unlock();
		}
		
		return Optional.ofNullable(property);
	}

	@Override
	public Collection<EntityTemplate.Property<?>> getAll() {
		Builder<EntityTemplate.Property<?>> builder = ImmutableSet.builder();
		
		this.read_lock.lock();
		try {
			builder.addAll(this.properties.values());
		} finally {
			this.read_lock.unlock();
		}
		
		return builder.build();
	}
}