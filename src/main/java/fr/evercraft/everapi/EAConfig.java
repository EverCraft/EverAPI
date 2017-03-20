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

import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EMessage;

public class EAConfig extends EConfig<EverAPI> {

	public EAConfig(final EverAPI plugin) {
		super(plugin);
	}
	
	public void reload() {
		super.reload();
		this.plugin.getELogger().setDebug(this.isDebug());
	}
	
	@Override
	public void loadDefault() {
		addDefault("DEBUG", false, "Displays plugin performance in the logs");
		addDefault("LANGUAGE", EMessage.FRENCH, "Select language messages", "Examples : ", "  French : FR_fr", "  English : EN_en");

		addDefault("server.name", "EverCraft");
		
		addDefault("format.date", "dd MMM yyyy");
		addDefault("format.time", "HH:mm");
		addDefault("format.datetime", "dd MMM yyyy HH:mm:ss");
		
		addDefault("maxCaractere", 16);
		
		addDefault("location.minX", -30000);
		addDefault("location.maxX", 30000);
		addDefault("location.minY", 0);
		addDefault("location.maxY", 255);
		addDefault("location.minZ", -30000);
		addDefault("location.maxZ", 30000);
	}
	
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
		return this.get("maxCaractere").getInt(16);
	}
}
