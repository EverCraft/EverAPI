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

import java.util.Optional;

import org.spongepowered.api.world.storage.WorldProperties;

import fr.evercraft.everapi.java.UtilsBoolean;

public class GameRule<T> {	
	
	private final String name;
	private final Class<T> type;
	
	public GameRule(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Class<T> getType() {
		return this.type;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<T> getValue(WorldProperties world) {
		Optional<String> value = world.getGameRule(this.name);
		
		if (value.isPresent()) {
			if (this.type.equals(String.class)) {
				return (Optional<T>) UtilsBoolean.parseBoolean(value.get());
			} else if (this.type.equals(Boolean.class)) {
				return (Optional<T>) UtilsBoolean.parseBoolean(value.get());
			} else if (this.type.equals(Integer.class)) {
				try {
					return (Optional<T>) Optional.of(Integer.parseInt(value.get()));
				} catch (NumberFormatException e) {}
			} else if (this.type.equals(Double.class)) {
				try {
					return (Optional<T>) Optional.of(Double.parseDouble(value.get()));
				} catch (NumberFormatException e) {}
			}
		}
		return Optional.empty();
	}

}
