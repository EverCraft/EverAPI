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
package fr.evercraft.everapi.config;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.text.TextTemplate;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatListString;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.format.EFormatTemplate;
import fr.evercraft.everapi.sponge.UtilsBossBar;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public final class EMessageBuilderSerializer implements TypeSerializer<EMessageBuilder> {
	
	private final EverAPI plugin;
	
	public EMessageBuilderSerializer(final EverAPI plugin) {
		this.plugin = plugin;
	}

	@Override
	public EMessageBuilder deserialize(TypeToken<?> type, ConfigurationNode node) throws ObjectMappingException {
		EMessageBuilder builder = EMessageFormat.builder();
		if (node.getValue() instanceof List) {
			this.getFormatListString(node).ifPresent(format -> builder.chatMessage(format));
		} else if (node.getValue() instanceof String) {
			this.getFormatString(node).ifPresent(format -> builder.chatMessage(format));
		} else if (!node.getNode("chat").isVirtual() || !node.getNode("actionbar").isVirtual() ||
					!node.getNode("title").isVirtual() || !node.getNode("bossbar").isVirtual()) {
			if (!node.getNode("chat").isVirtual()) {
				ConfigurationNode node_chat = node.getNode("chat");
				
				if (node_chat.getValue() instanceof List) {
					this.getFormatListString(node_chat).ifPresent(format -> builder.chatMessage(format));
        			
        		} else if (node_chat.getValue() instanceof String) {
        			this.getFormatString(node_chat).ifPresent(format -> builder.chatMessage(format));
        			
        		} else if (!node_chat.getNode("message").isVirtual()) {
        			if (node_chat.getValue() instanceof String) {
        				this.getFormatString(node_chat.getNode("message")).ifPresent(format -> builder.chatMessage(format));
        			} else {
        				this.getFormatTemplate(node_chat.getNode("message")).ifPresent(format -> builder.chatMessage(format));
        			}
        			
        			if(!node_chat.getNode("prefix").isVirtual()) {
						builder.actionbarPrefix(node_chat.getNode("prefix").getBoolean(true));
					}
        		
        		} else if (!node_chat.getNode("messages").isVirtual()) {
        			this.getFormatListString(node_chat.getNode("messages")).ifPresent(format -> builder.chatMessage(format));
        			
        			if(!node_chat.getNode("prefix").isVirtual()) {
						builder.actionbarPrefix(node_chat.getNode("prefix").getBoolean(true));
					}
        		} else {
        			this.getFormatTemplate(node_chat).ifPresent(format -> builder.chatMessage(format));
        		}
			}
			
			if (!node.getNode("actionbar").isVirtual()) {
				ConfigurationNode node_actionbar = node.getNode("actionbar");
				if (node_actionbar.getValue() instanceof String) {
					this.getFormatString(node_actionbar).ifPresent(format -> builder.actionbarMessage(format));
				} else {
					this.getFormatString(node_actionbar.getNode("message")).ifPresent(format -> builder.actionbarMessage(format));
					
					if(!node_actionbar.getNode("prefix").isVirtual()) {
						builder.actionbarPrefix(node_actionbar.getNode("prefix").getBoolean(false));
					}
					if(!node_actionbar.getNode("stay").isVirtual()) {
						builder.actionbarStay(node_actionbar.getNode("stay").getInt(3));
					}
					if(!node_actionbar.getNode("fadeOut").isVirtual()) {
						builder.actionbarPriority(node_actionbar.getNode("priority").getString("message"));
					}
				}
			}
			if (!node.getNode("title").isVirtual()) {
				ConfigurationNode node_title = node.getNode("title");
				this.getFormatString(node_title.getNode("title")).ifPresent(title -> builder.titleMessage(title));
				this.getFormatString(node_title.getNode("subTitle")).ifPresent(subtitle -> builder.titleMessage(subtitle));
				
				if(!node_title.getNode("prefix").isVirtual()) {
					builder.titlePrefix(node_title.getNode("prefix").getBoolean(false));
				}
				if(!node_title.getNode("subPrefix").isVirtual()) {
					builder.titleSubPrefix(node_title.getNode("subPrefix").getBoolean(false));
				}
				if(!node_title.getNode("stay").isVirtual()) {
					builder.titleStay(node_title.getNode("stay").getInt(5));
				}
				if(!node_title.getNode("fadeIn").isVirtual()) {
					builder.titleStay(node_title.getNode("fadeIn").getInt(1));
				}
				if(!node_title.getNode("fadeOut").isVirtual()) {
					builder.titleFadeOut(node_title.getNode("fadeOut").getInt(1));
				}
				if(!node_title.getNode("priority").isVirtual()) {
					builder.titlePriority(node_title.getNode("priority").getString("message"));
				}
				
			}
			if (!node.getNode("bossbar").isVirtual()) {
				ConfigurationNode node_bossbar = node.getNode("bossbar");
				if (node_bossbar.getValue() instanceof String) {
					this.getFormatString(node_bossbar).ifPresent(format -> builder
							.bossbarMessage(format));
				} else {
					Optional<EFormatString> format = this.getFormatString(node_bossbar.getNode("message"));
					if (format.isPresent()) {
						builder.bossbarMessage(format.get());
						if(!node_bossbar.getNode("stay").isVirtual()) {
							builder.bossbarStay(node_bossbar.getNode("stay").getInt(3));
						}
						if(!node_bossbar.getNode("color").isVirtual()) {
							builder.bossbarColor(UtilsBossBar.getColor(node_bossbar.getNode("color").getString("")).orElse(BossBarColors.WHITE));
						}
						if(!node_bossbar.getNode("createFog").isVirtual()) {
							builder.bossbarCreateFog(node_bossbar.getNode("createFog").getBoolean(false));
						}
						if(!node_bossbar.getNode("darkenSky").isVirtual()) {
							builder.bossbarDarkenSky(node_bossbar.getNode("darkenSky").getBoolean(false));
						}
						if(!node_bossbar.getNode("overlay").isVirtual()) {
							builder.bossbarOverlay(UtilsBossBar.getOverlay(node_bossbar.getNode("overlay").getString("")).orElse(BossBarOverlays.PROGRESS));
						}
						if(!node_bossbar.getNode("percent").isVirtual()) {
							builder.bossbarPercent(node_bossbar.getNode("percent").getFloat(100));
						}
						if(!node_bossbar.getNode("playEndBossMusic").isVirtual()) {
							builder.bossbarPlayEndBossMusic(node_bossbar.getNode("playEndBossMusic").getBoolean(false));
						}
						if(!node_bossbar.getNode("priority").isVirtual()) {
							builder.bossbarPriority(node_bossbar.getNode("priority").getString(this.plugin.getId()));
						}
						if(!node_bossbar.getNode("prefix").isVirtual()) {
							builder.bossbarPrefix(node_bossbar.getNode("prefix").getBoolean(false));
						}
					}
				}
			}
		} else {
			this.getFormatTemplate(node).ifPresent(format -> builder.chatMessage(format));
		}
		return null;
	}

	@Override
	public void serialize(TypeToken<?> type, EMessageBuilder builder, ConfigurationNode node) throws ObjectMappingException {		
		if (builder.getActionbarMessage() != null && !builder.getActionbarMessage().isEmpty()) {
			ConfigurationNode actionbar = node.getNode("actionbar");
			if (builder.getActionbarPrefix() != null) {
				actionbar.getNode("prefix").setValue(builder.getActionbarPrefix());
			}
			if (builder.getActionbarStay() != null) {
				actionbar.getNode("stay").setValue(builder.getActionbarStay());
			}
			if (builder.getActionbarPriority() != null) {
				actionbar.getNode("priority").setValue(builder.getActionbarPriority());
			}
			
			if (actionbar.isVirtual()) {
				actionbar.setValue(builder.getActionbarMessage());
			} else {
				actionbar.getNode("message").setValue(builder.getActionbarMessage());
			}
		}
		
		if ((builder.getTitleMessage() != null && !builder.getTitleMessage() .isEmpty()) || 
				(builder.getTitleSubMessage() != null && !builder.getTitleSubMessage() .isEmpty())) {
			ConfigurationNode title = node.getNode("title");
			if (builder.getTitleMessage() != null && !builder.getTitleMessage() .isEmpty()) {
				title.getNode("title").setValue(builder.getTitleMessage());
			}
			if (builder.getTitleSubMessage() != null && !builder.getTitleSubMessage() .isEmpty()) {
				title.getNode("subTitle").setValue(builder.getTitleSubMessage());
			}
			if (builder.getTitlePrefix() != null) {
				title.getNode("prefix").setValue(builder.getBossbarPrefix());
			}
			if (builder.getTitleSubPrefix() != null) {
				title.getNode("subPrefix").setValue(builder.getBossbarPrefix());
			}
			if (builder.getTitleStay() != null) {
				title.getNode("stay").setValue(builder.getBossbarStay());
			}
			if (builder.getTitleFadeIn() != null) {
				title.getNode("fadeIn").setValue(builder.getTitleFadeIn());
			}
			if (builder.getTitleFadeOut() != null) {
				title.getNode("fadeOut").setValue(builder.getTitleFadeOut());
			}
			if (builder.getTitlePriority() != null) {
				title.getNode("priority").setValue(builder.getBossbarPriority());
			}
			
		}
		
		if (builder.getBossbarMessage() != null && !builder.getBossbarMessage().isEmpty()) {
			ConfigurationNode bossbar = node.getNode("bossbar");
			if (builder.getBossbarPrefix() != null) {
				bossbar.getNode("prefix").setValue(builder.getBossbarPrefix());
			}
			if (builder.getBossbarStay() != null) {
				bossbar.getNode("stay").setValue(builder.getBossbarStay());
			}
			if (builder.getBossbarCreateFog() != null) {
				bossbar.getNode("createFog").setValue(builder.getBossbarCreateFog());
			}
			if (builder.getBossbarDarkenSky() != null) {
				bossbar.getNode("darkenSky").setValue(builder.getBossbarDarkenSky());
			}
			if (builder.getBossbarPlayEndBossMusic() != null) {
				bossbar.getNode("playEndBossMusic").setValue(builder.getBossbarPlayEndBossMusic());
			}
			if (builder.getBossbarColor() != null) {
				bossbar.getNode("color").setValue(builder.getBossbarColor().getId());
			}
			if (builder.getBossbarOverlay() != null) {
				bossbar.getNode("overlay").setValue(builder.getBossbarOverlay().getId());
			}
			if (builder.getBossbarPercent() != null) {
				bossbar.getNode("precent").setValue(builder.getBossbarPercent());
			}
			if (builder.getBossbarPriority() != null) {
				bossbar.getNode("priority").setValue(builder.getBossbarPriority());
			}
			
			if (bossbar.isVirtual()) {
				bossbar.setValue(builder.getBossbarMessage());
			} else {
				bossbar.getNode("message").setValue(builder.getBossbarMessage());
			}
		}
		
		if (builder.getChatMessage() != null && !builder.getChatMessage().isEmpty()) {
			if (node.isVirtual() && builder.getPrefix() == null) {
				node.setValue(builder.getChatMessage());
			} else {
				if (builder.getChatMessage().isListString()) {
					node.getNode("chat", "messages").setValue(builder.getChatMessage());
				} else {
					node.getNode("chat", "message").setValue(builder.getChatMessage());
				}
				node.getNode("prefix", "prefix").setValue(builder.getChatPrefix());
			}
		}
	}
	
	public Optional<EFormatListString> getFormatListString(ConfigurationNode node) throws ObjectMappingException {
		List<String> messages = node.getList(TypeToken.of(String.class));
		if (!messages.isEmpty()) {
			if (this.plugin.getChat() != null) {
				return Optional.of(new EFormatListString(this.plugin.getChat().replace(messages)));
			} else {
				return Optional.of(new EFormatListString(messages));
			}
		}
		return Optional.empty();
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
    
    public Optional<EFormatTemplate> getFormatTemplate(ConfigurationNode node) {
		try {
			TextTemplate template = node.getValue(TypeToken.of(TextTemplate.class));
			if (template.toText().isEmpty()) {
	    		return Optional.of(new EFormatTemplate(template));
			}
		} catch (ObjectMappingException e) {}
    	return Optional.empty();
    }
	
}
