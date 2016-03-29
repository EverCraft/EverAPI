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
package fr.evercraft.everapi;

import fr.evercraft.everapi.plugin.EPermission;
import fr.evercraft.everapi.plugin.EPlugin;

public class EAPermission extends EPermission {

	public EAPermission(final EPlugin plugin) {
		super(plugin);
	}

	@Override
	protected void load() {
		add("EVERAPI", "command");
		
		add("HELP", "help");
		add("RELOAD", "reload");
		add("PLUGINS", "plugins");
		
		add("VIEW_OTHERS", "view.others");
		
		add("WORLDS", "worlds");
		
		add("COOLDOWN", "cooldown");
		add("COOLDOWN_BYPASS", "cooldown.bypass");
	}
}
