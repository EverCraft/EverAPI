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
package fr.evercraft.everapi.plugin.file;

import java.util.List;

import org.spongepowered.api.event.Listener;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.event.ChatSystemEvent;
import fr.evercraft.everapi.plugin.EPlugin;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class EMessage extends EFile {
	public static final String FRENCH = "FR_fr";
	public static final String ENGLISH = "EN_en";
	
	private final EnumMessage[] enum_message;
	
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     */    
    public EMessage(final EPlugin plugin, final EnumMessage[] enum_message){
    	super(plugin, plugin.getConfigs().getLanguage(), true);
    	
    	this.plugin.getGame().getEventManager().registerListeners(this.plugin, this);
    	
    	this.enum_message = enum_message;
    	
    	reload();
    }
    
    @Listener
    public void chatService(ChatSystemEvent.Reload event) {
    	this.load();
    }
    
    /**
     * Charge la configuration
     */
    public void reload() {    	
    	this.loadFile();
    	this.load();
    	
    	this.save();
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
}
