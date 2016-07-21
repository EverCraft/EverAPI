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

import org.spongepowered.api.text.Text;

public class UtilsBook {
	private final static int LINE_NUMBER = 14;
	
	public static Text verticalCenter(List<Text> line) {
		String rt = "";
		for(int cpt=0; cpt < Math.max(0, ((LINE_NUMBER - line.size()) / 2) - 1); cpt++) {
			rt += "\n";
		}
		return Text.of(rt).concat(Text.joinWith(Text.of("\n"), line));
	}
}