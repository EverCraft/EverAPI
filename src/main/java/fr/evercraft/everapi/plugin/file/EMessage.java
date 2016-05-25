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

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.text.Text;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.chat.event.ChatSystemEvent;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class EMessage extends EFile {
	public static final String FRENCH = "FR_fr";
	public static final String ENGLISH = "EN_en";
	
	private final ConcurrentMap<String, String> messages;
	private final ConcurrentMap<String, List<String>> listsMessages;
	
	private final EnumMessage[] enum_message;
	
	public EMessage(final EPlugin plugin){
		 this(plugin, null);
	}
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     */    
    public <T extends Enum<T> & EnumMessage> EMessage(final EPlugin plugin, final EnumMessage[] enum_message){
    	super(plugin, plugin.getConfigs().getLanguage(), true);
    	
    	this.messages = new ConcurrentHashMap<String, String>();
    	this.listsMessages = new ConcurrentHashMap<String, List<String>>();
    	
    	this.plugin.getGame().getEventManager().registerListeners(this.plugin, this);
    	
    	this.enum_message = enum_message;
    	
    	reload();
    }
    
    @Listener
    public void chatService(ChatSystemEvent event) {
    	if(event.getAction().equals(ChatSystemEvent.Action.RELOADED)) {
    		this.messages.clear();
        	this.listsMessages.clear();
        	
    		this.loadConfig();
    	}
    }
    
    /**
     * Charge la configuration
     */
    public void reload(){
    	this.messages.clear();
    	this.listsMessages.clear();
    	
    	this.loadFile();
    	
    	this.loadDefault();
    	this.loadConfig();
    	
    	this.save();
    }
    
    /**
     * Ajoute un message à la liste
     * @param key Le nom du message
     * @param value Le nom du message a ajouté
     */
    public void addMessage(final String key, final String value) {
    	ConfigurationNode resultat = get(value);
    	if(resultat != null && key != null){
    		if(this.plugin.getChat() != null) {
    			this.messages.put(key.toUpperCase(), this.plugin.getChat().replace(resultat.getString("")));
    		} else {
    			this.messages.put(key.toUpperCase(), resultat.getString(""));
    		}
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
    			if(this.plugin.getChat() != null) {
    				this.listsMessages.put(key.toUpperCase(), this.plugin.getChat().replace(resultat.getList(TypeToken.of(String.class))));
    			} else {
    				this.listsMessages.put(key.toUpperCase(), resultat.getList(TypeToken.of(String.class)));
    			}
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
    
    public boolean hasMessage(String key) {
		return !getMessage(key).isEmpty();
	}
    
    /**
     * Retourne le message en fonction de son nom
     * @param key Le nom du message
     * @return Le message
     */
    public Text getText(final String key) {
    	return EChat.of(getMessage(key));
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
    	return EChat.of(getListMessage(key));
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
    
    public void add(final String key, final String paths, final Object french, final Object english){
    	ConfigurationNode node = get(paths);
    	if(node != null && key != null) {
    		if(node.getValue() == null){
    			if(this.name.equals(FRENCH)) {
        			node.setValue(french);
        		} else {
        			node.setValue(english);
        		}
        	}
    		
    		if(node.getValue() instanceof List) {
    			try {
        			if(this.plugin.getChat() != null) {
        				this.listsMessages.put(key, this.plugin.getChat().replace(node.getList(TypeToken.of(String.class))));
        			} else {
        				this.listsMessages.put(key, node.getList(TypeToken.of(String.class)));
        			}
    			} catch (ObjectMappingException e) {
    				this.plugin.getLogger().warn("Impossible de charger la liste des messages : '" + key + "'");
    			}
    		} else {
    			if(this.plugin.getChat() != null) {
	    			this.messages.put(key, this.plugin.getChat().replace(node.getString("")));
	    		} else {
	    			this.messages.put(key, node.getString(""));
	    		}
    		}
    	} else {
    		this.plugin.getLogger().warn("Le message '" + key + "' n'est pas définit");
    	}
    }
    
    public void load() {
    	for(EnumMessage message : this.enum_message) {
    		ConfigurationNode node = get(message.getPath());
        	if(node != null) {
        		if(node.getValue() == null){
        			if(this.name.equals(FRENCH)) {
            			node.setValue(message.getFrench());
            		} else {
            			node.setValue(message.getEnglish());
            		}
            	}
        		
        		if(node.getValue() instanceof List) {
        			try {
            			if(this.plugin.getChat() != null) {
            				message.set(this.plugin.getChat().replace(node.getList(TypeToken.of(String.class))));
            			} else {
            				message.set(node.getList(TypeToken.of(String.class)));
            			}
        			} catch (ObjectMappingException e) {
        				this.plugin.getLogger().warn("Impossible de charger la liste des messages : '" + message.getName() + "'");
        				message.set("Impossible de charger la liste des messages : '" + message.getName() + "'");
        			}
        		} else {
        			if(this.plugin.getChat() != null) {
        				message.set(this.plugin.getChat().replace(node.getString("")));
    	    		} else {
    	    			message.set(node.getString(""));
    	    		}
        		}
        	} else {
        		this.plugin.getLogger().warn("Le message '" + message.getName() + "' n'est pas définit");
        		message.set("Le message '" + message.getName() + "' n'est pas définit");
        	}
    	}
    }
    
    /**
     * Retourne le message en fonction de son nom
     * @param key Le nom du message
     * @return Le message
     */
    public String getMessage(final EnumMessage key) {
    	String name = key.getName().toUpperCase();
    	if(this.messages.containsKey(name)){
    		return this.messages.get(name);
    	}
    	this.plugin.getLogger().warn("Le message '" + name + "' n'est pas définit");
    	return "";
    }
    
    /**
     * Retourne le message en fonction de son nom
     * @param key Le nom du message
     * @return Le message
     */
    public Text getText(final EnumMessage key) {
    	return EChat.of(getMessage(key.getName()));
    }
    
    /**
     * Retourne une liste de messages en fonction leur nom
     * @param key Le nom du message
     * @return Une liste de message
     */
    public List<String> getListMessage(EnumMessage key) {
    	String name = key.getName().toUpperCase();
    	if(this.listsMessages.containsKey(name)){
    		return this.listsMessages.get(name);
    	}
    	this.plugin.getLogger().warn("La liste des messages '" + name + "' n'est pas définit");
    	return Arrays.asList();
    }
    
    /**
     * Retourne une liste de texts en fonction leur nom
     * @param key Le nom du message
     * @return Une liste de texts
     */
    public List<Text> getListText(final EnumMessage key) {
    	return EChat.of(getListMessage(key));
    }
    
    /**
     * Chargement de tous les éléments
     */
    public abstract void loadConfig();
    
    public Text getTextPrefix() {
    	return this.getText("PREFIX");
    }
    
    public String getMessagePrefix() {
    	return this.getMessage("PREFIX");
    }
}
