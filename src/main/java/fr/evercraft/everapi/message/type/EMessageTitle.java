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
import java.util.function.Supplier;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageTitle {
	
	private final EFormat message;
	private final boolean prefix;
	
	private final EFormat sub_message;
	private final boolean sub_prefix;
	
	private final double stay;
	private final double fadeIn;
	private final double fadeOut;
	
	private final String priority;

	public EMessageTitle(final EFormat message, final boolean prefix, 
			final EFormat sub_message, final boolean sub_prefix,
			final double stay, final double fadeIn, final double fadeOut, final String priority) {
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

	public double getStay() {
		return this.stay;
	}
	
	public double getFadeIn() {
		return this.fadeIn;
	}
	
	public double getFadeOut() {
		return this.fadeOut;
	}

	public String getPriority() {
		return this.priority;
	}

	public void send(EFormat prefix, EPlayer player, Map<String, Supplier<Object>> replaces) {
		Text title;
		if (!this.message.isEmpty()) {
			
		} else {
			title = Text.EMPTY;
		}
		
		Text sub_title;
	}
}