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
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.scoreboard.TypeScores;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.ChatService;
import fr.evercraft.everapi.sponge.UtilsItemStack;
import fr.evercraft.everapi.text.ETextBuilder;

public class EChat implements ChatService {
	// Server
	public final static String ONLINE_PLAYERS = "<ONLINE_PLAYERS>";
	public final static String MAX_PLAYERS = "<MAX_PLAYERS>";
	public final static String SERVER_NAME = "<SERVER_NAME>";
	public final static String VERSION = "<VERSION>";
	public final static String DATE = "<DATE>";
	public final static String TIME = "<TIME>";
	public final static String DATETIME = "<DATETIME>";
	public final static String MONEY_SINGULAR = "<MONEY_SINGULAR>";
	public final static String MONEY_PLURAL = "<MONEY_PLURAL>";
	public final static String SYMBOL = "<SYMBOL>";
	
	// Player
	public final static String UUID = "<UUID>";
	public final static String NAME = "<NAME>";
	public final static String DISPLAYNAME = "<DISPLAYNAME>";
	public final static String WORLD_NAME = "<WORLD_NAME>";
	public final static String SHORT_WORLD_NAME = "<SHORT_WORLD_NAME>";
	public final static String HEALTH = "<HEALTH>";
	public final static String MAX_HEALTH = "<MAX_HEALTH>";
	public final static String ONLINE_PLAYERS_CANSEE = "<ONLINE_PLAYERS_CANSEE>";
	public final static String IP = "<IP>";
	public final static String PING = "<PING>";
	public final static String LAST_DATE_PLAYED = "<LAST_DATE_PLAYED>";
	public final static String FIRST_DATE_PLAYED = "<FIRST_DATE_PLAYED>";
	public final static String FIRST_DATE_TIME_PLAYED = "<FIRST_DATE_TIME_PLAYED>";
	public final static String GROUP = "<GROUP>";
	public final static String TEAM_PREFIX = "<TEAM_PREFIX>";
	public final static String TEAM_SUFFIX = "<TEAM_SUFFIX>";
	public final static String TEAM_NAME = "<TEAM_NAME>";
	public final static String BALANCE = "<BALANCE>";
	public final static String DEATHS = "<DEATHS>";
	public final static String KILLS = "<KILLS>";
	public final static String RATIO = "<RATIO>";
	public final static String DEATHS_MONTHLY = "<DEATHS_MONTHLY>";
	public final static String KILLS_MONTHLY = "<KILLS_MONTHLY>";
	public final static String RATIO_MONTHLY = "<RATIO_MONTHLY>";
	
	public final static String HELMET = "<HELMET>";
	public final static String CHESTPLATE = "<CHESTPLATE>";
	public final static String LEGGINGS = "<LEGGINGS>";
	public final static String BOOTS = "<BOOTS>";
	
	public final static String HELMET_MAX = "<HELMET_MAX>";
	public final static String CHESTPLATE_MAX = "<CHESTPLATE_MAX>";
	public final static String LEGGINGS_MAX = "<LEGGINGS_MAX>";
	public final static String BOOTS_MAX = "<BOOTS_MAX>";
	
	public final static String HELMET_POURCENTAGE = "<HELMET_POURCENTAGE>";
	public final static String CHESTPLATE_POURCENTAGE = "<CHESTPLATE_POURCENTAGE>";
	public final static String LEGGINGS_POURCENTAGE = "<LEGGINGS_POURCENTAGE>";
	public final static String BOOTS_POURCENTAGE = "<BOOTS_POURCENTAGE>";
	
	// Text
	public final static String DISPLAYNAME_FORMAT = "<DISPLAYNAME_FORMAT>";
	public final static String TEAM_PREFIX_FORMAT = "<TEAM_PREFIX_FORMAT>";
	public final static String TEAM_SUFFIX_FORMAT = "<TEAM_SUFFIX_FORMAT>";
	public final static String TEAM_NAME_FORMAT = "<TEAM_NAME_FORMAT>";
	public final static String BALANCE_FORMAT = "<BALANCE_FORMAT>";
	public final static String MONEY_SINGULAR_FORMAT = "<MONEY_SINGULAR_FORMAT>";
	public final static String MONEY_PLURAL_FORMAT = "<MONEY_PLURAL_FORMAT>";
	public final static String SYMBOL_FORMAT = "<SYMBOL_FORMAT>";
	
	
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
	
	public String replaceGlobal(String message){
		Preconditions.checkNotNull(message, "message");
		
		if(message.contains(ONLINE_PLAYERS)) message = message.replaceAll(ONLINE_PLAYERS, String.valueOf(this.plugin.getEServer().playerNotVanish()));
		if(message.contains(MAX_PLAYERS)) message = message.replaceAll(MAX_PLAYERS, String.valueOf(this.plugin.getGame().getServer().getMaxPlayers()));
		if(message.contains(SERVER_NAME)) message = message.replaceAll(SERVER_NAME, this.plugin.getEServer().getName());
		if(message.contains(VERSION)) message = message.replaceAll(VERSION, this.plugin.getGame().getPlatform().getApi().getVersion().orElse(""));
		if(message.contains(DATE)) message = message.replaceAll(DATE, this.plugin.getManagerUtils().getDate().parseDate());
		if(message.contains(TIME)) message = message.replaceAll(TIME, this.plugin.getManagerUtils().getDate().parseTime());
		if(message.contains(DATETIME)) message = message.replaceAll(DATETIME, this.plugin.getManagerUtils().getDate().parseDateTime());

		if(message.contains(MONEY_SINGULAR) || message.contains(MONEY_PLURAL) || message.contains(SYMBOL)) {
			Optional<EconomyService> economy = this.plugin.getManagerService().getEconomy();
			if(economy.isPresent()) {
				Currency currency = economy.get().getDefaultCurrency();
				message = message.replace(MONEY_SINGULAR, currency.getDisplayName().toPlain());
				message = message.replace(MONEY_PLURAL, currency.getPluralDisplayName().toPlain());
				message = message.replace(SYMBOL, currency.getSymbol().toPlain());
			} else {
				message = message.replace(MONEY_SINGULAR, "");
				message = message.replace(MONEY_PLURAL, "");
				message = message.replace(SYMBOL, "");
			}
		}
		return message;
	}
	
	public String replacePlayer(final EPlayer player, String message){
		Preconditions.checkNotNull(player, "player");
		Preconditions.checkNotNull(message, "message");
		
		if(message.contains(UUID)) message = message.replaceAll(UUID, player.getUniqueId().toString());
		if(message.contains(NAME)) message = message.replaceAll(NAME, player.getName());
		if(message.contains(DISPLAYNAME)) message = message.replaceAll(DISPLAYNAME, player.getDisplayName());
		if(message.contains(WORLD_NAME)) message = message.replaceAll(WORLD_NAME, player.getWorld().getName());
		if(message.contains(SHORT_WORLD_NAME)) message = message.replaceAll(SHORT_WORLD_NAME, String.valueOf(player.getWorld().getName().toUpperCase().charAt(0)));
		if(message.contains(HEALTH)) message = message.replaceAll(HEALTH, String.valueOf(player.getHealth()));
		if(message.contains(MAX_HEALTH)) message = message.replaceAll(MAX_HEALTH, String.valueOf(player.getMaxHealth()));
		if(message.contains(ONLINE_PLAYERS_CANSEE)) message = message.replaceAll(ONLINE_PLAYERS_CANSEE, String.valueOf(this.plugin.getEServer().playerNotVanish(player)));
		if(message.contains(IP)) message = message.replaceAll(IP, player.getConnection().getAddress().getAddress().getHostAddress().toString());
		if(message.contains(PING)) message = message.replaceAll(PING, String.valueOf(player.getConnection().getLatency()));
		if(message.contains(LAST_DATE_PLAYED)) 
			message = message.replaceAll(LAST_DATE_PLAYED, this.plugin.getEverAPI().getManagerUtils().getDate().formatDateDiff(player.getLastDatePlayed(), 3));
		if(message.contains(FIRST_DATE_PLAYED)) 
			message = message.replaceAll(FIRST_DATE_PLAYED, this.plugin.getEverAPI().getManagerUtils().getDate().parseDate(player.getFirstDatePlayed()));
		if(message.contains(FIRST_DATE_TIME_PLAYED))
			message = message.replaceAll(FIRST_DATE_TIME_PLAYED, this.plugin.getEverAPI().getManagerUtils().getDate().parseDateTime(player.getFirstDatePlayed()));
		if(message.contains(GROUP)) {
			Optional<Subject> group = player.getGroup();
			if(group.isPresent()) {
				message = message.replaceAll(GROUP, group.get().getIdentifier());
			} else {
				message = message.replaceAll(GROUP, "");
			}
		}
		if(message.contains(TEAM_PREFIX) || message.contains(TEAM_SUFFIX) || message.contains(TEAM_NAME)) {
			Optional<Team> team = player.getTeam();
			if(team.isPresent()) {
				message = message.replaceAll(TEAM_PREFIX, team.get().getPrefix().toPlain());
				message = message.replaceAll(TEAM_SUFFIX, team.get().getSuffix().toPlain());
				message = message.replaceAll(TEAM_NAME, team.get().getDisplayName().toPlain());
			} else {
				message = message.replaceAll(TEAM_PREFIX, "");
				message = message.replaceAll(TEAM_SUFFIX, "");
				message = message.replaceAll(TEAM_NAME, "");
			}
		}
		
		if(message.contains(BALANCE)) message = message.replaceAll(BALANCE, player.getBalanceRound().toString());
		if(message.contains(DEATHS)) message = message.replaceAll(DEATHS, String.valueOf(player.getDeath()));
		if(message.contains(KILLS)) message = message.replaceAll(KILLS, String.valueOf(player.getKill()));
		if(message.contains(RATIO)) message = message.replaceAll(RATIO, String.valueOf(player.getRatio()));
		if(message.contains(DEATHS_MONTHLY)) message = message.replaceAll(DEATHS_MONTHLY, String.valueOf(player.getDeathMonthly()));
		if(message.contains(KILLS_MONTHLY)) message = message.replaceAll(KILLS_MONTHLY, String.valueOf(player.getKillMonthly()));
		if(message.contains(RATIO_MONTHLY)) message = message.replaceAll(RATIO_MONTHLY, String.valueOf(player.getRatioMonthly()));
		
		if(message.contains(HELMET)) message = message.replaceAll(HELMET, TypeScores.HELMET.getValue(player).toString());
		if(message.contains(CHESTPLATE)) message = message.replaceAll(CHESTPLATE, TypeScores.CHESTPLATE.getValue(player).toString());
		if(message.contains(LEGGINGS)) message = message.replaceAll(LEGGINGS, TypeScores.LEGGINGS.getValue(player).toString());
		if(message.contains(BOOTS)) message = message.replaceAll(BOOTS, TypeScores.BOOTS.getValue(player).toString());
		
		if(message.contains(HELMET_MAX)) message = message.replaceAll(HELMET_MAX, TypeScores.HELMET_MAX.getValue(player).toString());
		if(message.contains(CHESTPLATE_MAX)) message = message.replaceAll(CHESTPLATE_MAX, TypeScores.CHESTPLATE_MAX.getValue(player).toString());
		if(message.contains(LEGGINGS_MAX)) message = message.replaceAll(LEGGINGS_MAX, TypeScores.LEGGINGS_MAX.getValue(player).toString());
		if(message.contains(BOOTS_MAX)) message = message.replaceAll(BOOTS_MAX, TypeScores.BOOTS_MAX.getValue(player).toString());
		
		if(message.contains(HELMET_POURCENTAGE)) message = message.replaceAll(HELMET_POURCENTAGE, TypeScores.HELMET_POURCENTAGE.getValue(player).toString());
		if(message.contains(CHESTPLATE_POURCENTAGE)) message = message.replaceAll(CHESTPLATE_POURCENTAGE, TypeScores.CHESTPLATE_POURCENTAGE.getValue(player).toString());
		if(message.contains(LEGGINGS_POURCENTAGE)) message = message.replaceAll(LEGGINGS_POURCENTAGE, TypeScores.LEGGINGS_POURCENTAGE.getValue(player).toString());
		if(message.contains(BOOTS_POURCENTAGE)) message = message.replaceAll(BOOTS_POURCENTAGE, TypeScores.BOOTS_POURCENTAGE.getValue(player).toString());

		return message;
	}
	
	public Text replaceFormat(final EPlayer player, String message) {
		return replaceFormat(player, ETextBuilder.toBuilder(message));
	}
	
	public Text replaceFormat(final EPlayer player, ETextBuilder message) {
		Preconditions.checkNotNull(player, "player");
		Preconditions.checkNotNull(message, "message");
		
		if(message.contains(DISPLAYNAME_FORMAT)) message = message.replace(DISPLAYNAME_FORMAT, player.getDisplayNameHover());
		
		if(message.contains(TEAM_PREFIX_FORMAT) || message.contains(TEAM_SUFFIX_FORMAT) || message.contains(TEAM_NAME_FORMAT)) {
			Optional<Team> team = player.getTeam();
			if(team.isPresent()) {
				message = message.replace(TEAM_PREFIX_FORMAT, team.get().getPrefix());
				message = message.replace(TEAM_SUFFIX_FORMAT, team.get().getSuffix());
				message = message.replace(TEAM_NAME_FORMAT, team.get().getDisplayName());
			} else {
				message = message.replace(TEAM_PREFIX_FORMAT, Text.of());
				message = message.replace(TEAM_SUFFIX_FORMAT, Text.of());
				message = message.replace(TEAM_NAME_FORMAT, Text.of());
			}
		}
		if(message.contains(BALANCE_FORMAT) || message.contains(MONEY_SINGULAR_FORMAT) || message.contains(MONEY_PLURAL_FORMAT) || message.contains(SYMBOL_FORMAT)) {
			Optional<EconomyService> economy = this.plugin.getManagerService().getEconomy();
			if(economy.isPresent()) {
				Currency currency = economy.get().getDefaultCurrency();
				message = message.replace(BALANCE_FORMAT, currency.format(player.getBalance()));
				message = message.replace(MONEY_SINGULAR_FORMAT, currency.getSymbol());
				message = message.replace(MONEY_PLURAL_FORMAT, currency.getDisplayName());
				message = message.replace(SYMBOL_FORMAT, currency.getPluralDisplayName());
			} else {
				message = message.replace(BALANCE_FORMAT, Text.of());
				message = message.replace(MONEY_SINGULAR_FORMAT, Text.of());
				message = message.replace(MONEY_PLURAL_FORMAT, Text.of());
				message = message.replace(SYMBOL_FORMAT, Text.of());
			}
		}
		return message.build();
	}
	
	public Text replaceAllVariables(final EPlayer player, String message) {
		message = this.replaceGlobal(message);
		message = this.replacePlayer(player, message);
		return this.replaceFormat(player, message);
	}
	
	public static Text of(final String message) {
    	return TextSerializers.FORMATTING_CODE.deserialize(message);
    }
	
	public static String serialize(final Text message) {
    	return TextSerializers.FORMATTING_CODE.serialize(message);
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
		Preconditions.checkNotNull(message, "message");
		
		if(this.isPresent()) {
			return this.service.replace(message);
		}
		return message.replace("[RT]", "\n");
	}

	@Override
	public List<String> replace(final List<String> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		if(this.isPresent()) {
			return this.service.replace(messages);
		} else {
			List<String> list = new ArrayList<String>();
		    for(String message : messages){
		    	list.add(message.replace("[RT]", "\n"));
		    }
		    return list;
		} 
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
