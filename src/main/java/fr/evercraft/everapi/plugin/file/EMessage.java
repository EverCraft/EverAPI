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
import java.util.Optional;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.service.permission.PermissionDescription;
import org.spongepowered.api.service.permission.PermissionService;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.event.ChatSystemEvent;
import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatListString;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.plugin.EnumPermission;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class EMessage<T extends EPlugin<T>> extends EFile<T> {
	public static final String FRENCH = "FR_fr";
	public static final String ENGLISH = "EN_en";
	
	private final EnumMessage[] enum_message;
	
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     */    
    public EMessage(final T plugin, final EnumMessage[] enum_message){
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
    	
    	this.save(false);
    }
    
    public abstract EnumMessage getPrefix();
    
    
    public void load() {
    	Preconditions.checkNotNull(this.getPrefix(), "PREFIX");
    	
    	for (EnumMessage message : this.enum_message) {
			ConfigurationNode node = this.get(message.getPath());
			
			try {
	        	if (node.isVirtual()) {
	    			if (this.name.equals(FRENCH)) {
	    				node.setValue(TypeToken.of(EMessageBuilder.class), message.getFrench());
	        		} else {
	        			node.setValue(TypeToken.of(EMessageBuilder.class), message.getEnglish());
	        		}
	    			this.setModified(true);
	        	}
	        	
	        	try {
	    			message.set(node.getValue(TypeToken.of(EMessageBuilder.class)).prefix(this.getPrefix()));
	    		} catch (ObjectMappingException e) {
	    			this.plugin.getELogger().warn("Impossible de désérialiser : '" + message.getName() + "'");
	    			message.set(EMessageFormat.builder());
	    		}
        	} catch (ObjectMappingException e) {
    			this.plugin.getELogger().warn("Impossible de sérialiser : '" + message.getName() + "'");
    			message.set(EMessageFormat.builder());
			}
    	}
    	
    	PermissionService servicePermission = this.plugin.getGame().getServiceManager().provide(PermissionService.class).orElse(null);
    	if (servicePermission == null) return;
    	for (EnumPermission permission : this.plugin.getPermissions()) {
    		servicePermission.newDescriptionBuilder(this.plugin)
    			.id(permission.get())
    			.description(permission.getMessage().getText())
    			.assign(PermissionDescription.ROLE_USER, permission.getDefault())
    			.register();
    	}
    }
    
    public EMessageBuilder getList(String name, EMessageBuilder builder, ConfigurationNode node, boolean prefix) {
    	try {
			List<String> messages = node.getList(TypeToken.of(String.class));
			if (!messages.isEmpty()) {
    			if (this.plugin.getChat() != null) {
    				builder.chat(new EFormatListString(this.plugin.getChat().replace(messages)), prefix);
    			} else {
    				builder.chat(new EFormatListString(node.getList(TypeToken.of(String.class))), prefix);
    			}
			}
		} catch (ObjectMappingException e) {
			this.plugin.getELogger().warn("Impossible de charger la liste des messages : '" + name + "'");
			builder.chat(new EFormatString(name), false);
		}
		return builder;
    }
    
    public EMessageBuilder getString(String name, EMessageBuilder builder, ConfigurationNode node, boolean prefix) {
    	this.getFormatString(node).ifPresent(format -> builder.chat(format, prefix));
    	return builder;
    }
    
    public Optional<EFormatString> getFormatString(ConfigurationNode node) {
    	if (!node.getString("").isEmpty()) {
			if (this.plugin.getChat() != null) {
				return Optional.of(new EFormatString(this.plugin.getChat().replace(node.getString(""))));
    		} else {
    			return Optional.of(new EFormatString(node.getString("")));
    		}
		}
    	return Optional.empty();
    }
}
