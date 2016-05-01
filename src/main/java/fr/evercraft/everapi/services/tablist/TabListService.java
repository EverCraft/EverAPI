package fr.evercraft.everapi.services.tablist;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface TabListService {

	public boolean sendTabList(Player player, String identifier, Text teamRepresentation, Text prefix, Text suffix);
	
	public boolean removeTabList(Player player, String identifier, Text teamRepresentation);
	
	public boolean clearTabList(Player player, String identifier);
	
	public boolean has(UUID uuid);
	
	public Optional<String> get(UUID uuid);

}
