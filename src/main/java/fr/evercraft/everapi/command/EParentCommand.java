/*
 * EverAPI
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
package fr.evercraft.everapi.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.LiteralText.Builder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.pagination.CommandPagination;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;

public abstract class EParentCommand<T extends EPlugin> extends ECommand<T> {

	private final CopyOnWriteArrayList<ECommand<T>> commands;
	private final CopyOnWriteArrayList<ESubCommand<T>> subcommands;
	
	public EParentCommand(T plugin, String name, String... alias) {
		super(plugin, name, alias);
		
		this.commands = new CopyOnWriteArrayList<ECommand<T>>();
        this.subcommands = new CopyOnWriteArrayList<ESubCommand<T>>();
	}

	public void add(ESubCommand<T> command) {
		this.subcommands.add(command);
	}
	
	public void add(ECommand<T> command) {
		this.commands.add(command);
	}
	
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if(args.size() == 1){
			if(testPermissionHelp(source)) suggests.add("help");
			
			for(ESubCommand<T> subcommand : this.subcommands) {
				if(subcommand.testPermission(source)) { 
					suggests.add(subcommand.getSubName());
				}
			}
		} else if(args.size() >= 2) {
			int cpt = 0;
			boolean found = false;
			while(cpt < this.subcommands.size() && !found) {
				if(args.get(0).equalsIgnoreCase(this.subcommands.get(cpt).getSubName())) {
					suggests = this.subcommands.get(cpt).tabCompleter(source, args);
					found = true;
				}
				cpt++;
			}
		}
		return suggests;
	}

	public Text help(final CommandSource source) {
		Map<String, String> commands = new HashMap<String, String>();

		commands.put("help", this.getName() + "help");
		
		for(ESubCommand<T> subcommand : this.subcommands) {
			if(subcommand.testPermission(source)) { 
				commands.put(subcommand.getName(), subcommand.getSubName());
			}
		}
		
		Builder build;
		if(!commands.isEmpty()) {
			build = Text.builder("/" + this.getName() + " <");
			
			int cpt = 0;
			for(Entry<String, String> command : commands.entrySet()) {
				build = build.append(Text.builder(command.getValue()).onClick(TextActions.suggestCommand(command.getKey())).build());
				
				cpt++;
				if(cpt < commands.size()){
					build = build.append(Text.builder("|").build());
				}
			}
			
			build = build.append(Text.builder(">").build());
		} else {
			build = Text.builder(this.getName()).onClick(TextActions.suggestCommand(this.getName()));
		}
		return build.color(TextColors.RED).build();
	}

	public boolean execute(final CommandSource source, final List<String> args) throws CommandException, PluginDisableException, ServerDisableException {
		// Résultat de la commande :
		boolean resultat = false;
		
		// HELP
		if(args.size() == 0 || args.get(0).equalsIgnoreCase("help")) {
			// Si il a la permission
			if(testPermissionHelp(source)){
				resultat = commandHelp(source);
			// Il n'a pas la permission
			} else {
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
			}
		} else {
			int cpt = 0;
			boolean found = false;
			while(cpt < this.subcommands.size() && !found) {
				if(args.get(0).equalsIgnoreCase(this.subcommands.get(cpt).getSubName())) {
					resultat = this.subcommands.get(cpt).execute(source, args);
					found = true;
				}
				cpt++;
			}
			
			if(!found) {
				source.sendMessage(getHelp(source).get());
			}
		}
		return resultat;
	}
	
	private boolean commandHelp(final CommandSource source) {
		LinkedHashMap<String, CommandPagination> commands = new LinkedHashMap<String, CommandPagination>();
		
		for(ECommand<T> command : this.commands) {
			if(command.testPermission(source)) { 
				commands.put(command.getName(), command);
			}
		}
		
		for(ESubCommand<T> subcommand : this.subcommands) {
			if(subcommand.testPermission(source)) { 
				commands.put(subcommand.getName(), subcommand);
			}
		}
		this.plugin.getEverAPI().getManagerService().getEPagination().helpSubCommand(commands, source, this.plugin);
		return true;
	}
	
	public abstract boolean testPermissionHelp(final CommandSource source);
}
