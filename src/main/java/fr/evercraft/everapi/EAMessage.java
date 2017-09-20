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
	
	public EAMessage(final EverAPI plugin) {
		super(plugin, EAMessages.values());
	}
	
	public enum EAMessages implements EnumMessage {
		PREFIX("[&4Ever&6&lAPI&f] "),
				
		COMMAND_DESCRIPTION("Gestion de l'API"),
		COMMAND_PLUGINS_DESCRIPTION("Affiche la liste des plugins d'EverCraft"),
				
				
		// Permissions
		NO_PERMISSION(
				"&cErreur : Vous n'avez pas la permission !",
				"&cError : You don't have permission !"),
		NO_PERMISSION_WORLD(
				"&cErreur : Vous n'avez pas la permission d'aller dans ce monde.", 
				"&cError : You do not have permission to go in this world."),
		NO_PERMISSION_WORLD_OTHERS(  	
				"&cErreur : Le joueur n'a pas la permission pour ce monde !",
				"&cError : The player doesn't have permission for this world !"),
				
		// Autre
		WORLD_NOT_FOUND(
				"&cErreur : Le monde '&6{world}&c' est introuvable.",
				"&cError : The world '&6{world}&c' is not found"),
		REGION_NOT_FOUND(
				"&cErreur : La région '&6{region}&c' est introuvable."),
		EMPTY_ITEM_IN_HAND(
				"&cErreur : Vous n'avez aucun objet dans votre main.", 
				"&cError : You have no item in your hand."),
		ACCOUNT_NOT_FOUND(
				"&cErreur : Le compte n'a pas été trouvé.", 
				"&cError : The account was not found."),
		SERVER_ERROR(
				"&cUne erreur est survenu, les administrateurs ont été contactés.", 
				"&cAn error occurred, administrators were contacted."),
		IS_NOT_ENTITY_TYPE(
				"&cErreur : '{entity}' n'est pas un type d'entité.", 
				"&cError : '{entity}' is not an entity type."),
		ERROR_VARIABLE(
				"&cErreur : La variable '{number}' est incorrect."),
		IS_NOT_BOOLEAN(
				"&cErreur : '{boolean}' n'est pas un booléen (TRUE ou FALSE)."),
		IS_NOT_COLOR(
				"&cErreur : '{color}' n'est pas une couleur."),
		IS_NOT_OVERLAY("&cErreur : '{overlay}' n'est pas un overlay."),
				
				
		// Nombre
		NUMBER_INVALID("&cLe nombre est invalid."),
		IS_NOT_NUMBER(
				"&cErreur : '{number}' n'est pas nombre.", 
				"&cError : '{number}' is not a number"),
		IS_NOT_TIME(
				"&cErreur : &6'{time}' &cn'est pas une durée."),
		
		IS_NOT_DIRECTION(
				"&cErreur : '{direction}' n'est pas une direction."),
		
		// Commande
		COMMAND_USAGE(
				"&cUtilisation :[RT]", 
				"&cUsage :[RT]"),
		COMMAND_ERROR(
				"&cErreur : Un problème est survenu lors de l'exécution de la commande.", 
				"&cError : A problem occurred during the execution of the command."),
		COMMAND_ASYNC(
				"&cErreur : Cette commande est déjà encours d'exécution"),
		COMMAND_ERROR_PLAYER_DEAD(
				"&cErreur : Vous ne pouvez pas exécuter une commande quand vous êtes mort.", 
				"&cError : You can not run a command when you're dead"),
		COMMAND_ERROR_FOR_PLAYER(
				"&cErreur : Cette commande ne peut être exécutée que par un joueur.", 
				"&cError : This command can be executed only by a player."),
		
		// Player
		PLAYER_NOT_FOUND(
				"&cErreur : Le joueur &6{player} &cest introuvable.", 
				"&cError : This player is untraceable."),
		PLAYER_NO_LOOK_BLOCK(
				"&cErreur : Vous regardez aucun bloc.", 
				"&cError : You look no block."),
		PLAYER_INVENTORY_FULL(
				"&cErreur : Votre inventaire est plein."),
		PLAYER_INVENTORY_FULL_AND_DROP(
				"&cVotre inventaire est plein ! Le reste des objects est au sol."),
		
		PLAYER_ERROR_TELEPORT(
				"&cErreur : Impossible de trouver une position pour réaliser une téléportation."),
		
		GROUP_NOT_FOUND(
				"&cErreur : Ce groupe n'existe pas.", 
				"&cError: This group doesn't exist."),
		
		// Reload
		RELOAD_DESCRIPTION(
				"Permet de recharger le plugin.", 
				"Reloads the plugin."),
		RELOAD_COMMAND(
				"&7Rechargement du plugin terminé.", 
				"&7Reload complete."),
		
		// ReloadAll
		RELOAD_ALL_DESCRIPTION(
				"Permet de recharger tous les plugins EverCraft."),
		RELOAD_ALL_COMMAND(
				"&7Rechargement de tous les plugins EverCraft terminé."),
		
		LOCATION_ERROR_NUMBER(
				"&cErreur : La position {name} doit être compris entre {min} et {max}.", 
				"&cError : The position {name} must be between {min} and {max}"),
		
		// Time
		TIME_NOW(
				"moins d'une seconde", 
				"less than a second"),
		TIME_YEAR(
				"{value} an", 
				"{value} year"),
		TIME_YEARS(
				"{value} ans", 
				"{value} years"),
		TIME_MONTH( 
				"{value} mois", 
				"{value} month"),
		TIME_MONTHS( 
				"{value} mois", 
				"{value} months"),
		TIME_DAY( 
				"{value} jour", 
				"{value} day"),
		TIME_DAYS( 
				"{value} jours", 
				"{value} days"),
		TIME_HOUR( 
				"{value} heure", 
				"{value} hour"),
		TIME_HOURS( 
				"{value} heures", 
				"{value} hours"),
		TIME_MINUTE( 
				"{value} minute"),
		TIME_MINUTES( 
				"{value} minutes"),
		TIME_SECOND( 
				"{value} seconde", 
				"{value} second"),
		TIME_SECONDS( 
				"{value} secondes", 
				"{value} seconds"),
		TIME_JOIN( 
				" "),
		
		// Gamemode
		GAMEMODE_SURVIVAL( 
				"Survival"),
		GAMEMODE_CREATIVE( 
				"Créatif", 
				"Creative"),
		GAMEMODE_ADVENTURE( 
				"Aventure", 
				"Adventure"),
		GAMEMODE_SPECTATOR( 
				"Spectateur", 
				"Spectator"),
		GAMEMODE_NOT_SET( 
				"Empty"),
		
		// Copy
		HOVER_COPY( 
				"&cCliquez ici pour copier cette information.", 
				"&cClick here to copy this information."),
		HOVER_URL( 
				"&cCliquez ici pour ouvrir pour cette URL."),
		MORE_INFORMATION( 
				"&cCliquez ici pour plus d'information."),
		
		// Mail
		MAIL_SERVER_DISABLE_OBJECT( 
				"ServerDisableException : {server}"),
		MAIL_SERVER_DISABLE_MESSAGE(
				"Raison : {reason}", 
				"Reason : {reason}"),
		
		// Help
		HELP_TITLE( 
				"&a [ Aide : {plugin} v{version} &a] ", 
				"&a [ Help : {plugin} v{version} &a] "),
		HELP_TITLE_HOVER( 
				"&cAuteur(s) :[RT]&c  - {authors}"),
		HELP_AUTHORS_JOIN( 
				"[RT]&c  - "),
		HELP_AUTHORS_EMPTY( 
				"Aucun",
				"Empty"),
		HELP_PADDING( 
				"&m &r"),
		HELP_LINE( 
				"{name} &6: {description} "),
		HELP_LINE_NAME( 
				"&6/{command}"),
		HELP_LINE_NAME_HOVER( 
				"&c{help}"),
		HELP_EMPTY( 
				"&7Aucune commande", 
				"&7No command"),
		HELP_COLOR_HELP( 
				"&a"),
		HELP_COLOR_PADDING( 
				"&7"),
		HELP_COLOR_DESCRIPTION( 
				"&7"),
		
		// Plugin
		PLUGINS_MESSAGE( 
				"&4Ever&6&lPlugins&f(&a{count}&f) : {plugins}"),
		PLUGINS_ENABLE( 
				"&a{plugin}"),
		PLUGINS_DISABLE( 
				"&c{plugin}"),
		PLUGINS_ID( 
				"&6ID : &7{id}"),
		PLUGINS_VERSION( 
				"&6Version : &7{version}"),
		PLUGINS_DESCRIPTION( 
				"&6Description : &7{description}"),
		PLUGINS_URL( 
				"&6URL : &7{url}"),
		PLUGINS_AUTHOR( 
				"&6Auteur(s) : &7{author}", 
				"&6Author(s) : &7{author}"),
		
		// Argument
		ARGS_PLAYER( 
				"joueur", 
				"player"),
		ARGS_USER( 
				"pseudo", 
				"player"),
		ARGS_WORLD( 
				"monde", 
				"world"),
		ARGS_GROUP( 
				"groupe", 
				"group"),
		ARGS_SUBGROUP( 
				"sous-groupe", 
				"subgroup"),
		ARGS_INHERITANCE( 
				"inheritance"),
		ARGS_PERMISSION( 
				"permission"),
		ARGS_OPTION( 
				"option"),
		ARGS_VALUE( 
				"valeur", 
				"value"),
		ARGS_SUBJECT( 
				"subject", 
				"subject"),
		ARGS_MESSAGE( 
				"message..."),
		ARGS_MAIL( 
				"mail"),
		ARGS_AMOUNT(
				"quantité", 
				"amount"),
		ARGS_ARGUMENTS( 
				"argument..."),
		ARGS_COOLDOWN( 
				"cooldown"),
		ARGS_HOME( 
				"home"),
		ARGS_WARP( 
				"warp"),
		ARGS_REASON( 
				"raison...",
				"reason..."),
		ARGS_ENTITY( 
				"créature",
				"entity"),
		ARGS_SPEED( 
				"vitesse",
				"speed"),
		ARGS_BLOCK( 
				"bloc(s)",
				"block(s)"),
		ARGS_SECONDS( 
				"seconde(s)",
				"second(s)"),
		ARGS_MINUTES( 
				"minute(s)"),
		ARGS_DAMAGE( 
				"dégât(s)",
				"damage(s)"),
		ARGS_DISTANCE( 
				"distance"),
		ARGS_ITEM( 
				"objet",
				"item"),
		ARGS_TYPE( 
				"type"),
		ARGS_EFFECT( 
				"effet",
				"effect"),
		ARGS_AMPLIFICATION( 
				"amplification"),
		ARGS_ENCHANTMENT( 
				"enchantement",
				"enchantment"),
		ARGS_LEVEL( 
				"niveau",
				"level"),
		ARGS_COMMAND( 
				"commande",
				"command"),
		ARGS_ID( 
				"ID"),
		ARGS_RECIPIENT( 
				"destination",
				"recipient"),
		ARGS_RADIUS( 
				"rayon"),
		ARGS_ALL( 
				"all"),
		ARGS_GAMERULE( 
				"règle"),
		ARGS_NAME( 
				"nom d'épée"),
		ARGS_DESCRIPTION( 
				"description"),
		ARGS_LINE( 
				"ligne"),
		ARGS_TIME( 
				"time"),
		ARGS_IP( 
				"IP"),
		ARGS_JAIL( 
				"prison"),
		ARGS_REGION( 
				"region"),
		ARGS_FLAG( 
				"flag"),
		ARGS_FLAG_VALUE( 
				"flagValue"),
		ARGS_REGION_GROUP( 
				"region_groupe"),
		ARGS_OWNER( 
				"owner"),
		ARGS_OWNER_PLAYER( 
				"joueur_owner"),
		ARGS_OWNER_GROUP( 
				"group_owner"),
		ARGS_MEMBER( 
				"member"),
		ARGS_MEMBER_PLAYER( 
				"joueur_member"),
		ARGS_MEMBER_GROUP( 
				"groupe_member"),
		ARGS_PARENT( 
				"parent"),
		ARGS_PRIORITY( 
				"priority"),
		ARGS_DIRECTION( 
				"direction"),
		ARGS_REVERSE_AMOUNT(
				"quantité inverse", 
				"reverse-amount"),
		
		// Couleur
		COLORS_BLACK( 
				"Noir", 
				"Black"),
		COLORS_DARK_BLUE( 
				"Bleu foncé", 
				"Dark Blue"),
		COLORS_DARK_GREEN( 
				"Vert foncé", 
				"Dark Green"),
		COLORS_DARK_AQUA( 
				"Bleu ciel", 
				"Dark Aqua"),
		COLORS_DARK_RED( 
				"Rouge foncé", 
				"Dark Red"),
		COLORS_DARK_PURPLE( 
				"Violet", 
				"Dark Purple"),
		COLORS_GOLD( 
				"Or", 
				"Gold"),
		COLORS_GRAY( 
				"Gris", 
				"Gray"),
		COLORS_DARK_GRAY( 
				"Gris foncé", 
				"Dark Gray"),
		COLORS_BLUE( 
				"Bleu clair", 
				"Blue"),
		COLORS_GREEN( 
				"Vert clair", 
				"Green"),
		COLORS_AQUA( 
				"Cyan", 
				"Aqua"),
		COLORS_RED( 
				"Rouge", 
				"Red"),
		COLORS_LIGHT_PURPLE( 
				"Magenta", 
				"Light Purple"),
		COLORS_YELLOW( 
				"Jaune", 
				"Yellow"),
		COLORS_WHITE( 
				"Blanc", 
				"White"),
		
		DIRECTIONS_NORTH( 
				"Nord", 
				"North"),
		DIRECTIONS_NORTH_NORTHEAST( 
				"Nord Nord-Est", 
				"North NorthEast"),
		DIRECTIONS_NORTHEAST( 
				"Nord-Est", 
				"NorthEast"),
		DIRECTIONS_EAST_NORTHEAST( 
				"Est Nord-Est", 
				"East NorthEast"),
		DIRECTIONS_EAST( 
				"Est", 
				"East"),
		DIRECTIONS_EAST_SOUTHEAST( 
				"Est Sud-Est", 
				"East SouthEast"),
		DIRECTIONS_SOUTHEAST( 
				"Sud-Est", 
				"SouthEast"),
		DIRECTIONS_SOUTH( 
				"Sud", 
				"South"),
		DIRECTIONS_SOUTH_SOUTHWEST( 
				"Sud Sud-Ouest", 
				"South SouthWest"),
		DIRECTIONS_SOUTHWEST( 
				"Sud-Ouest", 
				"SouthWest"),
		DIRECTIONS_WEST_SOUTHWEST( 
				"Ouest Sud-Ouest", 
				"West SouthWest"),
		DIRECTIONS_WEST( 
				"Ouest", 
				"West"),
		DIRECTIONS_WEST_NORTHWEST( 
				"Ouest Nord-Ouest", 
				"West NorthWest"),
		DIRECTIONS_NORTHWEST( 
				"Nord-Ouest", 
				"NorthWest"),
		DIRECTIONS_NORTH_NORTHWEST( 
				"Nord Nord-Ouest", 
				"North NorthWest"),
		DIRECTIONS_UP( 
				"Haut", 
				"Up"),
		DIRECTIONS_DOWN( 
				"Bas", 
				"Down"),
		
		// Region
		REGION_TYPE_CUBOID_HOVER("&7Région rectangulaire."),
		REGION_TYPE_POLYGONAL_HOVER("&7Région avec plusieurs positions."),
		REGION_TYPE_TEMPLATE_HOVER("&7Région virtuelle."),
		REGION_TYPE_GLOBAL_HOVER("&7Région qui recouvre tout le monde."),
		
		REGION_GROUP_OWNER_HOVER("&7Les propriétaires de la région."),
		REGION_GROUP_MEMBER_HOVER("&7Les membres de la région"),
		REGION_GROUP_DEFAULT_HOVER("&7Les autres joueurs."),
		
		// Flag
		FLAG_DESCRIPTION("&7{description}"),
		
		FLAG_MESSAGE_JOIN("&7, "),
		FLAG_MESSAGE_CHAT("&cCHAT"),
		FLAG_MESSAGE_CHAT_MESSAGE("&6Message : &c{message}"),
		FLAG_MESSAGE_CHAT_PREFIX("&6Prefix : &c{prefix}"),
		
		FLAG_MESSAGE_ACTIONBAR("&cACTION_BAR"),
		FLAG_MESSAGE_ACTIONBAR_MESSAGE("&6Message : &c{message}"),
		FLAG_MESSAGE_ACTIONBAR_PREFIX("&6Prefix : &c{prefix}"),
		FLAG_MESSAGE_ACTIONBAR_STAY("&6Stay : &c{stay}(ms)"),
		FLAG_MESSAGE_ACTIONBAR_PRIORITY("&6Priority : &c{priority}"),
		
		FLAG_MESSAGE_TITLE("&cTITLE"),
		FLAG_MESSAGE_TITLE_MESSAGE("&6Message : &c{message}"),
		FLAG_MESSAGE_TITLE_SUBMESSAGE("&6Prefix : &c{prefix}"),
		FLAG_MESSAGE_TITLE_PREFIX("&6SubMessage : &c{submessage}"),
		FLAG_MESSAGE_TITLE_SUBPREFIX("&6SubPrefix : &c{subprefix}"),
		FLAG_MESSAGE_TITLE_STAY("&6Stay : &c{stay}(ms)"),
		FLAG_MESSAGE_TITLE_FADEIN("&6FadeIn : &c{fadein}(ms)"),
		FLAG_MESSAGE_TITLE_FADEOUT("&6FadeOut : &c{fadeout}(ms)"),
		FLAG_MESSAGE_TITLE_PRIORITY("&6Priority : &c{priority}"),
		
		FLAG_MESSAGE_BOSSBAR("&cBOSSBAR"),
		FLAG_MESSAGE_BOSSBAR_MESSAGE("&6Message : &c{message}"),
		FLAG_MESSAGE_BOSSBAR_PREFIX("&6Prefix : &c{prefix}"),
		FLAG_MESSAGE_BOSSBAR_STAY("&6Stay : &c{stay}(ms)"),
		FLAG_MESSAGE_BOSSBAR_CREATEFOG("&6CreateFog : &c{createfog}"),
		FLAG_MESSAGE_BOSSBAR_DARKENSKY("&6DarkenSky : &c{darkensky}"),
		FLAG_MESSAGE_BOSSBAR_MUSIC("&6Music : &c{music}"),
		FLAG_MESSAGE_BOSSBAR_COLOR("&6Color : &c{color}"),
		FLAG_MESSAGE_BOSSBAR_OVERLAY("&6Overlay : &c{overlay}"),
		FLAG_MESSAGE_BOSSBAR_PERCENT("&6Percent : &c{percent}"),
		FLAG_MESSAGE_BOSSBAR_PRIORITY("&6Priority : &c{priority}"),
		
		FLAG_MAP_JOIN("&7, "),
		FLAG_MAP_GROUP("&c{group}"),
		FLAG_MAP_EMPTY("&cAucun"),
		FLAG_MAP_HOVER("&c{value}"),
		FLAG_MAP_MORE("&c..."),
		
		FLAG_LOCATION("&7(&c{x}&7,&c{y}&7,&c{z}&7)"),
		FLAG_LOCATION_X("&6X : &c{x}"),
		FLAG_LOCATION_Y("&6Y : &c{y}"),
		FLAG_LOCATION_Z("&6Z : &c{z}"),
		FLAG_LOCATION_YAW("&6Yaw : &c{yaw}"),
		FLAG_LOCATION_PITCH("&6Pitch : &c{pitch}"),
		FLAG_LOCATION_WORLD("&6World : &c{world}"),
		
		// Pagination
		PAGINATION_COLOR("&7"),
		PAGINATION_PADDING("&m &r"),
		PAGINATION_TITLE("&6[ {title} &6]"), 
		
		PERMISSIONS_COMMANDS_EXECUTE(""),		
		PERMISSIONS_COMMANDS_HELP(""),
		PERMISSIONS_COMMANDS_RELOAD(""),
		PERMISSIONS_COMMANDS_PLUGINS(""),
		PERMISSIONS_COMMANDS_TEST(""),
		PERMISSIONS_COMMANDS_BLOCKINFO(""),
		PERMISSIONS_COMMANDS_DEBUG(""),
		PERMISSIONS_WORLDS("");
		
		private final String path;
	    private final EMessageBuilder french;
	    private final EMessageBuilder english;
	    private EMessageFormat message;
	    private EMessageBuilder builder;
	    
	    private EAMessages(final String french) {   	
	    	this(EMessageFormat.builder().chat(new EFormatString(french), true));
	    }
	    
	    private EAMessages(final String french, final String english) {   	
	    	this(EMessageFormat.builder().chat(new EFormatString(french), true), 
	    		EMessageFormat.builder().chat(new EFormatString(english), true));
	    }
	    
	    private EAMessages(final EMessageBuilder french) {   	
	    	this(french, french);
	    }
	    
	    private EAMessages(final EMessageBuilder french, final EMessageBuilder english) {
	    	Preconditions.checkNotNull(french, "Le message '" + this.name() + "' n'est pas définit");
	    	
	    	this.path = this.resolvePath();	    	
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

	@Override
	public EnumMessage getPrefix() {
		return EAMessages.PREFIX;
	}
}
