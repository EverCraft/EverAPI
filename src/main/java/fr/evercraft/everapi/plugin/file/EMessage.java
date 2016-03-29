/**
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
package fr.evercraft.everapi.plugin.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.text.Text;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.sponge.UtilsChat;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class EMessage extends EFile {
	public static final String FRENCH = "FR_fr";
	public static final String ENGLISH = "EN_en";
	
	private final ConcurrentMap<String, String> messages;
	private final ConcurrentMap<String, List<String>> listsMessages;
	
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     */    
    public EMessage(final EPlugin plugin){
    	super(plugin, plugin.getConfigs().getLanguage(), true);
    	
    	this.messages = new ConcurrentHashMap<String, String>();
    	this.listsMessages = new ConcurrentHashMap<String, List<String>>();
    	
    	reload();
    }
    
    /**
     * Charge la configuration
     */
    public void reload(){
    	this.messages.clear();
    	this.listsMessages.clear();
    	
    	this.loadFile();
    	this.loadDefault();
    	
    	this.save();
    	this.loadConfig();
    }
    
    /**
     * Ajoute un message à la liste
     * @param key Le nom du message
     * @param value Le nom du message a ajouté
     */
    public void addMessage(final String key, final String value) {
    	ConfigurationNode resultat = get(value);
    	if(resultat != null && key != null){
    		this.messages.put(key.toUpperCase(), UtilsChat.replace(resultat.getString("")));
    	} else {
    		this.plugin.getLogger().warn("Le message '" + key + "' n'est pas définit");
    	}
    }
    
    /**
     * Ajoute une liste de messages à la liste
     * @param key Le nom des messages
     * @param value La liste des messages a ajouté
     */
	public void addListMessages(final String key, final String value) {
    	ConfigurationNode resultat = get(value);
    	if(resultat != null && key != null){
    		try {
				this.listsMessages.put(key.toUpperCase(), UtilsChat.replace((List<String>) resultat.getList(TypeToken.of(String.class))));
			} catch (ObjectMappingException e) {
				this.plugin.getLogger().warn("Impossible de charger la liste des messages : '" + key + "'");
			}
    	} else {
    		this.plugin.getLogger().warn("La liste des messages '" + key + "' n'est pas définit");
    	}
    }
    
    /**
     * Retourne le message en fonction de son nom
     * @param key Le nom du message
     * @return Le message
     */
    public String getMessage(String key) {
    	key = key.toUpperCase();
    	if(this.messages.containsKey(key)){
    		return this.messages.get(key);
    	}
    	this.plugin.getLogger().warn("Le message '" + key + "' n'est pas définit");
    	return "";
    }
    
    /**
     * Retourne le message en fonction de son nom
     * @param key Le nom du message
     * @return Le message
     */
    public Text getText(final String key) {
    	return UtilsChat.of(getMessage(key));
    }
    
    /**
     * Retourne une liste de messages en fonction leur nom
     * @param key Le nom du message
     * @return Une liste de message
     */
    public List<String> getListMessage(String key) {
    	key = key.toUpperCase();
    	if(this.listsMessages.containsKey(key)){
    		return this.listsMessages.get(key);
    	}
    	this.plugin.getLogger().warn("La liste des messages '" + key + "' n'est pas définit");
    	return Arrays.asList();
    }
    
    /**
     * Retourne une liste de texts en fonction leur nom
     * @param key Le nom du message
     * @return Une liste de texts
     */
    public List<Text> getListText(final String key) {
    	return UtilsChat.of(getListMessage(key));
    }

    public void addDefault(final String paths, final String french, final String english){
    	CommentedConfigurationNode node = get(paths);    	
    	if(node.getValue() == null){
    		if(this.name.equals(FRENCH)) {
    			node.setValue(french);
    		} else {
    			node.setValue(english);
    		}
    	}
    }

    public List<String> getListString(String paths){
    	ConfigurationNode resultat = get(paths);
    	if(resultat != null){
    		try {
				return resultat.getList(TypeToken.of(String.class));
			} catch (ObjectMappingException e) {}
    	}
    	return new ArrayList<String>();
    }
    
    /**
     * Chargement de tous les éléments
     */
    public abstract void loadConfig();
}