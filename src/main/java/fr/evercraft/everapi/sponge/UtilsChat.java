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
package fr.evercraft.everapi.sponge;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;

public class UtilsChat {
	
	private EverAPI plugin;

	public UtilsChat(EverAPI plugin){
		this.plugin = plugin;
	}
	
	public String replaceVariable(String message){
		if(message != null){
			message = message.replace("<ONLINEPLAYERS>", String.valueOf(this.plugin.getEServer().playerNotVanish()));
			message = message.replace("<MAXPLAYERS>", String.valueOf(this.plugin.getEServer().getMaxPlayers()));
			message = message.replace("<SERVERNAME>", this.plugin.getGame().getPlatform().getApi().getName());
			message = message.replace("<VERSION>", this.plugin.getGame().getPlatform().getApi().getName());
		}
		return message;
	}
	
	public String replaceVariable(String message, final EPlayer player){
		if(message != null && player != null){
			message = message.replace("<NAME>", player.getName());
			message = message.replace("<DISPLAYNAME>", player.getIdentifier());
			message = message.replace("<HEALTH>", String.valueOf(player.getHealth()));
			message = message.replace("<MAXHEALTH>", String.valueOf(player.getMaxHealth()));
			message = message.replace("<BALANCE>", String.valueOf(player.getBalance()));
			message = message.replace("<UUID>", player.getUniqueId().toString());
			message = message.replace("<WORLD>", player.getWorld().getName());
			message = message.replace("<ONLINEPLAYERS>", String.valueOf(this.plugin.getEServer().playerNotVanish(player)));
		}
		return replaceVariable(message);
	}
	
	public static String replace(String colormsg) {
		if(colormsg != null){
			colormsg = colormsg.replace("[<3]", "\u2764");
			colormsg = colormsg.replace("[check]", "\u2714");
			colormsg = colormsg.replace("[*]", "\u2716");
			colormsg = colormsg.replace("[ARROW]", "\u279c");
			colormsg = colormsg.replace("[X]", "\u2588");
			colormsg = colormsg.replace("[RT]", "\n");
		}
		return colormsg;
    }
	
	public static List<String> replace(final List<String> colormsgs) {
		List<String> list = new ArrayList<String>();
        for(String colormsg : colormsgs){
        	list.add(UtilsChat.replace(colormsg));
        }
        return list;
    }
	
	public static Text of(final String colormsg) {
    	return TextSerializers.formattingCode('&').deserialize(replace(colormsg));
    }
	
    public static List<Text> of(final List<String> colormsgs) {
    	List<Text> list = new ArrayList<Text>();
        for(String colormsg : colormsgs){
        	list.add(UtilsChat.of(colormsg));
        }
        return list;
    }
	
    public static String fixLength(final String text) {
    	return UtilsChat.fixLength(text, 16);
    }
    
    public static String fixLength(final String text, final int length) {
		if (text.length() > length) {
			return text.substring(0, length);
		}
		return text;
	}
    
    public static TextColor getTextColor(final String arg){
    	TextColor color = TextColors.WHITE;
		if(arg != null){
			if(arg.equalsIgnoreCase("&0")){
				color = TextColors.BLACK;
			} else if(arg.equalsIgnoreCase("&1")){
				color = TextColors.DARK_BLUE;
			} else if(arg.equalsIgnoreCase("&2")){
				color = TextColors.DARK_GREEN;
			} else if(arg.equalsIgnoreCase("&3")){
				color = TextColors.DARK_AQUA;
			} else if(arg.equalsIgnoreCase("&4")){
				color = TextColors.DARK_RED;
			} else if(arg.equalsIgnoreCase("&5")){
				color = TextColors.DARK_PURPLE;
			} else if(arg.equalsIgnoreCase("&6")){
				color = TextColors.GOLD;
			} else if(arg.equalsIgnoreCase("&7")){
				color = TextColors.GRAY;
			} else if(arg.equalsIgnoreCase("&8")){
				color = TextColors.DARK_GRAY;
			} else if(arg.equalsIgnoreCase("&9")){
				color = TextColors.BLUE;
			} else if(arg.equalsIgnoreCase("&a")){
				color = TextColors.GREEN;
			} else if(arg.equalsIgnoreCase("&b")){
				color = TextColors.AQUA;
			} else if(arg.equalsIgnoreCase("&c")){
				color = TextColors.RED;
			} else if(arg.equalsIgnoreCase("&d")){
				color = TextColors.LIGHT_PURPLE;
			} else if(arg.equalsIgnoreCase("&e")){
				color = TextColors.YELLOW;
			}
		}
		return color;
	}

    public static Text getButtomItem(final ItemStack item){
		return getButtomItem(item, TextColors.WHITE);
	}
	
	public static Text getButtomItem(final ItemStack item, final TextColor color){
		if(item.get(Keys.DISPLAY_NAME).isPresent()) {
			return item.get(Keys.DISPLAY_NAME).get().toBuilder()
					.onHover(TextActions.showItem(item))
					.build();
		}
		return UtilsItemStack.getName(item).toBuilder()
				.color(color)
				.onHover(TextActions.showItem(item))
				.build();
	}
}
