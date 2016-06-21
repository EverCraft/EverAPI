package fr.evercraft.everapi.sponge;

import fr.evercraft.everapi.EverAPI;

public class ManagerUtils {
	private final EverAPI plugin;
	
	private final UtilsDate date;
	private final UtilsLocation location;
	private final UtilsGameMode gamemode;
	
	public ManagerUtils(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.date = new UtilsDate(this.plugin);
		this.location = new UtilsLocation(this.plugin);
		this.gamemode = new UtilsGameMode(this.plugin);
	}
	
	public void reload() {
		this.location.reload();
		this.date.reload();
	}
	
	public UtilsDate getDate(){
		return this.date;
	}
	
	public UtilsLocation getLocation(){
		return this.location;
	}

	public UtilsGameMode getGameMode() {
		return gamemode;
	}
}
