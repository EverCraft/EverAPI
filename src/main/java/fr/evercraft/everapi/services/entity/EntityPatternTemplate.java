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
import java.util.function.BiPredicate;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

public class EntityPatternTemplate implements EntityTemplate {
	
	private final String identifier;
	private final String name;
	
	private final EntityType type;
	
	private final BiPredicate<Entity, Optional<Player>> apply;
	private final BiPredicate<Entity, Optional<Player>> contains;
	
	public EntityPatternTemplate(String identifier, EntityType type,
			BiPredicate<Entity, Optional<Player>> apply, BiPredicate<Entity, Optional<Player>> contains) {
		this.identifier = "evercraft:" + identifier.toLowerCase();
		this.name = identifier;
		
		this.type = type;
		this.apply = apply;
		this.contains = contains;
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
	public boolean apply(Entity entity, Player player) {
		return entity.getType().equals(this.type) && this.apply.test(entity, Optional.ofNullable(player));
	}
	
	@Override
	public boolean contains(Entity entity, Player player) {
		return entity.getType().equals(this.type) && this.contains.test(entity, Optional.ofNullable(player));
	}
}
