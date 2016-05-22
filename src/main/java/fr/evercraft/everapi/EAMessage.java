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
package fr.evercraft.everapi;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;

import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class EAMessage extends EMessage {
	
	public enum Messages implements EnumMessage {
		PREFIX("prefix",  							
				"[&4Ever&6&lAPI&f] "),
				
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
		
		// Nombre
		NUMBER_INVALID("numberInvalid",
				"&cUne erreur est survenu, les administrateurs ont été contactés.", 
				"&cAn error occurred, administrators were contacted."),
		IS_NOT_NUMBER("isNotNumber", 
				"&cErreur : Ceci n'est pas nombre '<number>'.", 
				"&cError : '<number>' is not a number"),
		
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
				"&a [ Aide : <plugin> v<version> ] ", 
				"&a [ Help : <plugin> v<version> ] "),
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
				"&c"),
		
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
		
		// Format
		FORMAT_OBFUSCATED("format.obfuscated", 
				"Aléatoire", 
				"Random"),
		FORMAT_BOLD("format.bold", 
				"Gras", 
				"Bold"),
		FORMAT_STRIKETHROUGH("format.strikethrough", 
				"Barré", 
				"Strikethrough"),
		FORMAT_UNDERLINE("format.underline", 
				"Souligné", 
				"Underline"),
		FORMAT_ITALIC("format.italic", 
				"Italique", 
				"Italic"),
		FORMAT_RESET("format.reset", 
				"Réinitialisation", 
				"Reset"),
		
		// Pagination
		PAGINATION_COLOR("pagination.color", 
				"&7"),
		PAGINATION_PADDING("pagination.padding", 
				"&m &r"),
		PAGINATION_TITLE("pagination.title", 
				"&6[ <title> &6]");
		
		private final String path;
	    private final Object french;
	    private final Object english;
	    
	    private Messages(final String path, final Object french) {   	
	    	this(path, french, french);
	    }
	    
	    private Messages(final String path, final Object french, final Object english) {   	
	    	this.path = path;	    	
	    	this.french = french;
	    	this.english = english;
	    }

	    public String getName() {
			return this.name();
		}
	    
		public String getPath() {
			return this.path;
		}

		public Object getFrench() {
			return this.french;
		}

		public Object getEnglish() {
			return this.english;
		}
	}

	public EAMessage(final EverAPI plugin) {
		super(plugin);
	}

	@Override
	public void loadDefault() {
	}

	@Override
	public void loadConfig() {
		for(Messages message : Messages.values()) {
			add(message.getName(), message.getPath(), message.getFrench(), message.getEnglish());
		}
	}

	public String getArg(String arg) {
		return this.getMessage("ARGS_" + arg.toUpperCase());
	}
	
	public String getColor(TextColor color) {
		return this.getMessage("COLORS_" + color.getName().toUpperCase());
	}
	
	public Text getCommandError() {
		return this.getText("COMMAND_ERROR");
	}
}
