/**
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
package fr.evercraft.everapi.services.pagination;

import org.spongepowered.api.text.Text;

public class ESubCommand {
	
	private final Text usage;
	private final Text description;

	public ESubCommand(Text usage, Text description) {
		this.usage = usage;
		this.description = description;
	}
	
	public Text getUsage() {
		return this.usage;
	}

	public Text getDescription() {
		return this.description;
	}
}
