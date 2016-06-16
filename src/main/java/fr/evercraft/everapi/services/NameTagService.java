package fr.evercraft.everapi.services;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.player.EPlayer;

public interface NameTagService {

	public boolean sendNameTag(EPlayer player, String identifier, Text teamRepresentation, Text prefix, Text suffix);
	
	public boolean removeNameTag(EPlayer player, String identifier, Text teamRepresentation);
	
	public boolean clearNameTag(EPlayer player, String identifier);
	
	public boolean has(UUID uuid);
	
	public Optional<String> get(UUID uuid);

}
