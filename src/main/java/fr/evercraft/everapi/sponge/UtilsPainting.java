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

import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.Art;

public class UtilsPainting {
	
	public static List<Art> getAll() {
		return Sponge.getRegistry().getAllOf(Art.class).stream().collect(Collectors.toList());
	}
	
	private static int getNumero(List<Art> arts, Art art) {
		int cpt = 0;
		while(cpt < arts.size() && !arts.get(cpt).equals(art)){
			cpt++;
		}
		return cpt;
	}
	
	public static Art next(Art art) {
		List<Art> arts = UtilsPainting.getAll();
		int num = UtilsPainting.getNumero(arts, art) + 1;
		
		if (num >= arts.size()) {
			num = 0;
		}
		return arts.get(num);
	}
}
