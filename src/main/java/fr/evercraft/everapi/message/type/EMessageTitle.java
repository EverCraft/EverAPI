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

import fr.evercraft.everapi.message.EMessageType;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageTitle {
	
	private final Object message;
	private final EMessageType format;
	private final boolean prefix;
	
	private final Object sub_message;
	private final EMessageType sub_format;
	private final boolean sub_prefix;
	
	private final double stay;
	private final double fadeIn;
	private final double fadeOut;
	
	private final String priority;

	public EMessageTitle(final Object message, final EMessageType format, final boolean prefix, 
			final Object sub_message, final EMessageType sub_format, final boolean sub_prefix,
			final double stay, final double fadeIn, final double fadeOut, final String priority) {
		this.message = message;
		this.format = format;
		this.prefix = prefix;
		
		this.sub_message = sub_message;
		this.sub_format = sub_format;
		this.sub_prefix = sub_prefix;
		
		this.stay = stay;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		
		this.priority = priority;
	}

	public String getMessage() {
		return this.message.toString();
	}

	public EMessageType getFormat() {
		return this.format;
	}
	
	public boolean isPrefix() {
		return this.prefix;
	}
	
	public String getSubMessage() {
		return this.sub_message.toString();
	}

	public EMessageType getSubFormat() {
		return this.sub_format;
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

	public Object send(Text prefix, EPlayer player, Map<String, Object> replaces) {
		// TODO Auto-generated method stub
		return null;
	}
}
