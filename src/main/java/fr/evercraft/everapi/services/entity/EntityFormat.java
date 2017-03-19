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

import java.util.Set;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

import com.google.common.collect.Sets;

import fr.evercraft.everapi.sponge.UtilsKeys;
import fr.evercraft.everapi.sponge.UtilsKeys.KeyValue;

import ninja.leaping.configurate.ConfigurationNode;

public class EntityFormat implements CatalogType {
	
	private final String identifier;
	private final String name;
	
	private final EntityType type;
	private final Set<KeyValue<?, ?>> values;
	
	public EntityFormat(String identifier, String typeString, ConfigurationNode data) throws IllegalArgumentException {
		this.identifier = identifier.toLowerCase();
		this.name = identifier;
		
		this.type = Sponge.getGame().getRegistry().getType(EntityType.class, typeString).orElseThrow(() -> new IllegalArgumentException());
		this.values = Sets.newConcurrentHashSet();
		data.getChildrenMap().forEach((key, value) -> this.values.add(UtilsKeys.parse(key.toString(), value)));
	}
	
	@Override
	public String getId() {
		return this.identifier;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public EntityType getType() {
		return this.type;
	}
	
	public <S extends CompositeValueStore<S, H>, H extends ValueContainer<?> > boolean apply(CompositeValueStore<S, H> object) {
		return this.values.stream().map(value -> value.apply(object)).reduce((value1, value2) -> value1 && value2).orElse(true);
	}
	
	public <S extends CompositeValueStore<S, H>, H extends ValueContainer<?> > boolean contains(CompositeValueStore<S, H> object) {
		return this.values.stream().map(value -> value.contains(object)).reduce((value1, value2) -> value1 && value2).orElse(true);
	}
	
	public boolean equals(Entity entity) {
		return entity.getType().equals(this.type) && this.apply(entity);
	}
}
