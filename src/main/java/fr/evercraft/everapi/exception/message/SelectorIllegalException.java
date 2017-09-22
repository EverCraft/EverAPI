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
package fr.evercraft.everapi.exception.message;

import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class SelectorIllegalException extends EMessageException {
	private static final long serialVersionUID = 8556926768806244620L;
	
	private final CommandSource source;
	private final String selector;
	
	public SelectorIllegalException(final CommandSource source, final String selector) {
		super();
		
		this.source = source;
		this.selector = selector;
	}
	
	public void execute(EnumMessage prefix) {
		EAMessages.SELECTOR_ILLEGAL.sender()
			.prefix(prefix)
			.replace("{selector}", this.selector)
			.sendTo(this.source);
	}
}
