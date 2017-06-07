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

import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.User;

import fr.evercraft.everapi.java.UtilsPredicate.TriPredicate;

public class EntityPatternTemplate implements EntityTemplate {
	
	private final String identifier;
	private final String name;
	
	private final EntityType type;
	
	private final Map<EntityTemplate.Property<?>, ?> properties;
	
	private final BiPredicate<Entity, Optional<User>> apply;
	private final BiPredicate<Entity, Optional<User>> contains;
	
	@SuppressWarnings("unchecked")
	public <T> EntityPatternTemplate(String identifier, EntityType type, Map<EntityTemplate.Property<?>, ?> properties) {
		this.identifier = "evercraft:" + identifier.toLowerCase();
		this.name = identifier;
		
		this.type = type;
		this.properties = properties;
		
		this.apply = this.properties.entrySet().stream()
			.map(entry -> {
				T value = (T) entry.getValue();
				TriPredicate<T, Entity, Optional<User>> property = (TriPredicate<T, Entity, Optional<User>>) entry.getKey().getApply();
				BiPredicate<Entity, Optional<User>> predicate = (v1, v2) -> property.test(value, v1, v2);
				return predicate;
			})
			.reduce((p1, p2) -> p1.and(p2))
			.orElse((entity, player) -> true);
		this.contains = this.properties.entrySet().stream()
			.map(entry -> {
				T value = (T) entry.getValue();
				TriPredicate<T, Entity, Optional<User>> property = (TriPredicate<T, Entity, Optional<User>>) entry.getKey().getContains();
				BiPredicate<Entity, Optional<User>> predicate = (v1, v2) -> property.test(value, v1, v2);
				return predicate;
			})
			.reduce((p1, p2) -> p1.and(p2))
			.orElse((entity, player) -> true);
	}
	
	@Override
	public String getId() {
		return this.identifier;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public EntityType getType() {
		return this.type;
	}
	
	@Override
	public boolean apply(Entity entity) {
		return entity.getType().equals(this.type) && this.apply.test(entity, Optional.empty());
	}
	
	@Override
	public boolean contains(Entity entity) {
		return entity.getType().equals(this.type) && this.contains.test(entity, Optional.empty());
	}
	
	@Override
	public boolean apply(Entity entity, User player) {
		return entity.getType().equals(this.type) && this.apply.test(entity, Optional.ofNullable(player));
	}
	
	@Override
	public boolean contains(Entity entity, User player) {
		return entity.getType().equals(this.type) && this.contains.test(entity, Optional.ofNullable(player));
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EntityPatternTemplate)) return false;
		EntityPatternTemplate template = (EntityPatternTemplate) o;
		
		if (!template.getId().equals(this.getId())) return false;
		if (!template.getName().equals(this.getName())) return false;
		if (!template.getType().equals(this.getType())) return false;
		return true;
	}
}
