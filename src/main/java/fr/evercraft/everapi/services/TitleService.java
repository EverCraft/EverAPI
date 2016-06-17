package fr.evercraft.everapi.services;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.title.TitleMessage;

public interface TitleService {

	public boolean send(EPlayer player, String identifier, Title title);
	
	public boolean send(EPlayer player, String identifier, int priority, Title title);
	
	public boolean remove(EPlayer player, String identifier);
	
	public boolean has(UUID uuid);
	public Optional<TitleMessage> get(UUID uuid);
}
