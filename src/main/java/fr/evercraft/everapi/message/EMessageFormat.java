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
package fr.evercraft.everapi.message;

import java.util.Optional;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.type.EMessageActionBar;
import fr.evercraft.everapi.message.type.EMessageBossBar;
import fr.evercraft.everapi.message.type.EMessageChat;
import fr.evercraft.everapi.message.type.EMessageTitle;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public final class EMessageFormat {
	
	private final Optional<EnumMessage> prefix;
	
	private final Optional<EMessageChat> chat;
	private final Optional<EMessageActionBar> actionbar;
	private final Optional<EMessageTitle> title;
	private final Optional<EMessageBossBar> bossbar;
	
	public EMessageFormat(final Optional<EnumMessage> prefix, final Optional<EMessageChat> chat, final Optional<EMessageActionBar> actionbar,
			final Optional<EMessageTitle> title, final Optional<EMessageBossBar> bossbar) {
		this.prefix = prefix;
		this.chat = chat;
		this.actionbar = actionbar;
		this.title = title;
		this.bossbar = bossbar;
	}

	public EFormat getPrefix() {
		return this.prefix.isPresent() ? this.prefix.get().getFormat() : new EFormatString("");
	}
	
	public Optional<EMessageChat> getChat() {
		return chat;
	}

	public Optional<EMessageActionBar> getActionbar() {
		return actionbar;
	}

	public Optional<EMessageTitle> getTitle() {
		return title;
	}

	public Optional<EMessageBossBar> getBossbar() {
		return bossbar;
	}
	
	public static EMessageBuilder builder() {
		return new EMessageBuilder();
	}
	
	public EMessageSender sender() {
		return new EMessageSender(this);
	}
}
