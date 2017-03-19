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
package fr.evercraft.everapi.plugin;

import java.util.HashMap;
import java.util.Map;

public abstract class EPermission {
	private final EPlugin<?> plugin;
	
	/**
	 * La liste des permissions
	 */
	private final Map<String, String> permissions;
	
	/**
	 * Le prefix des permissions
	 */
	private final String prefix;	
	
	/**
	 * Création de Permissions
	 * @param plugin
	 */
	public EPermission(final EPlugin<?> plugin) {
		this.plugin = plugin;
		this.permissions = new HashMap<String, String>();
		this.prefix = this.plugin.getName().toLowerCase() + "." ;
		
		load();
	}
	
	/**
	 * Ajoute une permission à la liste
	 */
	protected void add(final String key, final String value){
		this.permissions.put(key.toUpperCase(), value.toLowerCase());
	}
	
	/**
	 * Retourne la permission en fonction de son nom
	 * @param key Le nom de la permission
	 * @return La permission
	 */
	public String get(String key){
		key = key.toUpperCase();
		if (this.permissions.containsKey(key)){
			return this. prefix + this.permissions.get(key);
		}
		this.plugin.getELogger().warn("La permission '" + key + "' n'est pas définit");
		return "";
	}
	
	/**
	 * Définit la liste des permissions 
	 * 
	 * Exemple :
	 * Le nom du plugin est automatiquement ajouté devant la permission.
	 * add("nom_de_la_permission", "la.permission");
	 */
	protected abstract void load();
}
