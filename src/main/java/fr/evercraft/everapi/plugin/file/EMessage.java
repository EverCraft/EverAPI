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

import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.Listener;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.event.ChatSystemEvent;
import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatListString;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.sponge.UtilsBossBar;
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
    	
    	this.save();
    }
    
    
    public void load() {
    	EnumMessage prefix = null;
    	for (EnumMessage message : this.enum_message) {
    		if (message.getName().equalsIgnoreCase("PREFIX")) {
    			prefix = message;
    		}
    	}
    	Preconditions.checkNotNull(prefix, "PREFIX");
    	
    	for (EnumMessage message : this.enum_message) {
			ConfigurationNode node = get(message.getPath());
			message.set(null);
			
        	if (node != null) {
        		if (node.getValue() == null){
        			if (this.name.equals(FRENCH)) {
            			node.setValue(message.getFrench());
            		} else {
            			node.setValue(message.getEnglish());
            		}
            	}
        		
        		EMessageBuilder builder = EMessageFormat.builder();
        		if (node.getValue() instanceof List) {
        			this.getList(message.getName(), builder, node, false);
        		} else if (node.getValue() instanceof String) {
        			this.getString(message.getName(), builder, node, true);
        		} else if (!node.getNode("chat").isVirtual() || !node.getNode("actionbar").isVirtual() ||
        					!node.getNode("title").isVirtual() || !node.getNode("bossbar").isVirtual()) {
        			if (!node.getNode("chat").isVirtual()) {
        				ConfigurationNode node_chat = node.getNode("chat");
        				if (node_chat.getValue() instanceof List) {
                			this.getList(message.getName(), builder, node, false);
                		} else if (node_chat.getValue() instanceof String) {
                			this.getString(message.getName(), builder, node, true);
                		} else if (!node_chat.getNode("message").isVirtual()) {
                			this.getString(message.getName(), builder, node_chat.getNode("message"), node_chat.getNode("prefix").getBoolean(true));
                		} else if (!node_chat.getNode("messages").isVirtual()) {
                			this.getList(message.getName(), builder, node_chat.getNode("messages"), node_chat.getNode("prefix").getBoolean(false));
                		}
        			}
        			
        			if (!node.getNode("actionbar").isVirtual()) {
        				ConfigurationNode node_actionbar = node.getNode("actionbar");
        				if (node_actionbar.getValue() instanceof String) {
        					this.getFormatString(node.getNode("message")).ifPresent(format -> builder.actionbar(format, 3L, this.plugin.getId(), false));
        				} else {
        					this.getFormatString(node_actionbar.getNode("message")).ifPresent(format -> builder.actionbar(
            						format, 
            						node_actionbar.getNode("stay").getLong(3L),
            						node_actionbar.getNode("priority").getString(this.plugin.getId()),
            						node_actionbar.getNode("prefix").getBoolean(false)));
        				}
        			}
					if (!node.getNode("title").isVirtual()) {
						ConfigurationNode node_title = node.getNode("title");
						Optional<EFormatString> format = this.getFormatString(node_title.getNode("title"));
						Optional<EFormatString> sub_format = this.getFormatString(node_title.getNode("subtitle"));
						if (format.isPresent() || sub_format.isPresent()) {
							builder.title(
									format.orElseGet(() -> new EFormatString("")), 
									node_title.getNode("prefix").getBoolean(false), 
									sub_format.orElseGet(() -> new EFormatString("")), 
									node_title.getNode("subPrefix").getBoolean(false), 
									node_title.getNode("stay").getInt(5), 
									node_title.getNode("fadeIn").getInt(1),
									node_title.getNode("fadeOut").getInt(1),
									node_title.getNode("priority").getString(this.plugin.getId()));
						}
						
					}
					if (!node.getNode("bossbar").isVirtual()) {
						ConfigurationNode node_bossbar = node.getNode("bossbar");
						if (node_bossbar.getValue() instanceof String) {
							this.getFormatString(node_bossbar).ifPresent(format -> builder.bossbar(
									format, 3L, 
									ServerBossBar.builder()
										.color(BossBarColors.WHITE)
										.createFog(false)
										.darkenSky(false)
										.overlay(BossBarOverlays.PROGRESS)
										.percent(100)
										.playEndBossMusic(false)
										.build(), 
									this.plugin.getId(), 
									false));
						} else {
							Optional<EFormatString> format = this.getFormatString(node_bossbar.getNode("message"));
							if (format.isPresent()) {
								builder.bossbar(
	        						format.get(), 
	        						node_bossbar.getNode("stay").getLong(3L),
	        						ServerBossBar.builder()
										.color(UtilsBossBar.getColor(node_bossbar.getNode("color").getString("")).orElse(BossBarColors.WHITE))
										.createFog(node_bossbar.getNode("createFog").getBoolean(false))
										.darkenSky(node_bossbar.getNode("darkenSky").getBoolean(false))
										.overlay(UtilsBossBar.getOverlay(node_bossbar.getNode("overlay").getString("")).orElse(BossBarOverlays.PROGRESS))
										.percent(node_bossbar.getNode("percent").getFloat(100))
										.playEndBossMusic(node_bossbar.getNode("playEndBossMusic").getBoolean(false))
										.build(),
	        						node_bossbar.getNode("priority").getString(this.plugin.getId()),
	        						node_bossbar.getNode("prefix").getBoolean(false));
							}
						}
					}
        		}
        		
        		builder.prefix(prefix);
        		message.set(builder.build());
        	} else {
        		this.plugin.getLogger().warn("Le message '" + message.getName() + "' n'est pas définit");
        	}
        	
        	if (message.getFormat() == null) {
        		message.set(EMessageFormat.builder().prefix(prefix).chat(new EFormatString(message.getPath()), false).build());
        	}
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
			this.plugin.getLogger().warn("Impossible de charger la liste des messages : '" + name + "'");
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
