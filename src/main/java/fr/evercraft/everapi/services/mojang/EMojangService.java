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
