package fr.evercraft.everapi.services.title;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.title.Title;

public interface TitleService {
	/**
	 * Envoyer un title à un joueur
	 * @param player Le joueur
	 * @param id L'identifiant de l'ActionBar
	 * @param title Le Title
	 * @return True si l'ActionBar a bien été ajouté
	 */
	public boolean send(Player player, String id, Title title);
	
	/**
	 * Envoyer un title à un joueur
	 * @param player Le joueur
	 * @param priority La priorité de l'ActionBar
	 * @param title Le Title
	 * @return True si l'ActionBar a bien été ajouté
	 */
	public boolean send(Player player, int priority, Title title);
	
	public boolean has(UUID uuid);
	public Optional<TitleMessage> get(UUID uuid);
}
