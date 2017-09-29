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
package fr.evercraft.everapi.services.permission;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;

/**
 * Contient la liste de tous les groupes
 * 
 * @author Rexbut
 */
public interface EGroupCollection extends ESubjectCollection<EGroupSubject> {

	/**
	 * Retourne le groupe par défaut de chaque type de monde
	 * 
	 * @return Le groupe par défaut de chaque type de monde
	 */
	ConcurrentMap<String, EGroupSubject> getDefaultGroups();

	/**
	 * Retourne le groupe par défaut d'un type de monde
	 * 
	 * @param typeWorld Le type de monde
	 * @return Le groupe par défaut d'un type de monde.
	 * Si il n'y a aucun groupe par défaut pour ce type de monde 
	 * la valeur sera EMPTY
	 */
	Optional<EGroupSubject> getDefaultGroup(String typeWorld);

	/**
	 * Retourne la liste des groupes qui sont dans le type de monde
	 * 
	 * @param typeWorld Le type de monde
	 * @return La liste des groupes qui sont dans le type de monde
	 * Si le type de monde n'existe pas la liste sera vide
	 */
	Set<EGroupSubject> getGroups(String typeWorld);

	/**
	 * Permet d'ajouter un groupe à un type de monde
	 * 
	 * @param groupName Le nom du groupe
	 * @param typeGroup Le type de monde
	 * @return L'ajoute dans la base de données est async donc le retour 
	 * est un {@link CompletableFuture}.
	 * <p>
	 * True l'ajout a bien été réalisé</p>
	 * False l'ajout n'a pas été réalisé (Le groupe existé déjà dans ce type 
	 * de monde ou il y a eu une erreur lors de l'insertion à la base de données)
	 * </p>
	 */
	CompletableFuture<Boolean> register(String groupName, String typeGroup);

}
