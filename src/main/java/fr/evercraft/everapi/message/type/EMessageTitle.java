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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageTitle {
	
	private final EFormat message;
	private final boolean prefix;
	
	private final EFormat sub_message;
	private final boolean sub_prefix;
	
	private final int stay;
	private final int fadeIn;
	private final int fadeOut;
	
	private final String priority;

	public EMessageTitle(final EFormat message, final boolean prefix, 
			final EFormat sub_message, final boolean sub_prefix,
			final int stay, final int fadeIn, final int fadeOut, final String priority) {
		this.message = message;
		this.prefix = prefix;
		
		this.sub_message = sub_message;
		this.sub_prefix = sub_prefix;
		
		this.stay = stay;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		
		this.priority = priority;
	}

	public EFormat getMessage() {
		return this.message;
	}

	public boolean isPrefix() {
		return this.prefix;
	}
	
	public EFormat getSubMessage() {
		return this.sub_message;
	}
	
	public boolean isSubPrefix() {
		return this.sub_prefix;
	}

	public int getStay() {
		return this.stay;
	}
	
	public int getFadeIn() {
		return this.fadeIn;
	}
	
	public int getFadeOut() {
		return this.fadeOut;
	}

	public String getPriority() {
		return this.priority;
	}

	public void send(EFormat prefix, EPlayer player, Map<String, EReplace<?>> replaces) {
		Text title = Text.EMPTY;
		if (!this.message.isEmpty()) {
			if (this.prefix) {
				title = prefix.toText().toText().concat(this.message.toText(replaces));
			} else {
				title = this.message.toText(replaces);
			}
		}
		
		Text sub_title = Text.EMPTY;
		if (!this.sub_message.isEmpty()) {
			if (this.sub_prefix) {
				title = prefix.toText().toText().concat(this.sub_message.toText(replaces));
			} else {
				title = this.message.toText(replaces);
			}
		}
		
		player.sendTitle(Title.builder()
				.title(title)
				.subtitle(sub_title)
				.fadeIn(this.fadeIn)
				.fadeOut(this.fadeOut)
				.stay(this.stay)
				.build());
	}
	
	public void send(EFormat prefix, CommandSource source, Map<String, EReplace<?>> replaces) {
		if (source instanceof EPlayer) {
			this.send(prefix, (EPlayer) source, replaces);
		} else {
			if (this.prefix) {
				source.sendMessage(prefix.toText().concat(this.message.toText(replaces)));
				source.sendMessage(prefix.toText().concat(this.sub_message.toText(replaces)));
			} else {
				source.sendMessage(this.message.toText(replaces));
				source.sendMessage(this.sub_message.toText(replaces));
			}
		}
	}
}
