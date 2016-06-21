package fr.evercraft.everapi.plugin;

import java.util.HashMap;
import java.util.Map;

public abstract class EPermission {
	private final EPlugin plugin;
	
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
	public EPermission(final EPlugin plugin) {
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
		if(this.permissions.containsKey(key)){
			return this. prefix + this.permissions.get(key);
		}
		this.plugin.getLogger().warn("La permission '" + key + "' n'est pas définit");
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
