package fr.evercraft.everapi.services;

import fr.evercraft.everapi.services.mojang.check.MojangCheck;
import fr.evercraft.everapi.services.mojang.namehistory.MojangNameHistory;

public interface MojangService {

	public MojangCheck getCheck();
	
	public MojangNameHistory getNameHistory();
}
