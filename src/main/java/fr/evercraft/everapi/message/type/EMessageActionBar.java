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

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageActionBar {
	
	private final EFormat message;
	private final double stay;
	
	private final String priority;
	private final boolean prefix;

	public EMessageActionBar(final EFormat message, final double stay, final String priority, final boolean prefix) {
		this.message = message;
		this.stay = stay;
		this.priority = priority;
		this.prefix = prefix;
	}

	public EFormat getMessage() {
		return this.message;
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

	public Object send(EFormat eFormat, EPlayer player, Map<String, Supplier<Object>> replaces) {
		// TODO Auto-generated method stub
		return null;
	}
}
