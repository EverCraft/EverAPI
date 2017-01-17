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
package fr.evercraft.everapi.message.format;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.TextTemplate.Arg;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public class EFormatTemplate extends EFormat {

	public final TextTemplate message;

	public EFormatTemplate(TextTemplate message) {
		this.message = message;
	}

	@Override
	public boolean isEmpty() {
		return this.message.toText().isEmpty();
	}

	@Override
	public Text toText() {
		return this.message.toText();
	}

	@Override
	public String toString() {
		return EChat.serialize(this.toText());
	}
	
	@Override
	public Text toText(Map<String, EReplace<?>> replaces) {
		HashMap<String, Text> elements = new HashMap<String, Text>();
		for(Entry<String, Arg> arguments : this.message.getArguments().entrySet()) {
			Object value = replaces.get(arguments.getKey());
			if (value != null) {
				elements.put(arguments.getKey(), this.getText(value, replaces));
			}
		}
		return this.message.apply(elements).build();
	}

	public TextTemplate getMessage() {
		return this.message;
	}
}
