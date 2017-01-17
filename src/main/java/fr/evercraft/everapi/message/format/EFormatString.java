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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public class EFormatString extends EFormat {
	
	public static final EFormatString EMPTY = new EFormatString("");
	
	public static EFormatString of(String message) {
		return new EFormatString(message);
	}
	
	public final String message;

	public EFormatString(String message) {
		this.message = message;
	}

	@Override
	public boolean isEmpty() {
		return this.message.isEmpty();
	}

	@Override
	public Text toText() {
		return EChat.of(this.message);
	}

	@Override
	public String toString() {
		return this.message;
	}
	
	@Override
	public Text toText(Map<String, EReplace<?>> replaces) {
		return EFormatString.apply(this.message, replaces);
	}
	
	public static Text apply(String message, Map<String, EReplace<?>> replaces) {
		List<Object> texts = new ArrayList<Object>();
		texts.add(message);
		
		for (Entry<String, EReplace<?>> replace : replaces.entrySet()) {
			String[] split;
			String text;
			int cpt = 0;
			while(cpt < texts.size()){
				if (texts.get(cpt) instanceof String) {
					text = (String) texts.get(cpt);
					if (text.contains(replace.getKey())) {
						split = text.split(replace.getKey(), 2);
						if (split.length == 2) {
							texts.remove(cpt);
							texts.add(cpt, split[0]);
							cpt++;
							texts.add(cpt, replace.getValue().get());
							cpt++;
							texts.add(cpt, split[1]);
						}
					}
				}
				cpt++;
			}
		}
		
		Builder builder = Text.builder();
		for (Object value : texts) {
			Text text = null;
			if (value instanceof String) {
				text = EChat.of((String) value);
			} else if (value instanceof Text) {
				text = (Text) value;
			} else if (value instanceof EFormat) {
				text = ((EFormat) value).toText(replaces);
			} else {
				text = EChat.of(value.toString());
			}
			
			if (!text.getFormat().isEmpty()) {
				builder.append(text);
			} else {
				builder.append(text.toBuilder().format(EChat.getLastFormat(builder)).build());
			}
		}
		return builder.build();
	}

	public String getMessage() {
		return this.message;
	}
}
