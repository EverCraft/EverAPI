package fr.evercraft.everapi.services;

import java.util.Optional;
import java.util.UUID;

import fr.evercraft.everapi.server.player.EPlayer;

public interface TabListService {

	public boolean sendTabList(EPlayer player, String identifier);
	
	public boolean sendTabList(EPlayer player, String identifier, int priority);
	
	public boolean removeTabList(EPlayer player, String identifier);
	
	public boolean hasTabList(EPlayer ePlayer, String identifier);
	
	public boolean has(UUID uuid);
	
	public Optional<String> get(UUID uuid);

}
