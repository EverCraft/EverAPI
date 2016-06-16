package fr.evercraft.everapi.services;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;

public interface ActionBarService {
	/**
	 * Envoyer une message dans l'ActionBar d'un joueur
	 * @param player Le joueur
	 * @param identifier L'identifiant de l'ActionBar
	 * @param stay Le temps d'apparition de l'ActionBar en Milliseconds
	 * @param message Le message a affiché
	 * @return True si l'ActionBar a bien été ajouté
	 */
	public boolean send(EPlayer player, String identifier, long stay, Text message);
	
	/**
	 * Envoyer une message dans l'ActionBar d'un joueur
	 * @param player Le joueur
	 * @param identifier L'identifiant de l'ActionBar
	 * @param priority La priorité de l'ActionBar
	 * @param stay Le temps d'apparition de l'ActionBar en Milliseconds
	 * @param message Le message a affiché
	 * @return True si l'ActionBar a bien été ajouté
	 */
	public boolean send(EPlayer player, String identifier, int priority, long stay, Text message);
	
	public boolean has(UUID uuid);
	public Optional<ActionBarMessage> get(UUID uuid);
}
