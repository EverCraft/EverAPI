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

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.regex.Matcher;

import org.spongepowered.api.text.Text;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public abstract class EFormat {

	public abstract boolean isEmpty();
	public abstract Text toText();
	public abstract String toString();
	public abstract Text toText(Map<String, EReplace<?>> replaces);
	
	public String toString(Map<String, EReplace<?>> replaces) {
		String message = this.toString();
		
		for(Entry<String, EReplace<?>> replace : replaces.entrySet()) {
			if (replace.getValue().getPattern().isPresent()) {
				Matcher matcher = replace.getValue().getPattern().get().matcher(message);
				while (matcher.find()) {
					String group = matcher.group(1);
					message = this.toString(message, "<(?i)" + replace.getValue().getPrefix().get() + "=" + group + ">", replace.getValue().get(group));
				    matcher = replace.getValue().getPattern().get().matcher(message);
				}
			} else {
				if (message.contains(replace.getKey())) {
					message = this.toString(message, replace.getKey(), replace.getValue().get(replace.getKey()));
				}
			}
		}
		return message;
	}
	
	public String toString(String key, EReplace<?> replace) {
		String message = this.toString();
		if (replace.getPattern().isPresent()) {
			Matcher matcher = replace.getPattern().get().matcher(message);
			while (matcher.find()) {
				String group = matcher.group(1);
				message = this.toString(message, "<(?i)" + replace.getPrefix().get() + "=" + group + ">", replace.get(group));
			    matcher = replace.getPattern().get().matcher(message);
			}
		} else {
			if (message.contains(key)) {
				message = this.toString(message, key, replace.get(key));
			}
		}
		return message;
	}
	
	private String toString(String message, String key, Object value) {
		if (value instanceof String){
			return message.replaceAll(key, (String) value);
		} else if (value instanceof Text) {
			return message.replaceAll(key, EChat.serialize((Text) value));
		} else if (value instanceof EFormat) {
			return message.replaceAll(key, ((EFormat) value).toString(key, value));
		} else {
			return message.replaceAll(key, value.toString());
		}
	}
	
	protected Text getText(Object value, Map<String, EReplace<?>> replaces) {
		if (value instanceof String){
			return EChat.of((String) value);
		} else if (value instanceof Text) {
			return (Text) value;
		} else if (value instanceof EFormat) {
			return ((EFormat) value).toText(replaces);
		}
		return EChat.of(value.toString());
	}
	
	public Text toText(String k1, Supplier<Object> v1) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public Text toText(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public Text toText(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2, String k3, Supplier<Object> v3) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public Text toText(String k1, Object v1) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3), k4, EReplace.of(v4)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3), k4, EReplace.of(v4), k5, EReplace.of(v5)));
	}
	
	public Text toText(String k1, EReplace<?> v1) {
		return this.toText(ImmutableMap.of(k1, v1));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2) {
		return this.toText(ImmutableMap.of(k1, v1, k2, v2));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2, String k3, EReplace<?> v3) {
		return this.toText(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2, String k3, EReplace<?> v3, String k4, EReplace<?> v4) {
		return this.toText(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4));
	}
	
	public String toString(String k1, Supplier<Object> v1) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public String toString(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public String toString(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2, String k3, Supplier<Object> v3) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public String toString(String k1, Object v1) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3), k4, EReplace.of(v4)));
	}
	
	public boolean isListString() {
		return this instanceof EFormatListString;
	}
	
	public boolean isString() {
		return this instanceof EFormatString;
	}
	
	public boolean isTemplate() {
		return this instanceof EFormatTemplate;
	}
}
