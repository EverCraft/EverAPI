package fr.evercraft.everapi.services.essentials;

public interface EssentialsService {
	public EssentialsSubject get(String identifier);
	public boolean hasRegistered(String identifier);
}
