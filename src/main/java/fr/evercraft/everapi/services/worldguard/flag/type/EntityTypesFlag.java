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
package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;
public abstract class EntityTypesFlag extends SetFlag<EntityType> {

	public EntityTypesFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		return Sponge.getGame().getRegistry().getAllOf(EntityType.class).stream()
				.map(type -> type.getId())
				.collect(Collectors.toSet());
	}

	@Override
	public String subSerialize(EntityType value) {
		return value.getId();
	}

	@Override
	public EntityType subDeserialize(String value) throws IllegalArgumentException {
		Optional<EntityType> type = Sponge.getGame().getRegistry().getType(EntityType.class, value);
		if (!type.isPresent()) {
			throw new IllegalArgumentException();
		}
		
		return type.get();
	}
}
