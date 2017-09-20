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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.message.format.EFormatListString;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.message.replace.EReplacesPlayer;
import fr.evercraft.everapi.message.replace.EReplacesServer;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.ChatService;
import fr.evercraft.everapi.sponge.UtilsItemStack;

public class EChat {
	
	private final EverAPI plugin;
	
	private ChatService service;

	public EChat(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	public Map<Pattern, EReplace<?>> getReplaceServer() {
		Map<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		for(EReplacesServer value : EReplacesServer.values()) {
			if (value.getFunction().isPresent()) {
				builder.put(value.getPattern(), EReplace.of(() -> (value.getFunction().get().apply(this.plugin))));
			} else {
				builder.put(value.getPattern(), EReplace.of((option) -> (value.getBiFunction().get().apply(this.plugin, option))));
			}
		}
		return builder;
	}
	
	public Map<Pattern, EReplace<?>> getReplacePlayer(final EPlayer player) {
		Map<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		for(EReplacesPlayer value : EReplacesPlayer.values()) {
			if (value.getBiFunction().isPresent()) {
				builder.put(value.getPattern(), EReplace.of(() -> (value.getBiFunction().get().apply(this.plugin, player))));
			} else {
				builder.put(value.getPattern(), EReplace.of((option) -> (value.getTriFunction().get().apply(this.plugin, player, option))));
			}
		}
		return builder;
	}
	
	public Map<Pattern, EReplace<?>> getReplaceVariables() {
		if (this.isPresent()) return this.service.getReplaceAll();
		
		Map<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		builder.put(Pattern.compile("\\[RT]"), EReplace.of("\n"));
		return builder;
	}
	
	public Map<Pattern, EReplace<?>> getReplaceCharacters() {
		if (!this.isPresent()) return new HashMap<Pattern, EReplace<?>>();
		
		return this.service.getReplaceCharacters();
	}
	
	public Map<Pattern, EReplace<?>> getReplaceCommand() {
		if (!this.isPresent()) return new HashMap<Pattern, EReplace<?>>();
		
		return this.service.getReplaceCommand();
	}
	
	public Map<Pattern, EReplace<?>> getReplaceIcons() {
		if (!this.isPresent()) return new HashMap<Pattern, EReplace<?>>();
		
		return this.service.getReplaceIcons();
	}
	
	public Map<Pattern, EReplace<?>> getReplaceUrl() {
		if (!this.isPresent()) return new HashMap<Pattern, EReplace<?>>();
		
		return this.service.getReplaceUrl();
	}
	
	public Map<Pattern, EReplace<?>> getReplaceAll(final EPlayer player) {
		Map<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		builder.putAll(this.getReplaceVariables());
		builder.putAll(this.getReplaceServer());
		builder.putAll(this.getReplacePlayer(player));
		return builder;
	}	
	
	public Map<Pattern, EReplace<?>> getReplaceAll() {
		Map<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		builder.putAll(this.getReplaceVariables());
		builder.putAll(this.getReplaceServer());
		return builder;
	}	
	
	private boolean isPresent() {
		if (this.service != null) return true;
		
		this.service = this.plugin.getGame().getServiceManager().provide(ChatService.class).orElse(null);
		return this.service != null;
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
    	Preconditions.checkArgument(length > 0);
    	
		if (message.length() > length) {
			return message.substring(0, length);
		}
		return message;
	}
    
    public static Text fixLength(final Text message) {
    	return EChat.fixLength(message, 16);
    }
    
    public static Text fixLength(Text message, final int length) {    	
    	return EChat.of(EChat.fixLength(EChat.serialize(message), length));
	}

    
    public static TextColor getTextColor(final String arg){
    	Preconditions.checkNotNull(arg, "arg");
    	
    	TextColor color = TextColors.NONE;
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
		} else if (arg.equalsIgnoreCase("&f")){
			color = TextColors.WHITE;
		}
		return color;
	}
    
    public static TextStyle getTextStyle(final String arg){
    	TextStyle style = TextStyles.NONE;
		if (arg.equalsIgnoreCase("&k")){
			style = TextStyles.OBFUSCATED;
		} else if (arg.equalsIgnoreCase("&l")){
			style = TextStyles.BOLD;
		} else if (arg.equalsIgnoreCase("&m")){
			style = TextStyles.STRIKETHROUGH;
		} else if (arg.equalsIgnoreCase("&n")){
			style = TextStyles.UNDERLINE;
		} else if (arg.equalsIgnoreCase("&o")){
			style = TextStyles.ITALIC;
		} else if (arg.equalsIgnoreCase("&r")){
			style = TextStyles.RESET;
		}
		return style;
    }
    
    public static TextFormat getTextFormat(final String arg){
    	return EChat.getLastFormat(EChat.of(arg + "\uE000"));
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

	public String getFormat(Subject subject) {
		if (this.isPresent()) {
			return this.service.getFormat(subject);
		}
		return null;
	}

	public String getFormat(Subject subject, Set<Context> contexts) {
		if (this.isPresent()) {
			return this.service.getFormat(subject, contexts);
		}
		return null;
	}
	
	public static TextFormat getLastFormat(Text.Builder text) {
		if (text.getChildren().isEmpty()) {
			return text.getFormat();
		} else {
			return EChat.getLastFormat(text.getChildren().get(text.getChildren().size() - 1));
		}
	}
	
	public static TextFormat getLastFormat(Text text) {
		if (text.getChildren().isEmpty()) {
			return text.getFormat();
		} else {
			return EChat.getLastFormat(text.getChildren().get(text.getChildren().size() - 1));
		}
	}

	public String replace(final String message) {
		Preconditions.checkNotNull(message, "message");
		
		return EFormatString.of(message).toString(this.getReplaceVariables());
	}

	public List<String> replace(final List<String> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		return EFormatListString.of(messages).toListString(this.getReplaceVariables());
	}

	public static Text getButtomCopy(String value) {
		Preconditions.checkNotNull(value, "identifier");
		
		return EChat.of(value).toBuilder()
				.onClick(TextActions.suggestCommand(value))
				.onShiftClick(TextActions.insertText(value))
				.onHover(TextActions.showText(EAMessages.HOVER_COPY.getText()))
				.build();
	}
}
