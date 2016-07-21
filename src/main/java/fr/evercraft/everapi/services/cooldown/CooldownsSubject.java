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
import java.util.Optional;

import org.spongepowered.api.service.permission.Subject;

public interface CooldownsSubject {
	
	/**
	 * Retourne la liste des cooldowns appliquer au joueur
	 * @return La liste des cooldowns appliquer au joueur
	 */
	public Map<String, Long> getAll();

	/**
	 * Ajouter appliquer un cooldown au joueur
	 * @param command La commande
	 * @return True si le cooldown est bien appliqué
	 */
	public boolean add(final String command);
	public boolean add(final Subject subject, final String command);
	
	/**
	 * Ajouter appliquer un cooldown au joueur
	 * @param command La commande
	 * @return True si le cooldown est bien appliqué
	 */
	public boolean remove(final String command);
	public boolean clear();
	
	/**
	 * Le temps qu'il reste avant la fin du cooldown
	 * @param command La commande
	 * @return Le temps qu'il reste
	 */
	public Optional<Long> get(final String command);	 
}
