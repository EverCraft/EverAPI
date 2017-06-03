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
package fr.evercraft.everapi.register;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import fr.evercraft.everapi.EAMessage.EAMessages;

public class EFormatCatalogType extends ECatalogType {

	private final EAMessages format;
	
	public EFormatCatalogType(String name, EAMessages format) {
		super(name);
		this.format = format;
    }
	
	public Text getHover() {
        return this.format.getText();
    }
	
	public Text getNameFormat() {
        return Text.builder(this.getName())
        				.onHover(TextActions.showText(this.format.getText()))
        				.onShiftClick(TextActions.insertText(this.getName()))
        				.build();
    }
	
}
