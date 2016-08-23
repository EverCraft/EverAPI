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
package fr.evercraft.everapi.services.worldguard.flag;

import java.util.Set;
import java.util.TreeSet;

import org.spongepowered.api.entity.EntityType;

public class FlagSetEntity extends Flag {
	
	private Set<EntityType> value;
	
	public FlagSetEntity(String name) {
		this(name, new TreeSet<EntityType>());
	}
	
	public FlagSetEntity(String name, Set<EntityType> value) {
		super(name);
		
		this.value = value;
	}
	
	public Set<EntityType> getValue() {
		return this.value;
	}
	
	public boolean setValue(Set<EntityType> value) {
		if (this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
