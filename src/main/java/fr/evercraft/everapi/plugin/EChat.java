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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.message.replace.EReplacePlayer;
import fr.evercraft.everapi.message.replace.EReplaceServer;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.ChatService;
import fr.evercraft.everapi.sponge.UtilsItemStack;
import fr.evercraft.everapi.text.ETextBuilder;

public class EChat implements ChatService {
	
	private final EverAPI plugin;
	
	private ChatService service;

	public EChat(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	public Map<String, EReplace<?>> getReplaceServer() {
		Builder<String, EReplace<?>> builder = ImmutableMap.builder();
		for(EReplaceServer value : EReplaceServer.values()) {
			builder.put(value.getKey(), new EReplace<Object>(() -> value.getValue().apply(this.plugin)));
		}
		return builder.build();
	}
	
	public Map<String, EReplace<?>> getReplacePlayer(final EPlayer player) {
		Builder<String, EReplace<?>> builder = ImmutableMap.builder();
		for(EReplacePlayer value : EReplacePlayer.values()) {
			builder.put(value.getKey(), new EReplace<Object>(() -> value.getValue().apply(this.plugin, player)));
		}
		return builder.build();
	}
	
	private boolean isPresent() {
		if (this.service == null && this.plugin.getGame().getServiceManager().provide(ChatService.class).isPresent()) {
			this.service = this.plugin.getGame().getServiceManager().provide(ChatService.class).get();
			return true;
		}
		return this.service != null;
	}
	
	public String replaceGlobal(String message) {
		Preconditions.checkNotNull(message, "message");
		return message;
	}
	
	public List<String> replaceGlobal(List<String> messages) {
		List<String> list = new ArrayList<String>();
        for (String message : messages){
        	list.add(this.replaceGlobal(message));
        }
        return list;
	}
	
	public String replacePlayer(final EPlayer player, String message){
		Preconditions.checkNotNull(player, "player");
		Preconditions.checkNotNull(message, "message");

		return message;
	}
	
	public Text replaceFormat(final EPlayer player, String message) {
		return replaceFormat(player, ETextBuilder.toBuilder(message));
	}
	
	public Text replaceFormat(final EPlayer player, ETextBuilder message) {
		Preconditions.checkNotNull(player, "player");
		Preconditions.checkNotNull(message, "message");
		return message.build();
	}
	
	public Text replaceAllVariables(final EPlayer player, String message) {
		message = this.replaceGlobal(message);
		message = this.replacePlayer(player, message);
		return this.replaceFormat(player, message);
	}
	
	public static Text of(final String message) {
		Preconditions.checkNotNull(message, "message");
		
    	return TextSerializers.FORMATTING_CODE.deserialize(message);
    }
	
	public static String serialize(final Text message) {
		Preconditions.checkNotNull(message, "message");
		
    	return TextSerializers.FORMATTING_CODE.serialize(message);
    }
	
	public static List<String> serialize(final List<Text> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		List<String> result = new ArrayList<String>();
		messages.forEach(message -> result.add(EChat.serialize(message)));
		return result;
    }
	
    public static List<Text> of(final List<String> messages) {
    	List<Text> list = new ArrayList<Text>();
        for (String message : messages){
        	list.add(EChat.of(message));
        }
        return list;
    }
	
    public static String fixLength(final String message) {
    	return EChat.fixLength(message, 16);
    }
    
    public static String fixLength(final String message, final int length) {
		if (message.length() > length) {
			return message.substring(0, length);
		}
		return message;
	}
    
    public static TextColor getTextColor(final String arg){
    	Preconditions.checkNotNull(arg, "arg");
    	
    	TextColor color = TextColors.WHITE;
		if (arg != null){
			if (arg.equalsIgnoreCase("&0")){
				color = TextColors.BLACK;
			} else if (arg.equalsIgnoreCase("&1")){
				color = TextColors.DARK_BLUE;
			} else if (arg.equalsIgnoreCase("&2")){
				color = TextColors.DARK_GREEN;
			} else if (arg.equalsIgnoreCase("&3")){
				color = TextColors.DARK_AQUA;
			} else if (arg.equalsIgnoreCase("&4")){
				color = TextColors.DARK_RED;
			} else if (arg.equalsIgnoreCase("&5")){
				color = TextColors.DARK_PURPLE;
			} else if (arg.equalsIgnoreCase("&6")){
				color = TextColors.GOLD;
			} else if (arg.equalsIgnoreCase("&7")){
				color = TextColors.GRAY;
			} else if (arg.equalsIgnoreCase("&8")){
				color = TextColors.DARK_GRAY;
			} else if (arg.equalsIgnoreCase("&9")){
				color = TextColors.BLUE;
			} else if (arg.equalsIgnoreCase("&a")){
				color = TextColors.GREEN;
			} else if (arg.equalsIgnoreCase("&b")){
				color = TextColors.AQUA;
			} else if (arg.equalsIgnoreCase("&c")){
				color = TextColors.RED;
			} else if (arg.equalsIgnoreCase("&d")){
				color = TextColors.LIGHT_PURPLE;
			} else if (arg.equalsIgnoreCase("&e")){
				color = TextColors.YELLOW;
			}
		}
		return color;
	}

    public static Text getButtomItem(final ItemStack item){
		return getButtomItem(item, TextColors.WHITE);
	}
	
	public static Text getButtomItem(final ItemStack item, final TextColor color){
		if (item.get(Keys.DISPLAY_NAME).isPresent()) {
			return item.get(Keys.DISPLAY_NAME).get().toBuilder()
					.onHover(TextActions.showItem(item.createSnapshot()))
					.build();
		}
		return UtilsItemStack.getName(item).toBuilder()
				.color(color)
				.onHover(TextActions.showItem(item.createSnapshot()))
				.build();
	}
	
	@Override
	public String replace(final String message) {
		Preconditions.checkNotNull(message, "message");
		
		if (this.isPresent()) {
			return this.service.replace(message);
		}
		return message.replace("[RT]", "\n");
	}

	@Override
	public List<String> replace(final List<String> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		if (this.isPresent()) {
			return this.service.replace(messages);
		} else {
			List<String> list = new ArrayList<String>();
		    for (String message : messages){
		    	list.add(message.replace("[RT]", "\n"));
		    }
		    return list;
		} 
	}
	
	@Override
	public String replaceCharacter(final String message) {
		Preconditions.checkNotNull(message, "message");
		
		if (this.isPresent()) {
			return this.service.replaceCharacter(message);
		}
		return message.replace("[RT]", "\n");
	}
	
	@Override
	public String replaceIcons(final String message) {
		Preconditions.checkNotNull(message, "message");
		
		if (this.isPresent()) {
			return this.service.replaceIcons(message);
		}
		return message;
	}

	@Override
	public String getFormat(Subject subject) {
		if (this.isPresent()) {
			return this.service.getFormat(subject);
		}
		return null;
	}

	@Override
	public String getFormat(Subject subject, Set<Context> contexts) {
		if (this.isPresent()) {
			return this.service.getFormat(subject, contexts);
		}
		return null;
	}
}
