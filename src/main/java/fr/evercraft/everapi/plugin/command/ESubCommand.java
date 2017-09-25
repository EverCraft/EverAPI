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
package fr.evercraft.everapi.plugin.command;

import fr.evercraft.everapi.plugin.EPlugin;

public abstract class ESubCommand<T extends EPlugin<T>> extends ECommand<T> implements ISubCommand {
	
	private final String parentName;
	private final String subName;
	
	public ESubCommand(final T plugin, final EParentCommand<T> parent, final String subName) {
		super(plugin, parent.getName() + " " + subName, true);
		
		this.parentName = parent.getName();
		this.subName = subName;
		
		parent.add(this);
	}
	
	public String getParentName() {
		return this.parentName;
	}
	
	public String getSubName() {
		return this.subName;
	}
}
