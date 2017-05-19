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

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.EntityService;

public class EEntityService implements EntityService, CatalogRegistryModule<EntityTemplate> {
	
	public final EverAPI plugin;
	
	private EEntityConfig config;
	
	private final ConcurrentHashMap<String, EntityTemplate> entities;

	public EEntityService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.entities = new ConcurrentHashMap<String, EntityTemplate>();
		this.config = new EEntityConfig(plugin);
		
		this.plugin.getGame().getRegistry().registerModule(EntityTemplate.class, this);
		this.reload();
	}

	public void reload() {
		this.entities.clear();
		
		this.config.getEntities().forEach(entity -> this.entities.put(entity.getId().toLowerCase(), entity));
	}
	
	@Override
	public Set<EntityTemplate> getAll() {
		return ImmutableSet.copyOf(this.entities.values());
	}

	@Override
	public Optional<EntityTemplate> getById(String identifier) {
		Preconditions.checkNotNull(identifier);
		
		return Optional.ofNullable(this.entities.get(identifier.toLowerCase()));
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
}
