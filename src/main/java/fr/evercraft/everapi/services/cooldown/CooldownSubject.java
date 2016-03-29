package fr.evercraft.everapi.services.cooldown;

import java.util.Map;

public interface CooldownSubject {
	
	/**
	 * Retourne la liste des cooldowns appliquer au joueur
	 * @return La liste des cooldowns appliquer au joueur
	 */
	public Map<String, Long> getCooldown();

	/**
	 * Ajouter appliquer un cooldown au joueur
	 * @param command La commande
	 * @return True si le cooldown est bien appliqué
	 */
	public boolean addCooldown(final String identifier);
	
	/**
	 * Ajouter appliquer un cooldown au joueur et lance un event à la fin du cooldown
	 * @param command La commande
	 * @return True si le cooldown est bien appliqué
	 */
	public boolean addCooldownScheduler(final String identifier);
	
	/**
	 * Ajouter appliquer un cooldown au joueur
	 * @param command La commande
	 * @return True si le cooldown est bien appliqué
	 */
	public boolean removeCooldown(final String identifier);
	
	/**
	 * Le temps qu'il reste avant la fin du cooldown
	 * @param command La commande
	 * @return Le temps qu'il reste
	 */
	public long getCooldownTime(final String identifier);
	 
}
