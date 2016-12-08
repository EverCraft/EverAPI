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

import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializer;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.type.EMessageActionBar;
import fr.evercraft.everapi.message.type.EMessageBossBar;
import fr.evercraft.everapi.message.type.EMessageChat;
import fr.evercraft.everapi.message.type.EMessageTitle;

public final class EMessageBuilder {
	private Text prefix;
	
	private EMessageChat chat;
	private EMessageActionBar actionbar;
	private EMessageTitle title;
	private EMessageBossBar bossbar;
	
	public EMessageBuilder() {
		this.clear();
	}
	
	public EMessageBuilder chat(final String message, final EMessageType format, final boolean prefix) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(format, "format");
		Preconditions.checkArgument(!message.isEmpty(), "Message is empty");
		
		this.chat = new EMessageChat(message, format, prefix);
		return this;
	}
	
	public EMessageBuilder actionbar(final Object message, final EMessageType format, final double stay, final String priority, final boolean prefix) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(format, "format");
		Preconditions.checkNotNull(priority, "priority");
		Preconditions.checkArgument(!priority.isEmpty(), "Priority is empty");
		
		this.actionbar = new EMessageActionBar(message, format, stay, priority, prefix);
		return this;
	}
	
	public EMessageBuilder title(final Object message, final EMessageType format, final boolean prefix, 
			final Object sub_message, final EMessageType sub_format, final boolean sub_prefix,
			final double stay, final double fadeIn, final double fadeOut, final String priority) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(format, "format");
		Preconditions.checkNotNull(sub_message, "sub_message");
		Preconditions.checkNotNull(sub_format, "sub_format");
		Preconditions.checkNotNull(priority, "priority");
		Preconditions.checkArgument(!priority.isEmpty(), "Priority is empty");
		
		this.title = new EMessageTitle(message, format, prefix, sub_message, sub_format, sub_prefix, stay, fadeIn, fadeOut, priority);
		return this;
	}
	
	public EMessageBuilder bossbar(final Object message, final EMessageType format, final double stay, float percent, BossBarColor color, final BossBarOverlay overlay, 
			final boolean darkenSky, final boolean playEndBossMusic, final boolean createFog, final String priority, final boolean prefix) {
		Preconditions.checkNotNull(message, "message");
		Preconditions.checkNotNull(format, "format");
		Preconditions.checkNotNull(color, "color");
		Preconditions.checkNotNull(overlay, "overlay");
		Preconditions.checkNotNull(priority, "priority");
		Preconditions.checkArgument(!priority.isEmpty(), "Priority is empty");
		
		this.bossbar = new EMessageBossBar(message, format, stay, percent, color, overlay, darkenSky, playEndBossMusic, createFog, priority, prefix);
		return this;
	}
	
	public EMessageBuilder prefix(final String prefix, final TextSerializer format) {
		Preconditions.checkNotNull(prefix, "prefix");
		
		this.prefix = format.deserialize(prefix);
		return this;
	}
	
	public EMessageBuilder clear() {
		this.prefix = Text.EMPTY;
		this.chat = null;
		this.actionbar = null;
		this.title = null;
		this.bossbar = null;
		return this;
	}
	
	public EMessageFormat build() {
		return new EMessageFormat(this.prefix, Optional.ofNullable(chat), Optional.ofNullable(actionbar), Optional.ofNullable(title), Optional.ofNullable(bossbar));
	}
}
