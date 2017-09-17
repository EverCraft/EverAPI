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
package fr.evercraft.everapi.sponge;

import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.golem.Golem;
import org.spongepowered.api.entity.living.monster.Monster;

import com.google.common.collect.ImmutableSet;

public class UtilsEntityType {
	
	public static final Set<EntityType> ANIMALS;
	public static final Set<EntityType> MONSTERS;
	public static final Set<EntityType> OTHERS;
	
	static {
		ANIMALS = Sponge.getGame().getRegistry().getAllOf(EntityType.class).stream()
			.filter(entity -> {
				if (entity == EntityTypes.UNKNOWN) return false;
				if (entity == EntityTypes.SQUID) return true;
				
				return Animal.class.isAssignableFrom(entity.getEntityClass());
			})
			.collect(ImmutableSet.toImmutableSet());
		
		MONSTERS = Sponge.getGame().getRegistry().getAllOf(EntityType.class).stream()
				.filter(entity -> {
					if (entity == EntityTypes.UNKNOWN) return false;
					if (entity == EntityTypes.GUARDIAN) return true;
					
					return Golem.class.isAssignableFrom(entity.getEntityClass()) || Monster.class.isAssignableFrom(entity.getEntityClass());
				})
				.collect(ImmutableSet.toImmutableSet());
		
		OTHERS = Sponge.getGame().getRegistry().getAllOf(EntityType.class).stream()
				.filter(entity -> !ANIMALS.contains(entity))
				.filter(entity -> !MONSTERS.contains(entity))
				.filter(entity -> entity != EntityTypes.PLAYER)
				.collect(ImmutableSet.toImmutableSet());
	}
	
	public static boolean isAnimal(Entity entity) {
		if (entity.get(Keys.ANGRY).orElse(false)) return false;

		return ANIMALS.contains(entity.getType());
	}

	public static boolean isMonster(Entity entity) {
		if (!entity.get(Keys.ANGRY).orElse(true)) return false;

		return MONSTERS.contains(entity.getType());
	}
}
