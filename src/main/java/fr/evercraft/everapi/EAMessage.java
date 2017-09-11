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
package fr.evercraft.everapi;

import java.util.Optional;

import org.spongepowered.api.text.format.TextColor;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class EAMessage extends EMessage<EverAPI> {
	
	public enum EAMessages implements EnumMessage {
		PREFIX("PREFIX",  						"[&4Ever&6&lAPI&f] "),
				
		COMMAND_DESCRIPTION("DESCRIPTION",  	"Gestion de l'API"),
		COMMAND_PLUGINS_DESCRIPTION("pluginsDescription",  	"Affiche la liste des plugins d'EverCraft"),
				
				
		// Permissions
		NO_PERMISSION("noPermission",
				"&cErreur : Vous n'avez pas la permission !",
				"&cError : You don't have permission !"),
		NO_PERMISSION_WORLD("noPermissionWorld",  	
				"&cErreur : Vous n'avez pas la permission d'aller dans ce monde.", 
				"&cError : You do not have permission to go in this world."),
		NO_PERMISSION_WORLD_OTHERS("noPermissionWorldOthers",  	
				"&cErreur : Le joueur n'a pas la permission pour ce monde !",
				"&cError : The player doesn't have permission for this world !"),
				
		// Autre
		WORLD_NOT_FOUND("worldNotFound",
				"&cErreur : Le monde '&6{world}&c' est introuvable.",
				"&cError : The world '&6{world}&c' is not found"),
		REGION_NOT_FOUND("regionNotFound",
				"&cErreur : La région '&6{region}&c' est introuvable."),
		EMPTY_ITEM_IN_HAND("emptyItemInHand", 
				"&cErreur : Vous n'avez aucun objet dans votre main.", 
				"&cError : You have no item in your hand."),
		ACCOUNT_NOT_FOUND("accountNotFound",
				"&cErreur : Le compte n'a pas été trouvé.", 
				"&cError : The account was not found."),
		SERVER_ERROR("serverError",
				"&cUne erreur est survenu, les administrateurs ont été contactés.", 
				"&cAn error occurred, administrators were contacted."),
		IS_NOT_ENTITY_TYPE("isNotEntityType", 
				"&cErreur : '{entity}' n'est pas un type d'entité.", 
				"&cError : '{entity}' is not an entity type."),
		ERROR_VARIABLE("errorVariable", 
				"&cErreur : La variable '{number}' est incorrect."),
		IS_NOT_BOOLEAN("isNotBoolean", 
				"&cErreur : '{boolean}' n'est pas un booléen (TRUE ou FALSE)."),
		IS_NOT_COLOR("isNotBoolean", 
				"&cErreur : '{color}' n'est pas une couleur."),
		IS_NOT_OVERLAY("isNotBoolean", 
				"&cErreur : '{overlay}' n'est pas un overlay."),
				
				
		// Nombre
		NUMBER_INVALID("numberInvalid",
				"&cLe nombre est invalid."),
		IS_NOT_NUMBER("isNotNumber", 
				"&cErreur : '{number}' n'est pas nombre.", 
				"&cError : '{number}' is not a number"),
		IS_NOT_TIME("isNotTime", 
				"&cErreur : &6'{time}' &cn'est pas une durée."),
		
		IS_NOT_DIRECTION("isNotDirection", 
				"&cErreur : '{direction}' n'est pas une direction."),
		
		// Commande
		COMMAND_USAGE("commandUsage",
				"&cUtilisation :[RT]", 
				"&cUsage :[RT]"),
		COMMAND_ERROR("commandError", 
				"&cErreur : Un problème est survenu lors de l'exécution de la commande.", 
				"&cError : A problem occurred during the execution of the command."),
		COMMAND_ASYNC("commandAsync", 
				"&cErreur : Cette commande est déjà encours d'exécution"),
		COMMAND_ERROR_PLAYER_DEAD("commandErrorPlayerDead", 
				"&cErreur : Vous ne pouvez pas exécuter une commande quand vous êtes mort.", 
				"&cError : You can not run a command when you're dead"),
		COMMAND_ERROR_FOR_PLAYER("commandErrorForPlayer", 
				"&cErreur : Cette commande ne peut être exécutée que par un joueur.", 
				"&cError : This command can be executed only by a player."),
		
		// Player
		PLAYER_NOT_FOUND("playerNotFound", 
				"&cErreur : Le joueur &6{player} &cest introuvable.", 
				"&cError : This player is untraceable."),
		PLAYER_NO_LOOK_BLOCK("playerNoLookBlock", 
				"&cErreur : Vous regardez aucun bloc.", 
				"&cError : You look no block."),
		PLAYER_INVENTORY_FULL("playerInventoryFull", 
				"&cErreur : Votre inventaire est plein."),
		PLAYER_INVENTORY_FULL_AND_DROP("playerInventoryFullAndDrop", 
				"&cVotre inventaire est plein ! Le reste des objects est au sol."),
		
		PLAYER_ERROR_TELEPORT("playerErrorTeleport", 
				"&cErreur : Impossible de trouver une position pour réaliser une téléportation."),
		
		GROUP_NOT_FOUND("pluginMessagesGroupNotFound", 				
				"&cErreur : Ce groupe n'existe pas.", 
				"&cError: This group doesn't exist."),
		
		// Reload
		RELOAD_DESCRIPTION("reloadDescription", 
				"Permet de recharger le plugin.", 
				"Reloads the plugin."),
		RELOAD_COMMAND("reloadCommand", 
				"&7Rechargement du plugin terminé.", 
				"&7Reload complete."),
		
		// ReloadAll
		RELOAD_ALL_DESCRIPTION("reloadAllDescription", 
				"Permet de recharger tous les plugins EverCraft."),
		RELOAD_ALL_COMMAND("reloadAllCommand", 
				"&7Rechargement de tous les plugins EverCraft terminé."),
		
		LOCATION_ERROR_NUMBER("locationErrorNumber", 
				"&cErreur : La position {name} doit être compris entre {min} et {max}.", 
				"&cError : The position {name} must be between {min} and {max}"),
		
		// Time
		TIME_NOW("timeNow", 
				"moins d'une seconde", 
				"less than a second"),
		TIME_YEAR("timeYear", 
				"{value} an", 
				"{value} year"),
		TIME_YEARS("timeYears", 
				"{value} ans", 
				"{value} years"),
		TIME_MONTH("timeMonth", 
				"{value} mois", 
				"{value} month"),
		TIME_MONTHS("timeMonths", 
				"{value} mois", 
				"{value} months"),
		TIME_DAY("timeDay", 
				"{value} jour", 
				"{value} day"),
		TIME_DAYS("timeDays", 
				"{value} jours", 
				"{value} days"),
		TIME_HOUR("timeHour", 
				"{value} heure", 
				"{value} hour"),
		TIME_HOURS("timeHours", 
				"{value} heures", 
				"{value} hours"),
		TIME_MINUTE("timeMinute", 
				"{value} minute"),
		TIME_MINUTES("timeMinutes", 
				"{value} minutes"),
		TIME_SECOND("timeSecond", 
				"{value} seconde", 
				"{value} second"),
		TIME_SECONDS("timeSeconds", 
				"{value} secondes", 
				"{value} seconds"),
		TIME_JOIN("timeJoin", 
				" "),
		
		// Gamemode
		GAMEMODE_SURVIVAL("gamemodeSurvival", 
				"Survival"),
		GAMEMODE_CREATIVE("gamemodeCreative", 
				"Créatif", 
				"Creative"),
		GAMEMODE_ADVENTURE("gamemodeAdventure", 
				"Aventure", 
				"Adventure"),
		GAMEMODE_SPECTATOR("gamemodeSpectator", 
				"Spectateur", 
				"Spectator"),
		GAMEMODE_NOT_SET("gamemodeNoset", 
				"Empty"),
		
		// Copy
		HOVER_COPY("hoverCopy", 
				"&cCliquez ici pour copier cette information.", 
				"&cClick here to copy this information."),
		HOVER_URL("hoverUrl", 
				"&cCliquez ici pour ouvrir pour cette URL."),
		
		// Mail
		MAIL_SERVER_DISABLE_OBJECT("mailServerDisableObject", 
				"ServerDisableException : {server}"),
		MAIL_SERVER_DISABLE_MESSAGE("mailServerDisableMessage",
				"Raison : {reason}", 
				"Reason : {reason}"),
		
		// Help
		HELP_TITLE("helpTitle", 
				"&a [ Aide : {plugin} v{version} &a] ", 
				"&a [ Help : {plugin} v{version} &a] "),
		HELP_TITLE_HOVER("helpTitleHover", 
				"&cAuteur(s) :[RT]&c  - {authors}"),
		HELP_AUTHORS_JOIN("helpAuthorsJoin", 
				"[RT]&c  - "),
		HELP_AUTHORS_EMPTY("helpAuthorsEmpty", 
				"Aucun",
				"Empty"),
		HELP_PADDING("helpPadding", 
				"&m &r"),
		HELP_LINE("helpLine", 
				"{name} &6: {description} "),
		HELP_LINE_NAME("helpLineName", 
				"&6/{command}"),
		HELP_LINE_NAME_HOVER("helpLineNameHover", 
				"&c{help}"),
		HELP_EMPTY("helpEmpty", 
				"&7Aucune commande", 
				"&7No command"),
		HELP_COLOR_HELP("helpColorHelp", 
				"&a"),
		HELP_COLOR_PADDING("helpColorPadding", 
				"&7"),
		HELP_COLOR_DESCRIPTION("helpColorDescription", 
				"&7"),
		
		// Plugin
		PLUGINS_MESSAGE("pluginsMessage", 
				"&4Ever&6&lPlugins&f(&a{count}&f) : {plugins}"),
		PLUGINS_ENABLE("pluginsEnable", 
				"&a{plugin}"),
		PLUGINS_DISABLE("pluginsDisable", 
				"&c{plugin}"),
		PLUGINS_ID("pluginsId", 
				"&6ID : &7{id}"),
		PLUGINS_VERSION("pluginsVersion", 
				"&6Version : &7{version}"),
		PLUGINS_DESCRIPTION("pluginsDescription", 
				"&6Description : &7{description}"),
		PLUGINS_URL("pluginsUrl", 
				"&6URL : &7{url}"),
		PLUGINS_AUTHOR("pluginsAuthor", 
				"&6Auteur(s) : &7{author}", 
				"&6Author(s) : &7{author}"),
		
		// Argument
		ARGS_PLAYER("argsPlayer", 
				"joueur", 
				"player"),
		ARGS_USER("argsUser", 
				"pseudo", 
				"player"),
		ARGS_WORLD("argsWorld", 
				"monde", 
				"world"),
		ARGS_GROUP("argsGroup", 
				"groupe", 
				"group"),
		ARGS_SUBGROUP("argsSubgroup", 
				"sous-groupe", 
				"subgroup"),
		ARGS_INHERITANCE("argsInheritance", 
				"inheritance"),
		ARGS_PERMISSION("argsPermission", 
				"permission"),
		ARGS_OPTION("argsOption", 
				"option"),
		ARGS_VALUE("argsValue", 
				"valeur", 
				"value"),
		ARGS_SUBJECT("argsSubject", 
				"subject", 
				"subject"),
		ARGS_MESSAGE("argsMessage", 
				"message..."),
		ARGS_MAIL("argsMail", 
				"mail"),
		ARGS_AMOUNT("argsAmount",
				"quantité", 
				"amount"),
		ARGS_ARGUMENTS("argsArguments", 
				"argument..."),
		ARGS_COOLDOWN("argsCooldown", 
				"cooldown"),
		ARGS_HOME("argsHome", 
				"home"),
		ARGS_WARP("argsWarp", 
				"warp"),
		ARGS_REASON("argsReason", 
				"raison...",
				"reason..."),
		ARGS_ENTITY("argsEntity", 
				"créature",
				"entity"),
		ARGS_SPEED("argsSpeed", 
				"vitesse",
				"speed"),
		ARGS_BLOCK("argsBlock", 
				"bloc(s)",
				"block(s)"),
		ARGS_SECONDS("argsSeconds", 
				"seconde(s)",
				"second(s)"),
		ARGS_MINUTES("argsMinutes", 
				"minute(s)"),
		ARGS_DAMAGE("argsDamage", 
				"dégât(s)",
				"damage(s)"),
		ARGS_DISTANCE("argsDistance", 
				"distance"),
		ARGS_ITEM("argsItem", 
				"objet",
				"item"),
		ARGS_TYPE("argsType", 
				"type"),
		ARGS_EFFECT("argsEffect", 
				"effet",
				"effect"),
		ARGS_AMPLIFICATION("argsAmplification", 
				"amplification"),
		ARGS_ENCHANTMENT("argsEnchantment", 
				"enchantement",
				"enchantment"),
		ARGS_LEVEL("argsLevel", 
				"niveau",
				"level"),
		ARGS_COMMAND("argsCommand", 
				"commande",
				"command"),
		ARGS_ID("argsId", 
				"ID"),
		ARGS_RECIPIENT("argsRecipient", 
				"destination",
				"recipient"),
		ARGS_RADIUS("argsRadius", 
				"rayon"),
		ARGS_ALL("argsAll", 
				"all"),
		ARGS_GAMERULE("argsGamerule", 
				"règle"),
		ARGS_NAME("argsName", 
				"nom d'épée"),
		ARGS_DESCRIPTION("argsDescription", 
				"description"),
		ARGS_LINE("argsLine", 
				"ligne"),
		ARGS_TIME("argsTime", 
				"time"),
		ARGS_IP("argsIp", 
				"IP"),
		ARGS_JAIL("argsJail", 
				"prison"),
		ARGS_REGION("argsRegion", 
				"region"),
		ARGS_FLAG("argsFlag", 
				"flag"),
		ARGS_FLAG_VALUE("argsFlagValue", 
				"flagValue"),
		ARGS_REGION_GROUP("argsRegionGroup", 
				"region_groupe"),
		ARGS_OWNER("argsOwner", 
				"owner"),
		ARGS_OWNER_PLAYER("argsOwnerPlayer", 
				"joueur_owner"),
		ARGS_OWNER_GROUP("argsOwnerGroup", 
				"group_owner"),
		ARGS_MEMBER("argsMember", 
				"member"),
		ARGS_MEMBER_PLAYER("argsMemberPlayer", 
				"joueur_member"),
		ARGS_MEMBER_GROUP("argsMemberGroup", 
				"groupe_member"),
		ARGS_PARENT("argsParent", 
				"parent"),
		ARGS_PRIORITY("argsPriority", 
				"priority"),
		ARGS_DIRECTION("argsDirection", 
				"direction"),
		ARGS_REVERSE_AMOUNT("argsReverseAmount",
				"quantité inverse", 
				"reverse-amount"),
		
		// Couleur
		COLORS_BLACK("colorsBlack", 
				"Noir", 
				"Black"),
		COLORS_DARK_BLUE("colorsDarkBlue", 
				"Bleu foncé", 
				"Dark Blue"),
		COLORS_DARK_GREEN("colorsDarkGreen", 
				"Vert foncé", 
				"Dark Green"),
		COLORS_DARK_AQUA("colorsDarkAqua", 
				"Bleu ciel", 
				"Dark Aqua"),
		COLORS_DARK_RED("colorsDarkRed", 
				"Rouge foncé", 
				"Dark Red"),
		COLORS_DARK_PURPLE("colorsDarkPurple", 
				"Violet", 
				"Dark Purple"),
		COLORS_GOLD("colorsGold", 
				"Or", 
				"Gold"),
		COLORS_GRAY("colorsGray", 
				"Gris", 
				"Gray"),
		COLORS_DARK_GRAY("colorsDarkGray", 
				"Gris foncé", 
				"Dark Gray"),
		COLORS_BLUE("colorsBlue", 
				"Bleu clair", 
				"Blue"),
		COLORS_GREEN("colorsGreen", 
				"Vert clair", 
				"Green"),
		COLORS_AQUA("colorsAqua", 
				"Cyan", 
				"Aqua"),
		COLORS_RED("colorsRed", 
				"Rouge", 
				"Red"),
		COLORS_LIGHT_PURPLE("colorsLightPurple", 
				"Magenta", 
				"Light Purple"),
		COLORS_YELLOW("colorsYellow", 
				"Jaune", 
				"Yellow"),
		COLORS_WHITE("colorsWhite", 
				"Blanc", 
				"White"),
		
		DIRECTIONS_NORTH("directionsNorth", 
				"Nord", 
				"North"),
		DIRECTIONS_NORTH_NORTHEAST("directionsNorthNorthEast", 
				"Nord Nord-Est", 
				"North NorthEast"),
		DIRECTIONS_NORTHEAST("directionsNorthEast", 
				"Nord-Est", 
				"NorthEast"),
		DIRECTIONS_EAST_NORTHEAST("directionsEastNorthEast", 
				"Est Nord-Est", 
				"East NorthEast"),
		DIRECTIONS_EAST("directionsEast", 
				"Est", 
				"East"),
		DIRECTIONS_EAST_SOUTHEAST("directionsEastSouthEast", 
				"Est Sud-Est", 
				"East SouthEast"),
		DIRECTIONS_SOUTHEAST("directionsSouthEast", 
				"Sud-Est", 
				"SouthEast"),
		DIRECTIONS_SOUTH("directionsSouth", 
				"Sud", 
				"South"),
		DIRECTIONS_SOUTH_SOUTHWEST("directionsSouthSouthWest", 
				"Sud Sud-Ouest", 
				"South SouthWest"),
		DIRECTIONS_SOUTHWEST("directionsSouthWest", 
				"Sud-Ouest", 
				"SouthWest"),
		DIRECTIONS_WEST_SOUTHWEST("directionsWestSouthWest", 
				"Ouest Sud-Ouest", 
				"West SouthWest"),
		DIRECTIONS_WEST("directionsWest", 
				"Ouest", 
				"West"),
		DIRECTIONS_WEST_NORTHWEST("directionsWestNorthWest", 
				"Ouest Nord-Ouest", 
				"West NorthWest"),
		DIRECTIONS_NORTHWEST("directionsNorthWest", 
				"Nord-Ouest", 
				"NorthWest"),
		DIRECTIONS_NORTH_NORTHWEST("directionsNorthNorthWest", 
				"Nord Nord-Ouest", 
				"North NorthWest"),
		DIRECTIONS_UP("directionsUp", 
				"Haut", 
				"Up"),
		DIRECTIONS_DOWN("directionsDown", 
				"Bas", 
				"Down"),
		
		// Region
		REGION_TYPE_CUBOID_HOVER("regionTypeCudoidHover",
				"&7Région rectangulaire."),
		REGION_TYPE_POLYGONAL_HOVER("regionTypePolygonalHover",
				"&7Région avec plusieurs positions."),
		REGION_TYPE_TEMPLATE_HOVER("regionTypeTemplateHover",
				"&7Région virtuelle."),
		REGION_TYPE_GLOBAL_HOVER("regionTypeGlobalHover",
				"&7Région qui recouvre tout le monde."),
		
		REGION_GROUP_OWNER_HOVER("regionGroupOwnerHover",						
				"&7Les propriétaires de la région."),
		REGION_GROUP_MEMBER_HOVER("regionGroupMemberHover",						
				"&7Les membres de la région"),
		REGION_GROUP_DEFAULT_HOVER("regionGroupDefaultHover",						
				"&7Les autres joueurs."),
		
		// Flag
		FLAG_DESCRIPTION("flagDescription",			
				"&7{description}"),
		
		FLAG_MESSAGE_JOIN("flagMessageJoin",			
				"&7, "),
		FLAG_MESSAGE_CHAT("flagMessageChat",			
				"&cCHAT"),
		FLAG_MESSAGE_CHAT_MESSAGE("flagMessageChatMessage",			
				"&6Message : &c{message}"),
		FLAG_MESSAGE_CHAT_PREFIX("flagMessageChatPrefix",			
				"&6Prefix : &c{prefix}"),
		
		FLAG_MESSAGE_ACTIONBAR("flagMessageActionBar",			
				"&cACTION_BAR"),
		FLAG_MESSAGE_ACTIONBAR_MESSAGE("flagMessageActionBarMessage",			
				"&6Message : &c{message}"),
		FLAG_MESSAGE_ACTIONBAR_PREFIX("flagMessageActionBarPrefix",			
				"&6Prefix : &c{prefix}"),
		FLAG_MESSAGE_ACTIONBAR_STAY("flagMessageActionBarStay",			
				"&6Stay : &c{stay}(ms)"),
		FLAG_MESSAGE_ACTIONBAR_PRIORITY("flagMessageActionBarPriority",			
				"&6Priority : &c{priority}"),
		
		FLAG_MESSAGE_TITLE("flagMessageTitle",			
				"&cTITLE"),
		FLAG_MESSAGE_TITLE_MESSAGE("flagMessageTitleMessage",			
				"&6Message : &c{message}"),
		FLAG_MESSAGE_TITLE_SUBMESSAGE("flagMessageTitleSubMessage",			
				"&6Prefix : &c{prefix}"),
		FLAG_MESSAGE_TITLE_PREFIX("flagMessageTitlePrefix",			
				"&6SubMessage : &c{submessage}"),
		FLAG_MESSAGE_TITLE_SUBPREFIX("flagMessageTitleSubPrefix",			
				"&6SubPrefix : &c{subprefix}"),
		FLAG_MESSAGE_TITLE_STAY("flagMessageTitleStay",			
				"&6Stay : &c{stay}(ms)"),
		FLAG_MESSAGE_TITLE_FADEIN("flagMessageTitleFadeIn",			
				"&6FadeIn : &c{fadein}(ms)"),
		FLAG_MESSAGE_TITLE_FADEOUT("flagMessageTitleFadeOut",			
				"&6FadeOut : &c{fadeout}(ms)"),
		FLAG_MESSAGE_TITLE_PRIORITY("flagMessageTitlePriority",			
				"&6Priority : &c{priority}"),
		
		FLAG_MESSAGE_BOSSBAR("flagMessageBossBar",			
				"&cBOSSBAR"),
		FLAG_MESSAGE_BOSSBAR_MESSAGE("flagMessageBossBarMessage",			
				"&6Message : &c{message}"),
		FLAG_MESSAGE_BOSSBAR_PREFIX("flagMessageBossBarPrefix",			
				"&6Prefix : &c{prefix}"),
		FLAG_MESSAGE_BOSSBAR_STAY("flagMessageBossBarStay",			
				"&6Stay : &c{stay}(ms)"),
		FLAG_MESSAGE_BOSSBAR_CREATEFOG("flagMessageBossBarCreateFog",			
				"&6CreateFog : &c{createfog}"),
		FLAG_MESSAGE_BOSSBAR_DARKENSKY("flagMessageBossBarDarkenSky",			
				"&6DarkenSky : &c{darkensky}"),
		FLAG_MESSAGE_BOSSBAR_MUSIC("flagMessageBossBarMusic",			
				"&6Music : &c{music}"),
		FLAG_MESSAGE_BOSSBAR_COLOR("flagMessageBossBarColor",			
				"&6Color : &c{color}"),
		FLAG_MESSAGE_BOSSBAR_OVERLAY("flagMessageBossBarOverlay",			
				"&6Overlay : &c{overlay}"),
		FLAG_MESSAGE_BOSSBAR_PERCENT("flagMessageBossBarPercent",			
				"&6Percent : &c{percent}"),
		FLAG_MESSAGE_BOSSBAR_PRIORITY("flagMessageBossBarPriority",			
				"&6Priority : &c{priority}"),
		
		FLAG_MAP_JOIN("flagMapJoin",			
				"&7, "),
		FLAG_MAP_GROUP("flagMapGroup",			
				"&c{group}"),
		FLAG_MAP_EMPTY("flagMapEmpty",			
				"&cAucun"),
		FLAG_MAP_HOVER("flagMapHover",			
				"&c{value}"),
		FLAG_MAP_MORE("flagMapMore",			
				"&c..."),
		
		FLAG_LOCATION("flagLocationPosition",			
				"&7(&c{x}&7,&c{y}&7,&c{z}&7)"),
		FLAG_LOCATION_X("flagLocationX",			
				"&6X : &c{x}"),
		FLAG_LOCATION_Y("flagLocationY",			
				"&6Y : &c{y}"),
		FLAG_LOCATION_Z("flagLocationZ",			
				"&6Z : &c{z}"),
		FLAG_LOCATION_YAW("flagLocationYaw",			
				"&6Yaw : &c{yaw}"),
		FLAG_LOCATION_PITCH("flagLocationPitch",			
				"&6Pitch : &c{pitch}"),
		FLAG_LOCATION_WORLD("flagLocationWorld",			
				"&6World : &c{world}"),
		
		// Pagination
		PAGINATION_COLOR("paginationColor", 
				"&7"),
		PAGINATION_PADDING("paginationPadding", 
				"&m &r"),
		PAGINATION_TITLE("paginationTitle", 
				"&6[ {title} &6]");
		
		private final String path;
	    private final EMessageBuilder french;
	    private final EMessageBuilder english;
	    private EMessageFormat message;
	    private EMessageBuilder builder;
	    
	    private EAMessages(final String path, final String french) {   	
	    	this(path, EMessageFormat.builder().chat(new EFormatString(french), true));
	    }
	    
	    private EAMessages(final String path, final String french, final String english) {   	
	    	this(path, 
	    		EMessageFormat.builder().chat(new EFormatString(french), true), 
	    		EMessageFormat.builder().chat(new EFormatString(english), true));
	    }
	    
	    private EAMessages(final String path, final EMessageBuilder french) {   	
	    	this(path, french, french);
	    }
	    
	    private EAMessages(final String path, final EMessageBuilder french, final EMessageBuilder english) {
	    	Preconditions.checkNotNull(french, "Le message '" + this.name() + "' n'est pas définit");
	    	
	    	this.path = path;	    	
	    	this.french = french;
	    	this.english = english;
	    	this.message = french.build();
	    }

	    public String getName() {
			return this.name();
		}
	    
		public String getPath() {
			return this.path;
		}

		public EMessageBuilder getFrench() {
			return this.french;
		}

		public EMessageBuilder getEnglish() {
			return this.english;
		}
		
		public EMessageFormat getMessage() {
			return this.message;
		}
		
		public EMessageBuilder getBuilder() {
			return this.builder;
		}
		
		public void set(EMessageBuilder message) {
			this.message = message.build();
			this.builder = message;
		}

		public static Optional<EAMessages> getColor(TextColor color) {
			try {
				return Optional.of(EAMessages.valueOf("COLORS_" + color.getName().toUpperCase()));
			} catch (IllegalArgumentException e) {}
			return Optional.empty();
		}
	}

	public EAMessage(final EverAPI plugin) {
		super(plugin, EAMessages.values());
		
		this.load();
	}
}
