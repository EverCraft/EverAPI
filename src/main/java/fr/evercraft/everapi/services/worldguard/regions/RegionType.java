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
package fr.evercraft.everapi.services.worldguard.regions;

import java.util.Optional;

public enum  RegionType {
	CUBOID("cuboid"),
	POLYGONAL("poly2d"),
	TEMPLATE("template"),
	GLOBAL("global");

	private final String name;

	RegionType(final String name) {
        this.name = name;
    }
	
	public String getName() {
        return this.name;
    }

	public static Optional<RegionType> of(String name) {
		RegionType type = null;
		int cpt = 0;
		while(cpt < values().length && type == null){
			if (values()[cpt].getName().equalsIgnoreCase(name)) {
				type = values()[cpt];
			}
			cpt++;
		}
		return Optional.ofNullable(type);
	}
}
