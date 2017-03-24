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

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

public interface EntityTemplate extends CatalogType {
	
	public EntityType getType();
	
	public boolean apply(Entity entity);
	public boolean contains(Entity entity);
	public boolean apply(Entity entity, Player player);
	public boolean contains(Entity entity, Player player);
	
	static EntityTemplate of(EntityType type) {
		return new EntityTypeTemplate(type);
	}
}
