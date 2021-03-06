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
package fr.evercraft.everapi;

import java.util.Arrays;
import java.util.List;

import fr.evercraft.everapi.plugin.file.EConfig;

public class EAConfig extends EConfig<EverAPI> {

	public EAConfig(final EverAPI plugin) {
		super(plugin);
	}
	
	public void reload() {
		super.reload();
		this.plugin.getELogger().setDebug(this.isDebug());
	}
	
	@Override
	public List<String> getHeader() {
		return 	Arrays.asList(	"####################################################### #",
								"                    EverAPI (By rexbut)                  #",
								"    For more information : https://docs.evercraft.fr     #",
								"####################################################### #");
	}
	
	@Override
	public void loadDefault() {
		this.configDefault();

		addDefault("server-name", "EverCraft", "Value is used by other plugins (Global Variables)");
		
		addDefault("format.date", "dd MMM yyyy", "Value is used by other plugins (Global Variables)");
		addDefault("format.time", "HH:mm", "Value is used by other plugins (Global Variables)");
		addDefault("format.datetime", "dd MMM yyyy HH:mm:ss", "Value is used by other plugins (Global Variables)");
		
		addDefault("max-caractere", 16, "Value is used by other plugins (home, jail ...)");
		
		addDefault("location.minX", -30000);
		addDefault("location.maxX", 30000);
		addDefault("location.minY", 0);
		addDefault("location.maxY", 255);
		addDefault("location.minZ", -30000);
		addDefault("location.maxZ", 30000);
		
		// World
		addDefault("world-permissions", false, "Set to true to enable per-world permissions for teleporting between worlds with commands.",
														"This applies to /world, /back, /tp[a|o][here|all].",
														"Give someone permission to teleport to a world with everapi.worlds.<worldname>");
	}
	
	/*
	 * Server
	 */
	
	public String getServerName() {
		return this.get("server-name").getString("MineCraft");
	}
	
	/*
	 * Format
	 */
	
	public String getFormatDate(){
		return this.get("format.date").getString("dd MMM yyyy");
	}

	public String getFormatTime() {
		return this.get("format.time").getString("HH:mm");
	}

	public String getFormatDateTime() {
		return this.get("format.datetime").getString("dd MMM yyyy HH:mm:ss");
	}
	
	public int getMaxCaractere() {
		return this.get("max-caractere").getInt(16);
	}
	
	/*
	 * World
	 */
	
	public boolean isWorldPermissions() {
		return this.get("world-permissions").getBoolean(false);
	}
}
