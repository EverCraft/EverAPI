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

public class EMessageActionBar {
	
	/*
	 * String ou TextTemplate
	 */
	private final Object message;
	private final EMessageType format;
	private final double stay;
	
	private final String priority;
	private final boolean prefix;

	public EMessageActionBar(final Object message, final EMessageType format, final double stay, final String priority, final boolean prefix) {
		this.message = message;
		this.format = format;
		this.stay = stay;
		this.priority = priority;
		this.prefix = prefix;
	}

	public String getMessage() {
		return this.message.toString();
	}

	public EMessageType getFormat() {
		return this.format;
	}

	public double getStay() {
		return this.stay;
	}

	public String getPriority() {
		return this.priority;
	}

	public boolean isPrefix() {
		return this.prefix;
	}

	public Object send(Text prefix, EPlayer player, Map<String, Object> replaces) {
		// TODO Auto-generated method stub
		return null;
	}
}
