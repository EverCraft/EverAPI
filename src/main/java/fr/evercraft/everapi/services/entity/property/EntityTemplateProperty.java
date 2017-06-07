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

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.User;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.java.UtilsPredicate.TriPredicate;
import fr.evercraft.everapi.services.entity.EntityTemplate;

public class EntityTemplateProperty<T> implements EntityTemplate.Property<T> {
	
	private final String identifier;
	private final String name;
	
	private final TypeToken<T> type;
	
	private final TriPredicate<T, Entity, Optional<User>> apply;
	private final TriPredicate<T, Entity, Optional<User>> contains;
	
	public EntityTemplateProperty(String name, TypeToken<T> type, TriPredicate<T, Entity, Optional<User>> apply, TriPredicate<T, Entity, Optional<User>> contains) {
		this("evercraft:" + name, name, type, apply, contains);
	}
	
	public EntityTemplateProperty(String identifier, String name, TypeToken<T> type, 
			TriPredicate<T, Entity, Optional<User>> apply, TriPredicate<T, Entity, Optional<User>> contains) {
		this.identifier = identifier.toLowerCase();
		this.name = name.toUpperCase();
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
	public TypeToken<T> getType() {
		return this.type;
	}
	
	@Override
	public TriPredicate<T, Entity, Optional<User>> getApply() {
		return this.apply;
	}
	
	@Override
	public TriPredicate<T, Entity, Optional<User>> getContains() {
		return this.contains;
	}
}
