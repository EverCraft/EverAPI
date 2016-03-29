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

import java.util.Arrays;

import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EMessage;

public class EAConfig extends EConfig {

	public EAConfig(final EverAPI plugin) {
		super(plugin);
	}
	
	@Override
	public void loadDefault() {
		addDefault("debug", false, "Displays plugin performance in the logs");
		addDefault("language", EMessage.ENGLISH, "Select language messages", "Examples : ", "  French : FR_fr", "  English : EN_en");
		
		addDefault("MySQL.enable", false);
		addDefault("MySQL.host", "localhost");
		addDefault("MySQL.port", 3306);
		addDefault("MySQL.database", "everapi");
		addDefault("MySQL.username", "root");
		addDefault("MySQL.password", "");
		addDefault("MySQL.prefix", "everapi_");
		
		addDefault("mail.enable", true);
		addDefault("mail.host", "evercraft.fr");
		addDefault("mail.username", "contact");
		addDefault("mail.alerts", Arrays.asList("butbutdu45@gmail.com", "lesbleu@evercraft.fr"));
		
		addDefault("server.name", "EverCraft");
		
		addDefault("maxCaractere", 16);
		
		addDefault("location.minX", -30000);
		addDefault("location.maxX", 30000);
		addDefault("location.minY", 0);
		addDefault("location.maxY", 255);
		addDefault("location.minZ", -30000);
		addDefault("location.maxZ", 30000);
	}
}
