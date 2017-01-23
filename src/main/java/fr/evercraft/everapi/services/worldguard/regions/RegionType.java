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

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import fr.evercraft.everapi.EAMessage.EAMessages;

public enum  RegionType {
	CUBOID(EAMessages.REGION_TYPE_CUBOID, EAMessages.REGION_TYPE_CUBOID_HOVER),
	POLYGONAL(EAMessages.REGION_TYPE_POLYGONAL, EAMessages.REGION_TYPE_POLYGONAL_HOVER),
	TEMPLATE(EAMessages.REGION_TYPE_TEMPLATE, EAMessages.REGION_TYPE_TEMPLATE_HOVER),
	GLOBAL(EAMessages.REGION_TYPE_GLOBAL, EAMessages.REGION_TYPE_GLOBAL_HOVER);

	private final EAMessages name;
	private final EAMessages format;
	
	RegionType(EAMessages name, EAMessages format) {
		this.name = name;
		this.format = format;
    }
	
	public String getName() {
        return this.name.getString();
    }
	
	public Text getHover() {
        return this.format.getText();
    }
	
	public Text getNameFormat() {
        return this.name.getText().toBuilder()
        				.onHover(TextActions.showText(this.format.getText()))
        				.build();
    }

	public static Optional<RegionType> of(String name) {
		RegionType type = null;
		int cpt = 0;
		while(cpt < values().length && type == null){
			if (values()[cpt].name().equalsIgnoreCase(name)) {
				type = values()[cpt];
			}
			cpt++;
		}
		return Optional.ofNullable(type);
	}
}
