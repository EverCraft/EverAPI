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
import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public class EFormatListString extends EFormat {
	
	public final List<String> messages;

	public static EFormatListString of(List<String> messages) {
		return new EFormatListString(messages);
	}
	
	public EFormatListString(List<String> messages) {
		this.messages = messages;
	}

	@Override
	public boolean isEmpty() {
		return this.messages.isEmpty();
	}

	@Override
	public Text toText() {
		return Text.joinWith(Text.of("\n"), EChat.of(this.messages));
	}

	@Override
	public String toString() {
		return String.join("\n", this.messages);
	}
	
	@Override
	public Text toText(Map<Pattern, EReplace<?>> replaces) {
		List<Text> texts = new ArrayList<Text>();
		this.messages.forEach(message -> texts.add(EFormatString.apply(message, replaces)));
		return Text.joinWith(Text.of("\n"), texts);
	}
	
	public List<Text> toListText(Map<Pattern, EReplace<?>> replaces) {
		List<Text> texts = new ArrayList<Text>();
		this.messages.forEach(message -> texts.add(EFormatString.apply(message, replaces)));
		return texts;
	}
	
	public List<String> toListString(Map<Pattern, EReplace<?>> replaces) {
		List<String> strings = new ArrayList<String>();
		this.messages.forEach(message -> strings.add(this.toString(message, replaces)));
		return strings;
	}

	public List<String> getMessage() {
		return this.messages;
	}

}
