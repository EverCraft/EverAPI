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
package fr.evercraft.everapi.services.mojang;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.MojangService;
import fr.evercraft.everapi.services.mojang.check.MojangCheck;
import fr.evercraft.everapi.services.mojang.namehistory.MojangNameHistory;

public class EMojangService implements MojangService {
	private final EverAPI plugin;
	
	private final MojangCheck check;
	private final MojangNameHistory nameHistory;
	
	public EMojangService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.check = new MojangCheck(this.plugin);
		this.nameHistory = new MojangNameHistory(this.plugin);
	}
	
	public void reload() {
		this.check.reload();
	}
	
	public MojangCheck getCheck() {
		return this.check;
	}
	
	public MojangNameHistory getNameHistory() {
		return this.nameHistory;
	}
}
