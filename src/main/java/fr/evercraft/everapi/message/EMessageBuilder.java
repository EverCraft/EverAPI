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

import org.spongepowered.api.boss.ServerBossBar;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.type.EMessageActionBar;
import fr.evercraft.everapi.message.type.EMessageBossBar;
import fr.evercraft.everapi.message.type.EMessageChat;
import fr.evercraft.everapi.message.type.EMessageTitle;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public final class EMessageBuilder {
	private EnumMessage prefix;
	
	private EMessageChat chat;
	private EMessageActionBar actionbar;
	private EMessageTitle title;
	private EMessageBossBar bossbar;
	
	public EMessageBuilder() {
		this.clear();
	}
	
	public EMessageBuilder chat(final EFormat message, final boolean prefix) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkArgument(!message.isEmpty(), "Message is empty");
		
		this.chat = new EMessageChat(message, prefix);
		return this;
	}
	
	public EMessageBuilder actionbar(final EFormat message, final long stay, final String priority, final boolean prefix) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(priority, "priority");
		Preconditions.checkArgument(!priority.isEmpty(), "Priority is empty");
		
		this.actionbar = new EMessageActionBar(message, stay, priority, prefix);
		return this;
	}
	
	public EMessageBuilder title(final EFormat message, final boolean prefix, 
			final EFormat sub_message, final boolean sub_prefix,
			final int stay, final int fadeIn, final int fadeOut, final String priority) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(sub_message, "sub_message");
		Preconditions.checkNotNull(priority, "priority");
		Preconditions.checkArgument(!priority.isEmpty(), "Priority is empty");
		
		this.title = new EMessageTitle(message, prefix, sub_message, sub_prefix, stay, fadeIn, fadeOut, priority);
		return this;
	}
	
	public EMessageBuilder bossbar(final EFormat message, final long stay, final ServerBossBar bossbar, final String priority, final boolean prefix) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(bossbar, "bossbar");
		Preconditions.checkNotNull(priority, "priority");
		Preconditions.checkArgument(!priority.isEmpty(), "Priority is empty");
		
		this.bossbar = new EMessageBossBar(message, stay, bossbar, priority, prefix);
		return this;
	}
	
	public EMessageBuilder prefix(final EnumMessage format) {
		Preconditions.checkNotNull(prefix, "prefix");
		
		this.prefix = format;
		return this;
	}
	
	public EMessageBuilder clear() {
		this.prefix = null;
		this.chat = null;
		this.actionbar = null;
		this.title = null;
		this.bossbar = null;
		return this;
	}
	
	public EMessageFormat build() {
		Preconditions.checkNotNull(this.prefix, "prefix");
		return new EMessageFormat(this.prefix, Optional.ofNullable(chat), Optional.ofNullable(actionbar), Optional.ofNullable(title), Optional.ofNullable(bossbar));
	}
}
