package fr.evercraft.everapi.services.cooldown;

public interface CooldownService {
	public final static String NAME_DEFAULT = "default";
	public final static int DEFAULT = 0;
	
	public CooldownSubject get(String identifier);
	public boolean hasRegistered(String identifier);
}
