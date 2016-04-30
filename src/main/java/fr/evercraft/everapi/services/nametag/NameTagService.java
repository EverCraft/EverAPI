package fr.evercraft.everapi.services.nametag;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface NameTagService {

	public boolean sendNameTag(Player player, String identifier, Text teamRepresentation, Text prefix, Text suffix);
	
	public boolean removeNameTag(Player player, String identifier, Text teamRepresentation);
	
	public boolean clearNameTag(Player player, String identifier);
	
	public boolean has(UUID uuid);
	
	public Optional<String> get(UUID uuid);

}
