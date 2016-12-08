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
package fr.evercraft.everapi.message.type;

import java.util.Map;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializer;

import fr.evercraft.everapi.message.EMessageType;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageChat {
	
	private final Object message;
	private final EMessageType format;
	
	private final boolean prefix;

	public EMessageChat(final Object message, final EMessageType format, final boolean prefix) {
		this.message = message;
		this.format = format;
		this.prefix = prefix;
	}

	public String getMessage() {
		return message.toString();
	}

	public EMessageType getFormat() {
		return format;
	}

	public boolean isPrefix() {
		return prefix;
	}

	public void send(Text prefix, EPlayer player, Map<String, Object> replaces) {
		
	}
	
	public static Text replace(String message, TextSerializer format, boolean prefix) {
		return null;
	}
}
