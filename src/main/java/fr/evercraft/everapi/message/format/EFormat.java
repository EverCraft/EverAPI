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
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public abstract class EFormat {

	public abstract boolean isEmpty();
	public abstract Text toText();
	public abstract String toString();
	
	public abstract Text toText(Map<Pattern, EReplace<?>> replaces);
	
	public Text toText2(Map<String, EReplace<?>> replaces) {
		Map<Pattern, EReplace<?>> map = new HashMap<Pattern, EReplace<?>>();
		replaces.forEach((pattern, replace) -> {
			if (pattern.startsWith("{")) pattern = "\\" + pattern;
			map.put(Pattern.compile(pattern), replace);
		});
		return this.toText(map);
	}
	
	public String toString(Map<Pattern, EReplace<?>> replaces) {
		return this.toString(this.toString(), replaces);
	}
	
	public String toString(String message, Map<Pattern, EReplace<?>> replaces) {
		for(Entry<Pattern, EReplace<?>> replace : replaces.entrySet()) {
			Matcher matcher = replace.getKey().matcher(message);
			if (matcher.find()) {
				String group = (matcher.groupCount() > 0) ? matcher.group(1) : "";
				message = this.toString(message, replace.getKey().pattern(), replace.getValue().get(group));
			}
		}
		return message;
	}
	
	public String toString(Pattern pattern, EReplace<?> replace) {
		String message = this.toString();
		Matcher matcher = pattern.matcher(message);
		while (matcher.find()) {
			String group = (matcher.groupCount() > 0) ? matcher.group(1) : "";
			message = this.toString(message, pattern.pattern(), replace.get(group));
		    matcher = pattern.matcher(message);
		}
		return message;
	}
	
	private String toString(String message, String key, Object value) {
		if (value instanceof String){
			return message.replace(key, (String) value);
		} else if (value instanceof Text) {
			return message.replace(key, EChat.serialize((Text) value));
		} else if (value instanceof EFormat) {
			return message.replace(key, ((EFormat) value).toString(key, value));
		} else {
			return message.replace(key, value.toString());
		}
	}
	
	protected Text getText(Object value, Map<Pattern, EReplace<?>> replaces) {
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
		if (k1.startsWith("{")) k1 = "\\" + k1;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1)));
	}
	
	public Text toText(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2)));
	}
	
	public Text toText(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2, String k3, Supplier<Object> v3) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3)));
	}
	
	public Text toText(String k1, Object v1) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		if (k4.startsWith("{")) k4 = "\\" + k4;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3), Pattern.compile(k4), EReplace.of(v4)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		if (k4.startsWith("{")) k4 = "\\" + k4;
		if (k5.startsWith("{")) k5 = "\\" + k5;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3), Pattern.compile(k4), EReplace.of(v4), Pattern.compile(k5), EReplace.of(v5)));
	}
	
	public Text toText(String k1, EReplace<?> v1) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), v1));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), v1, Pattern.compile(k2), v2));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2, String k3, EReplace<?> v3) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), v1, Pattern.compile(k2), v2, Pattern.compile(k3), v3));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2, String k3, EReplace<?> v3, String k4, EReplace<?> v4) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		if (k4.startsWith("{")) k4 = "\\" + k4;
		return this.toText(ImmutableMap.of(Pattern.compile(k1), v1, Pattern.compile(k2), v2, Pattern.compile(k3), v3, Pattern.compile(k4), v4));
	}
	
	public String toString(String k1, Supplier<Object> v1) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1)));
	}
	
	public String toString(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2)));
	}
	
	public String toString(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2, String k3, Supplier<Object> v3) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3)));
	}
	
	public String toString(String k1, Object v1) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
		if (k1.startsWith("{")) k1 = "\\" + k1;
		if (k2.startsWith("{")) k2 = "\\" + k2;
		if (k3.startsWith("{")) k3 = "\\" + k3;
		if (k4.startsWith("{")) k4 = "\\" + k4;
		return this.toString(ImmutableMap.of(Pattern.compile(k1), EReplace.of(v1), Pattern.compile(k2), EReplace.of(v2), Pattern.compile(k3), EReplace.of(v3), Pattern.compile(k4), EReplace.of(v4)));
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
