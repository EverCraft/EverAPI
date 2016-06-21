package fr.evercraft.everapi;

import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EMessage;

public class EAConfig extends EConfig {

	public EAConfig(final EverAPI plugin) {
		super(plugin);
	}
	
	public void reload() {
		super.reload();
		this.plugin.getLogger().setDebug(this.isDebug());
	}
	
	@Override
	public void loadDefault() {
		addDefault("debug", false, "Displays plugin performance in the logs");
		addDefault("language", EMessage.ENGLISH, "Select language messages", "Examples : ", "  French : FR_fr", "  English : EN_en");

		addDefault("server-name", "EverCraft");
		
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
}
