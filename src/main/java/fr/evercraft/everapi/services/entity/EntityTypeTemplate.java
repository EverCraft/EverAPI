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

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

public class EntityTypeTemplate implements EntityTemplate {
	
	private final EntityType type;
	
	public EntityTypeTemplate(EntityType type) throws IllegalArgumentException {
		this.type = type;
	}
	
	@Override
	public String getId() {
		return this.type.getId();
	}

	@Override
	public String getName() {
		return this.type.getName();
	}
	
	@Override
	public EntityType getType() {
		return this.type;
	}
	
	@Override
	public boolean apply(Entity entity) {
		return false;
	}
	
	@Override
	public boolean contains(Entity entity) {
		return entity.getType().equals(this.type);
	}
	
	@Override
	public boolean apply(Entity entity, Player player) {
		return false;
	}
	
	@Override
	public boolean contains(Entity entity, Player player) {
		return this.contains(entity);
	}
}
