package fr.evercraft.everapi.services.cooldown;

import java.util.UUID;

public interface CooldownService {
	public final static String NAME_DEFAULT = "default";
	public final static int DEFAULT = 0;
	
	public CooldownSubject get(UUID identifier);
	public boolean hasRegistered(UUID identifier);
}
