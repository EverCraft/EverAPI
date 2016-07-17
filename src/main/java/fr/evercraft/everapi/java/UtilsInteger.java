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
package fr.evercraft.everapi.java;

import java.util.Random;

public class UtilsInteger {
	
	/*
	 * Retournne une valeur random selon un range
	 */
	
	public static int range(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		} else {
			Random random = new Random();
			return random.nextInt(max) + min;
		}
	}
}
