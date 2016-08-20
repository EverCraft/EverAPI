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

import com.flowpowered.math.vector.Vector3i;

public class FlagLocation extends Flag {
	
	private Vector3i value;
	
	public FlagLocation(String name) {
		super(name);
		
		this.value = new Vector3i(0, 0, 0);
	}
	
	public FlagLocation(String name, Vector3i value) {
		super(name);
		
		this.value = value;
	}
	
	public Vector3i getValue() {
		return this.value;
	}
	
	public boolean setValue(Vector3i value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
