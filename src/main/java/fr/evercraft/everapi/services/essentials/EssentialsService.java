package fr.evercraft.everapi.services.essentials;

import java.util.UUID;

public interface EssentialsService {
	public EssentialsSubject get(UUID uuid);
	public boolean hasRegistered(UUID uuid);
	
	public String getPermissionVanishSee();
}
