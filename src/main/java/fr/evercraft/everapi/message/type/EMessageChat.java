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

import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.replace.EReplace;

public class EMessageChat {
	
	private final EFormat message;
	
	private final boolean prefix;

	public EMessageChat(final EFormat message, final boolean prefix) {
		this.message = message;
		this.prefix = prefix;
	}

	public EFormat getMessage() {
		return this.message;
	}

	public boolean isPrefix() {
		return this.prefix;
	}

	public void send(EFormat prefix, CommandSource player, Map<String, EReplace<?>> replaces) {
		if (this.prefix) {
			player.sendMessage(prefix.toText().concat(this.message.toText(replaces)));
		} else {
			player.sendMessage(this.message.toText(replaces));
		}
	}
}
