/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
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
