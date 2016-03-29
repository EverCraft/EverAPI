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

import fr.evercraft.everapi.plugin.file.EMessage;

public class EAMessage extends EMessage {

	public EAMessage(final EverAPI plugin) {
		super(plugin);
	}

	@Override
	public void loadDefault() {
		// Prefix
		addDefault("prefix", 					"[&4Ever&6&lAPI&f] ");
		
		addDefault("noPermission", 				"&cErreur : Vous n'avez pas la permission !");
		addDefault("noPermissionWorld",			"&cErreur : Vous n'avez pas la permission pour ce monde !");
		addDefault("noPermissionWorldOthers", 	"&cErreur : Le joueur n'a pas la permission pour ce monde !");
		
		addDefault("worldNotFound", 			"&cErreur : Le monde '&6<world>&c' est introuvable.");
		addDefault("emptyItemInHand", 			"&cErreur : Vous n'avez aucun objet dans votre main.");
		addDefault("accountNotFound", 			"&cErreur : Le compte n'a pas été trouvé.");
		addDefault("numberInvalid", 			"&cErreur : Le nombre est invalide.");
		addDefault("serverError", 				"&cUne erreur est survenu, les administrateurs ont été contactés.");
		
		addDefault("isNotEntityType", 			"&cErreur : '<entity>' n'est pas un type d'entité.");
		addDefault("isNotNumber", 				"&cErreur : Ceci n'est pas nombre '<number>'.");
		
		addDefault("command.usage", 			"&cUtilisation :[RT]");
		addDefault("command.error", 			"&cErreur : Problème lors de l'exécution de la commande.");
		addDefault("command.errorPlayerDead", 	"&cErreur : Vous ne pouvez pas exécuter une commande quand vous êtes mort.");
		addDefault("command.errorForPlayer",	"&cErreur : Cette commande ne peut être exécutée que par un joueur.");
		
		addDefault("location.errorNumber", 		"&cLa position <name> doit être entre <min> et <max>.");
		
		// Joueur :
		addDefault("player.notFound", 			"&cErreur : Ce joueur est introuvable.");
		addDefault("player.noLookBlock", 		"&cErreur : Vous regardez aucun bloc.");
		addDefault("player.noPermissionWorld", 	"&cErreur : Vous n'avez pas la permission d'aller dans ce monde.");
		
		// Plugins :
		addDefault("plugins.message", 			"&4Ever&6&lPlugins&f(&a<count>&f) : <plugins>");
		addDefault("plugins.enable", 			"&a<plugin>");
		addDefault("plugins.disable", 			"&c<plugin>");
		addDefault("plugins.id", 				"&6ID : &7<id>");
		addDefault("plugins.version", 			"&6Version : &7<version>");
		addDefault("plugins.description", 		"&6Description : &7<description>");
		addDefault("plugins.url", 				"&6URL : &7<url>");
		addDefault("plugins.author", 			"&6Auteur(s) : &7<author>" );
		
		// Reload :
		addDefault("reload.description", 		"Recharge le plugin");
		addDefault("reload.command", 			"&7Rechargement du plugin terminé.");
		
		addDefault("hoverCopy", 				"&cCliquez ici pour copier cette information.",
												"&cClick here to copy this information.");
		
		// Cooldown
		addDefault("cooldown.message",			"&cVous ne pouvez pas effectuer cette action avant : <time>");
		
		// Time
		addDefault("time.now", 					"moins d'une seconde", "less than a second");
		addDefault("time.year", 				"<value> an", "<value> year");
		addDefault("time.years", 				"<value> ans", "<value> years");
		addDefault("time.month", 				"<value> mois", "<value> month");
		addDefault("time.months", 				"<value> mois", "<value> months");
		addDefault("time.day", 					"<value> jour", "<value> day");
		addDefault("time.days", 				"<value> jours", "<value> days");
		addDefault("time.hour", 				"<value> heure", "<value> hour");
		addDefault("time.hours", 				"<value> heures", "<value> hours");
		addDefault("time.minute", 				"<value> minute");
		addDefault("time.minutes", 				"<value> minutes");
		addDefault("time.second", 				"<value> seconde", "<value> second");
		addDefault("time.seconds", 				"<value> secondes", "<value> seconds");
		addDefault("time.join", 				" ");
		
		addDefault("gamemode.survival", 		"Survival");
		addDefault("gamemode.creative", 		"Créatif", "Creative");
		addDefault("gamemode.adventure", 		"Aventure", "Adventure");
		addDefault("gamemode.spectator", 		"Spectateur", "Spectator");
		addDefault("gamemode.noset", 			"Empty");
		
		// Arguments
		addDefault("args.player", 				"joueur", "player");
		addDefault("args.world", 				"monde", "world");
		addDefault("args.group", 				"groupe", "group");
		addDefault("args.subgroup", 			"sous-groupe", "subgroup");
		addDefault("args.inheritance", 			"inheritance");
		addDefault("args.permission", 			"permission");
		addDefault("args.option", 				"option");
		addDefault("args.value", 				"valeur", "value");
		addDefault("args.subject", 				"subject", "subject");
		addDefault("args.message", 				"message");
		addDefault("args.mail", 				"mail");
		addDefault("args.amount", 				"quantité", "amount");
		
		// Help :
		addDefault("help.title", 				"&a [ Aide : <plugin> v<version> ] ");
		addDefault("help.padding", 				"&m &r");
		addDefault("help.line", 				"<name> &6: <description> ");
		addDefault("help.lineName", 			"&6/<command>");
		addDefault("help.lineNameHover", 		"&c<help>");
		addDefault("help.empty", 				"&7Aucune commande", "&7No command");
		addDefault("help.colorPadding", 		"&a");
		addDefault("help.colorDescription", 	"&7");
		addDefault("help.colorHelp", 			"&c");
		
		// Pagination :
		addDefault("pagination.color", 			"&7");
		addDefault("pagination.padding", 		"&m &r");
		addDefault("pagination.title", 			"&6[ <title> &6]");
		
		// Mail :
		addDefault("mail.serverDisable.object", "ServerDisableException : <server>");
		addDefault("mail.serverDisable.message","Raison : <raison>");
	}

	@Override
	public void loadConfig() {
		// Prefix
		addMessage("PREFIX", "prefix");
		
		addMessage("NO_PERMISSION", "noPermission");
		addMessage("NO_PERMISSION_WORLD", "noPermissionWorld");
		addMessage("NO_PERMISSION_WORLD_OTHERS", "noPermissionWorldOthers");
		
		addMessage("WORLD_NOT_FOUND", "worldNotFound");
		addMessage("EMPTY_ITEM_IN_HAND", "emptyItemInHand");
		addMessage("ACCOUNT_NOT_FOUND", "accountNotFound");
		addMessage("NUMBER_INVALID", "numberInvalid");
		addMessage("SERVER_ERROR", "serverError");
		
		addMessage("IS_NOT_ENTITY_TYPE", "isNotEntityType");
		addMessage("IS_NOT_NUMBER", "isNotNumber");
		
		addMessage("COMMAND_USAGE", "command.usage");
		addMessage("COMMAND_ERROR", "command.error");
		addMessage("COMMAND_ERROR_PLAYER_DEAD", "command.errorPlayerDead");
		addMessage("COMMAND_ERROR_FOR_PLAYER", "command.errorForPlayer");
		
		addMessage("PLAYER_NOT_FOUND", "player.notFound");
		addMessage("PLAYER_NO_LOOK_BLOCK", "player.noLookBlock");
		
		addMessage("RELOAD_DESCRIPTION", "reload.description");
		addMessage("RELOAD_COMMAND", "reload.command");
		
		addMessage("LOCATION_ERROR_NUMBER", "location.errorNumber");
		
		addMessage("COOLDOWN", "cooldown.message");
		
		addMessage("TIME_NOW", "time.now");
		addMessage("TIME_YEAR", "time.year");
		addMessage("TIME_YEARS", "time.years");
		addMessage("TIME_MONTH", "time.month");
		addMessage("TIME_MONTHS", "time.months");
		addMessage("TIME_DAY", "time.day");
		addMessage("TIME_DAYS", "time.days");
		addMessage("TIME_HOUR", "time.hour");
		addMessage("TIME_HOURS", "time.hours");
		addMessage("TIME_MINUTE", "time.minute");
		addMessage("TIME_MINUTES", "time.minutes");
		addMessage("TIME_SECOND", "time.second");
		addMessage("TIME_SECONDS", "time.seconds");
		addMessage("TIME_JOIN", "time.join");
		
		addMessage("GAMEMODE_SURVIVAL", "gamemode.survival");
		addMessage("GAMEMODE_CREATIVE", "gamemode.creative");
		addMessage("GAMEMODE_ADVENTURE", "gamemode.adventure");
		addMessage("GAMEMODE_SPECTATOR", "gamemode.spectator");
		addMessage("GAMEMODE_NOT_SET", "gamemode.noset");
		
		addMessage("HOVER_COPY", "hoverCopy");
		
		addMessage("MAIL_SERVER_DISABLE_OBJECT", "mail.serverDisable.object");
		addMessage("MAIL_SERVER_DISABLE_MESSAGE", "mail.serverDisable.message");
		
		addMessage("HELP_TITLE", "help.title");
		addMessage("HELP_PADDING", "help.padding");
		addMessage("HELP_LINE", "help.line");
		addMessage("HELP_LINE_NAME", "help.lineName");
		addMessage("HELP_LINE_NAME_HOVER", "help.lineNameHover");
		addMessage("HELP_EMPTY", "help.empty");
		addMessage("HELP_COLOR_HELP", "help.colorHelp");
		addMessage("HELP_COLOR_PADDING", "help.colorPadding");
		addMessage("HELP_COLOR_DESCRIPTION", "help.colorDescription");
		
		addMessage("PLUGINS_MESSAGE", "plugins.message");
		addMessage("PLUGINS_ENABLE", "plugins.enable");
		addMessage("PLUGINS_DISABLE", "plugins.disable");
		addMessage("PLUGINS_ID", "plugins.id");
		addMessage("PLUGINS_VERSION", "plugins.version");
		addMessage("PLUGINS_DESCRIPTION", "plugins.description");
		addMessage("PLUGINS_URL", "plugins.url");
		addMessage("PLUGINS_AUTHOR", "plugins.author");
		
		addMessage("ARGS_PLAYER", "args.player");
		addMessage("ARGS_WORLD", "args.world");
		addMessage("ARGS_GROUP", "args.group");
		addMessage("ARGS_SUBGROUP", "args.subgroup");
		addMessage("ARGS_INHERITANCE", "args.inheritance");
		addMessage("ARGS_PERMISSION", "args.permission");
		addMessage("ARGS_OPTION", "args.option");
		addMessage("ARGS_VALUE", "args.value");
		addMessage("ARGS_SUBJECT", "args.subject");
		addMessage("ARGS_MESSAGE", "args.message");
		addMessage("ARGS_MAIL", "args.mail");
		addMessage("ARGS_AMOUNT", "args.amount");
		
		addMessage("PAGINATION_COLOR", "pagination.color");
		addMessage("PAGINATION_PADDING", "pagination.padding");
		addMessage("PAGINATION_TITLE", "pagination.title");
	}

	public String getArg(String arg) {
		return this.getMessage("ARGS_" + arg.toUpperCase());
	}
}