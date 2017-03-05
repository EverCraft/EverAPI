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

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class EAMessage extends EMessage<EverAPI> {
	
	public enum EAMessages implements EnumMessage {
		PREFIX("prefix",  							
				"[&4Ever&6&lAPI&f] "),
				
		COMMAND_DESCRIPTION("description",  							
				"Gestion de l'API"),
		COMMAND_PLUGINS_DESCRIPTION("description",  							
				"Affiche la liste des plugins d'EverCraft"),
				
				
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
				"&cErreur : Le monde '&6<world>&c' est introuvable.",
				"&cError : The world '&6<world>&c' is not found"),
		REGION_NOT_FOUND("regionNotFound",
				"&cErreur : La région '&6<region>&c' est introuvable."),
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
				"&cErreur : '<entity>' n'est pas un type d'entité.", 
				"&cError : '<entity>' is not an entity type."),
		ERROR_VARIABLE("errorVariable", 
				"&cErreur : La variable '<number>' est incorrect."),
				
				
		// Nombre
		NUMBER_INVALID("numberInvalid",
				"&cLe nombre est invalid."),
		IS_NOT_NUMBER("isNotNumber", 
				"&cErreur : '<number>' n'est pas nombre.", 
				"&cError : '<number>' is not a number"),
		IS_NOT_TIME("isNotTime", 
				"&cErreur : &6'<time>' &cn'est pas une durée."),
		
		IS_NOT_DIRECTION("isNotDirection", 
				"&cErreur : '<direction>' n'est pas une direction."),
		
		// Commande
		COMMAND_USAGE("command.usage",
				"&cUtilisation :[RT]", 
				"&cUsage :[RT]"),
		COMMAND_ERROR("command.error", 
				"&cErreur : Un problème est survenu lors de l'exécution de la commande.", 
				"&cError : A problem occurred during the execution of the command."),
		COMMAND_ERROR_PLAYER_DEAD("command.errorPlayerDead", 
				"&cErreur : Vous ne pouvez pas exécuter une commande quand vous êtes mort.", 
				"&cError : You can not run a command when you're dead"),
		COMMAND_ERROR_FOR_PLAYER("command.errorForPlayer", 
				"&cErreur : Cette commande ne peut être exécutée que par un joueur.", 
				"&cError : This command can be executed only by a player."),
		
		// Player
		PLAYER_NOT_FOUND("player.notFound", 
				"&cErreur : Ce joueur est introuvable.", 
				"&cError : This player is untraceable."),
		PLAYER_NO_LOOK_BLOCK("player.noLookBlock", 
				"&cErreur : Vous regardez aucun bloc.", 
				"&cError : You look no block."),
		PLAYER_INVENTORY_FULL("player.inventoryFull", 
				"&cErreur : Votre inventaire est plein."),
		PLAYER_INVENTORY_FULL_AND_DROP("player.inventoryFullAndDrop", 
				"&cVotre inventaire est plein ! Le reste des objects est au sol."),
		
		PLAYER_ERROR_TELEPORT("player.errorTeleport", 
				"&cErreur : Impossible de trouver une position pour réaliser une téléportation."),
		
		GROUP_NOT_FOUND("plugin.messages.groupNotFound", 				
				"&cErreur : Ce groupe n'existe pas.", 
				"&cError: This group doesn't exist."),
		
		// Reload
		RELOAD_DESCRIPTION("reload.description", 
				"Permet de recharger le plugin.", 
				"Reloads the plugin."),
		RELOAD_COMMAND("reload.command", 
				"&7Rechargement du plugin terminé.", 
				"&7Reload complete."),
		
		// ReloadAll
		RELOAD_ALL_DESCRIPTION("reloadAll.description", 
				"Permet de recharger tous les plugins EverCraft."),
		RELOAD_ALL_COMMAND("reloadAll.command", 
				"&7Rechargement de tous les plugins EverCraft terminé."),
		
		LOCATION_ERROR_NUMBER("location.errorNumber", 
				"&cErreur : La position <name> doit être compris entre <min> et <max>.", 
				"&cError : The position <name> must be between <min> and <max>"),
		
		// Time
		TIME_NOW("time.now", 
				"moins d'une seconde", 
				"less than a second"),
		TIME_YEAR("time.year", 
				"<value> an", 
				"<value> year"),
		TIME_YEARS("time.years", 
				"<value> ans", 
				"<value> years"),
		TIME_MONTH("time.month", 
				"<value> mois", 
				"<value> month"),
		TIME_MONTHS("time.months", 
				"<value> mois", 
				"<value> months"),
		TIME_DAY("time.day", 
				"<value> jour", 
				"<value> day"),
		TIME_DAYS("time.days", 
				"<value> jours", 
				"<value> days"),
		TIME_HOUR("time.hour", 
				"<value> heure", 
				"<value> hour"),
		TIME_HOURS("time.hours", 
				"<value> heures", 
				"<value> hours"),
		TIME_MINUTE("time.minute", 
				"<value> minute"),
		TIME_MINUTES("time.minutes", 
				"<value> minutes"),
		TIME_SECOND("time.second", 
				"<value> seconde", 
				"<value> second"),
		TIME_SECONDS("time.seconds", 
				"<value> secondes", 
				"<value> seconds"),
		TIME_JOIN("time.join", 
				" "),
		
		// Gamemode
		GAMEMODE_SURVIVAL("gamemode.survival", 
				"Survival"),
		GAMEMODE_CREATIVE("gamemode.creative", 
				"Créatif", 
				"Creative"),
		GAMEMODE_ADVENTURE("gamemode.adventure", 
				"Aventure", 
				"Adventure"),
		GAMEMODE_SPECTATOR("gamemode.spectator", 
				"Spectateur", 
				"Spectator"),
		GAMEMODE_NOT_SET("gamemode.noset", 
				"Empty"),
		
		// Copy
		HOVER_COPY("hoverCopy", 
				"&cCliquez ici pour copier cette information.", 
				"&cClick here to copy this information."),
		
		// Mail
		MAIL_SERVER_DISABLE_OBJECT("mail.serverDisable.object", 
				"ServerDisableException : <server>"),
		MAIL_SERVER_DISABLE_MESSAGE("mail.serverDisable.message",
				"Raison : <reason>", 
				"Reason : <reason>"),
		
		// Help
		HELP_TITLE("help.title", 
				"&a [ Aide : <plugin> v<version> &a] ", 
				"&a [ Help : <plugin> v<version> &a] "),
		HELP_TITLE_HOVER("help.titleHover", 
				"&cAuteur(s) :[RT]&c  - <authors>"),
		HELP_AUTHORS_JOIN("help.authorsJoin", 
				"[RT]&c  - "),
		HELP_AUTHORS_EMPTY("help.authorsEmpty", 
				"Aucun",
				"Empty"),
		HELP_PADDING("help.padding", 
				"&m &r"),
		HELP_LINE("help.line", 
				"<name> &6: <description> "),
		HELP_LINE_NAME("help.lineName", 
				"&6/<command>"),
		HELP_LINE_NAME_HOVER("help.lineNameHover", 
				"&c<help>"),
		HELP_EMPTY("help.empty", 
				"&7Aucune commande", 
				"&7No command"),
		HELP_COLOR_HELP("help.colorHelp", 
				"&a"),
		HELP_COLOR_PADDING("help.colorPadding", 
				"&7"),
		HELP_COLOR_DESCRIPTION("help.colorDescription", 
				"&7"),
		
		// Plugin
		PLUGINS_MESSAGE("plugins.message", 
				"&4Ever&6&lPlugins&f(&a<count>&f) : <plugins>"),
		PLUGINS_ENABLE("plugins.enable", 
				"&a<plugin>"),
		PLUGINS_DISABLE("plugins.disable", 
				"&c<plugin>"),
		PLUGINS_ID("plugins.id", 
				"&6ID : &7<id>"),
		PLUGINS_VERSION("plugins.version", 
				"&6Version : &7<version>"),
		PLUGINS_DESCRIPTION("plugins.description", 
				"&6Description : &7<description>"),
		PLUGINS_URL("plugins.url", 
				"&6URL : &7<url>"),
		PLUGINS_AUTHOR("plugins.author", 
				"&6Auteur(s) : &7<author>", 
				"&6Author(s) : &7<author>"),
		
		// Argument
		ARGS_PLAYER("args.player", 
				"joueur", 
				"player"),
		ARGS_USER("args.user", 
				"pseudo", 
				"player"),
		ARGS_WORLD("args.world", 
				"monde", 
				"world"),
		ARGS_GROUP("args.group", 
				"groupe", 
				"group"),
		ARGS_SUBGROUP("args.subgroup", 
				"sous-groupe", 
				"subgroup"),
		ARGS_INHERITANCE("args.inheritance", 
				"inheritance"),
		ARGS_PERMISSION("args.permission", 
				"permission"),
		ARGS_OPTION("args.option", 
				"option"),
		ARGS_VALUE("args.value", 
				"valeur", 
				"value"),
		ARGS_SUBJECT("args.subject", 
				"subject", 
				"subject"),
		ARGS_MESSAGE("args.message", 
				"message..."),
		ARGS_MAIL("args.mail", 
				"mail"),
		ARGS_AMOUNT("args.amount",
				"quantité", 
				"amount"),
		ARGS_ARGUMENTS("args.arguments", 
				"argument..."),
		ARGS_COOLDOWN("args.cooldown", 
				"cooldown"),
		ARGS_HOME("args.home", 
				"home"),
		ARGS_WARP("args.warp", 
				"warp"),
		ARGS_REASON("args.reason", 
				"raison...",
				"reason..."),
		ARGS_ENTITY("args.entity", 
				"créature",
				"entity"),
		ARGS_SPEED("args.speed", 
				"vitesse",
				"speed"),
		ARGS_BLOCK("args.block", 
				"bloc(s)",
				"block(s)"),
		ARGS_SECONDS("args.seconds", 
				"seconde(s)",
				"second(s)"),
		ARGS_MINUTES("args.minutes", 
				"minute(s)"),
		ARGS_DAMAGE("args.damage", 
				"dégât(s)",
				"damage(s)"),
		ARGS_DISTANCE("args.distance", 
				"distance"),
		ARGS_ITEM("args.item", 
				"objet",
				"item"),
		ARGS_TYPE("args.type", 
				"type"),
		ARGS_EFFECT("args.effect", 
				"effet",
				"effect"),
		ARGS_AMPLIFICATION("args.amplification", 
				"amplification"),
		ARGS_ENCHANTMENT("args.enchantment", 
				"enchantement",
				"enchantment"),
		ARGS_LEVEL("args.level", 
				"niveau",
				"level"),
		ARGS_COMMAND("args.command", 
				"commande",
				"command"),
		ARGS_ID("args.id", 
				"ID"),
		ARGS_RECIPIENT("args.recipient", 
				"destination",
				"recipient"),
		ARGS_RADIUS("args.radius", 
				"rayon"),
		ARGS_ALL("args.all", 
				"all"),
		ARGS_GAMERULE("args.gamerule", 
				"règle"),
		ARGS_NAME("args.name", 
				"nom d'épée"),
		ARGS_DESCRIPTION("args.description", 
				"description"),
		ARGS_LINE("args.line", 
				"ligne"),
		ARGS_TIME("args.time", 
				"time"),
		ARGS_IP("args.ip", 
				"IP"),
		ARGS_JAIL("args.jail", 
				"prison"),
		ARGS_REGION("args.region", 
				"region"),
		ARGS_FLAG("args.flag", 
				"flag"),
		ARGS_FLAG_VALUE("args.flagValue", 
				"flagValue"),
		ARGS_REGION_GROUP("args.regionGroup", 
				"region_groupe"),
		ARGS_OWNER("args.owner", 
				"owner"),
		ARGS_OWNER_PLAYER("args.ownerPlayer", 
				"joueur_owner"),
		ARGS_OWNER_GROUP("args.ownerGroup", 
				"group_owner"),
		ARGS_MEMBER("args.member", 
				"member"),
		ARGS_MEMBER_PLAYER("args.memberPlayer", 
				"joueur_member"),
		ARGS_MEMBER_GROUP("args.memberGroup", 
				"groupe_member"),
		ARGS_PARENT("args.parent", 
				"parent"),
		ARGS_PRIORITY("args.priority", 
				"priority"),
		ARGS_DIRECTION("args.direction", 
				"direction"),
		ARGS_REVERSE_AMOUNT("args.reverseAmount",
				"quantité inverse", 
				"reverse-amount"),
		
		// Couleur
		COLORS_BLACK("colors.black", 
				"Noir", 
				"Black"),
		COLORS_DARK_BLUE("colors.dark_blue", 
				"Bleu foncé", 
				"Dark Blue"),
		COLORS_DARK_GREEN("colors.dark_green", 
				"Vert foncé", 
				"Dark Green"),
		COLORS_DARK_AQUA("colors.dark_aqua", 
				"Bleu ciel", 
				"Dark Aqua"),
		COLORS_DARK_RED("colors.dark_red", 
				"Rouge foncé", 
				"Dark Red"),
		COLORS_DARK_PURPLE("colors.dark_purple", 
				"Violet", 
				"Dark Purple"),
		COLORS_GOLD("colors.gold", 
				"Or", 
				"Gold"),
		COLORS_GRAY("colors.gray", 
				"Gris", 
				"Gray"),
		COLORS_DARK_GRAY("colors.dark_gray", 
				"Gris foncé", 
				"Dark Gray"),
		COLORS_BLUE("colors.blue", 
				"Bleu clair", 
				"Blue"),
		COLORS_GREEN("colors.green", 
				"Vert clair", 
				"Green"),
		COLORS_AQUA("colors.aqua", 
				"Cyan", 
				"Aqua"),
		COLORS_RED("colors.red", 
				"Rouge", 
				"Red"),
		COLORS_LIGHT_PURPLE("colors.light_purple", 
				"Magenta", 
				"Light Purple"),
		COLORS_YELLOW("colors.yellow", 
				"Jaune", 
				"Yellow"),
		COLORS_WHITE("colors.white", 
				"Blanc", 
				"White"),
		
		DIRECTIONS_NORTH("directions.north", 
				"Nord", 
				"North"),
		DIRECTIONS_NORTH_NORTHEAST("directions.northNorthEast", 
				"Nord Nord-Est", 
				"North NorthEast"),
		DIRECTIONS_NORTHEAST("directions.northEast", 
				"Nord-Est", 
				"NorthEast"),
		DIRECTIONS_EAST_NORTHEAST("directions.eastNorthEast", 
				"Est Nord-Est", 
				"East NorthEast"),
		DIRECTIONS_EAST("directions.east", 
				"Est", 
				"East"),
		DIRECTIONS_EAST_SOUTHEAST("directions.eastSouthEast", 
				"Est Sud-Est", 
				"East SouthEast"),
		DIRECTIONS_SOUTHEAST("directions.southEast", 
				"Sud-Est", 
				"SouthEast"),
		DIRECTIONS_SOUTH("directions.south", 
				"Sud", 
				"South"),
		DIRECTIONS_SOUTH_SOUTHWEST("directions.southSouthWest", 
				"Sud Sud-Ouest", 
				"South SouthWest"),
		DIRECTIONS_SOUTHWEST("directions.southWest", 
				"Sud-Ouest", 
				"SouthWest"),
		DIRECTIONS_WEST_SOUTHWEST("directions.westSouthWest", 
				"Ouest Sud-Ouest", 
				"West SouthWest"),
		DIRECTIONS_WEST("directions.west", 
				"Ouest", 
				"West"),
		DIRECTIONS_WEST_NORTHWEST("directions.westNorthWest", 
				"Ouest Nord-Ouest", 
				"West NorthWest"),
		DIRECTIONS_NORTHWEST("directions.northWest", 
				"Nord-Ouest", 
				"NorthWest"),
		DIRECTIONS_NORTH_NORTHWEST("directions.northNorthWest", 
				"Nord Nord-Ouest", 
				"North NorthWest"),
		DIRECTIONS_UP("directions.up", 
				"Haut", 
				"Up"),
		DIRECTIONS_DOWN("directions.down", 
				"Bas", 
				"Down"),
		
		// Region
		REGION_TYPE_CUBOID("regionType.cudoid",						
				"CUBOID"),
		REGION_TYPE_CUBOID_HOVER("regionType.cudoidHover",			
				"&7Région rectangulaire."),
		REGION_TYPE_POLYGONAL("regionType.polygonal",				
				"POLYGONAL"),
		REGION_TYPE_POLYGONAL_HOVER("regionType.polygonalHover",	
				"&7Région avec plusieurs positions."),
		REGION_TYPE_TEMPLATE("regionType.template",					
				"TEMPLATE"),
		REGION_TYPE_TEMPLATE_HOVER("regionType.templateHover",		
				"&7Région virtuelle."),
		REGION_TYPE_GLOBAL("regionType.global",						
				"GLOBAL"),
		REGION_TYPE_GLOBAL_HOVER("regionType.globalHover",			
				"&7Région qui recouvre tout le monde."),
		
		REGION_GROUP_OWNER_HOVER("regionGroup.ownerHover",						
				"&7Les propriétaires de la région."),
		REGION_GROUP_MEMBER_HOVER("regionGroup.memberHover",						
				"&7Les membres de la région"),
		REGION_GROUP_DEFAULT_HOVER("regionGroup.defaultHover",						
				"&7Les autres joueurs."),
		
		// Flag
		FLAG_DESCRIPTION("flag.description",			
				"&7<description>"),
		
		// Pagination
		PAGINATION_COLOR("pagination.color", 
				"&7"),
		PAGINATION_PADDING("pagination.padding", 
				"&m &r"),
		PAGINATION_TITLE("pagination.title", 
				"&6[ <title> &6]");
		
		private final String path;
	    private final EMessageBuilder french;
	    private final EMessageBuilder english;
	    private EMessageFormat message;
	    
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
		
		public void set(EMessageFormat message) {
			this.message = message;
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