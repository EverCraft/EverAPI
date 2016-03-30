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
package fr.evercraft.everapi.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.chat.ChatService;
import fr.evercraft.everapi.sponge.UtilsItemStack;
import fr.evercraft.everapi.text.ETextBuilder;

public class EChat implements ChatService {
	
	private final EverAPI plugin;
	
	private ChatService service;

	public EChat(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	private boolean isPresent() {
		if(this.service == null && this.plugin.getGame().getServiceManager().provide(ChatService.class).isPresent()) {
			this.service = this.plugin.getGame().getServiceManager().provide(ChatService.class).get();
			return true;
		}
		return this.service != null;
	}
	
	public String replaceVariable(String message){
		Preconditions.checkNotNull(message, "message");
		
		message = message.replaceAll("<ONLINEPLAYERS>", String.valueOf(this.plugin.getEServer().playerNotVanish()));
		message = message.replaceAll("<MAXPLAYERS>", String.valueOf(this.plugin.getEServer().getMaxPlayers()));
		message = message.replaceAll("<SERVERNAME>", this.plugin.getGame().getPlatform().getApi().getName());
		message = message.replaceAll("<VERSION>", this.plugin.getGame().getPlatform().getApi().getName());
		return message;
	}
	
	public String replaceVariable(final EPlayer player, String message){
		Preconditions.checkNotNull(player, "player");
		Preconditions.checkNotNull(message, "message");
		
		message = message.replaceAll("<UUID>", player.getUniqueId().toString());
		message = message.replaceAll("<NAME>", player.getName());
		message = message.replaceAll("<DISPLAYNAME>", player.getDisplayName());
		message = message.replaceAll("<GROUP>", player.getDisplayName());
		
		message = message.replaceAll("<WORLDNAME>", player.getWorld().getName());
		message = message.replaceAll("<SHORTWORLDNAME>", String.valueOf(player.getWorld().getName().charAt(0)));

		message = message.replaceAll("<BALANCE>", String.valueOf(player.getBalance()));
		message = message.replaceAll("<HEALTH>", String.valueOf(player.getHealth()));
		message = message.replaceAll("<MAXHEALTH>", String.valueOf(player.getMaxHealth()));
		message = message.replaceAll("<ONLINEPLAYERS>", String.valueOf(this.plugin.getEServer().playerNotVanish(player)));
		return message;
	}
	
	public Text replaceVariableText(final EPlayer player, String message){
		return replaceVariableText(player, ETextBuilder.toBuilder(message));
	}
	
	public Text replaceVariableText(final EPlayer player, ETextBuilder message){
		Preconditions.checkNotNull(player, "player");
		Preconditions.checkNotNull(message, "message");
		
		Optional<Team> team = player.getTeam();
		if(team.isPresent()) {
			message = message.replace("<TEAMPREFIX>", team.get().getPrefix());
			message = message.replace("<TEAMSUFFIX>", team.get().getSuffix());
			message = message.replace("<TEAMNAME>", team.get().getDisplayName());
		} else {
			message = message.replace("<TEAMPREFIX>", Text.of());
			message = message.replace("<TEAMSUFFIX>", Text.of());
			message = message.replace("<TEAMNAME>", Text.of());
		}
		return message.build();
	}
	
	public static Text of(final String message) {
    	return TextSerializers.formattingCode('&').deserialize(message);
    }
	
    public static List<Text> of(final List<String> messages) {
    	List<Text> list = new ArrayList<Text>();
        for(String message : messages){
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
	
	@Override
	public String replace(final String message) {
		if(this.isPresent()) {
			return this.service.replace(message);
		}
		return message;
	}

	@Override
	public List<String> replace(final List<String> messages) {
		if(this.isPresent()) {
			return this.service.replace(messages);
		}
		return messages;
	}

	@Override
	public String getFormat(Subject subject) {
		if(this.isPresent()) {
			return this.service.getFormat(subject);
		}
		return null;
	}

	@Override
	public String getFormat(Subject subject, Set<Context> contexts) {
		if(this.isPresent()) {
			return this.service.getFormat(subject, contexts);
		}
		return null;
	}
}
