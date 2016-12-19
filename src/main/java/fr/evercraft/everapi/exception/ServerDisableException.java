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
package fr.evercraft.everapi.exception;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EPlugin;

public class ServerDisableException extends Exception {
	private static final long serialVersionUID = 2L;
	private final EPlugin<?> plugin;
	
	public ServerDisableException(final EPlugin<?> plugin, final String message){
		super(message);
		this.plugin = plugin;
	}
	
	public void execute() {
		this.plugin.getLogger().warn(this.getMessage());
		if (this.plugin.getEverAPI() != null && this.plugin.getEverAPI().getMessages() != null) {
			if (this.plugin.getEverAPI().getManagerService() != null && this.plugin.getEverAPI().getManagerService().getMail().isPresent()) {
				this.plugin.getEverAPI().getManagerService().getMail().get().alert(
						EAMessages.MAIL_SERVER_DISABLE_OBJECT.getFormat().toString(
							"<server>", EReplace.of(this.plugin.getEServer().getName()),
							"<reason>", EReplace.of(this.getMessage())
						),
						EAMessages.MAIL_SERVER_DISABLE_MESSAGE.getFormat().toString(
							"<server>", EReplace.of(this.plugin.getEServer().getName()),
							"<reason>", EReplace.of(this.getMessage())
						));
			}
			this.plugin.getEverAPI().disable();
			this.plugin.getGame().getServer().shutdown(EAMessages.SERVER_ERROR.getText());
		} else {
			this.plugin.getGame().getServer().shutdown();
		}
	}
}
