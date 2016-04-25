package fr.evercraft.everapi.servives.actionbar;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface ActionBarService {
	/**
	 * Envoyer une message dans l'ActionBar d'un joueur
	 * @param player Le joueur
	 * @param id L'identifiant de l'ActionBar
	 * @param stay Le temps d'apparition de l'ActionBar en Milliseconds
	 * @param message Le message a affiché
	 * @return True si l'ActionBar a bien été ajouté
	 */
	public boolean send(Player player, String id, long stay, Text message);
	
	/**
	 * Envoyer une message dans l'ActionBar d'un joueur
	 * @param player Le joueur
	 * @param priority La priorité de l'ActionBar
	 * @param stay Le temps d'apparition de l'ActionBar en Milliseconds
	 * @param message Le message a affiché
	 * @return True si l'ActionBar a bien été ajouté
	 */
	public boolean send(Player player, int priority, long stay, Text message);
	
	public boolean has(UUID uuid);
	public Optional<ActionBarMessage> get(UUID uuid);
}
