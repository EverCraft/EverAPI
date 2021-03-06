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
package fr.evercraft.everapi.plugin.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.LiteralText.Builder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.exception.message.EMessageException;

public abstract class EParentCommand<T extends EPlugin<T>> extends ECommand<T> {

	private final TreeSet<ICommand> commands;
	private final TreeSet<ISubCommand> subcommands;
	
	public EParentCommand(T plugin, String name, String... alias) {
		this(plugin, name, false, alias);
	}
	
	public EParentCommand(T plugin, String name, boolean subCommand, String... alias) {
		super(plugin, name, subCommand, alias);
		
		this.commands = new TreeSet<ICommand>((o1, o2) -> o1.getName().compareTo(o2.getName()));
        this.subcommands = new TreeSet<ISubCommand>((o1, o2) -> o1.getName().compareTo(o2.getName()));
	}

	public void add(ISubCommand command) {
		this.subcommands.add(command);
	}
	
	public void add(ICommand command) {
		this.commands.add(command);
	}
	
	public Collection<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if (args.size() == 1){
			if (testPermissionHelp(source)) suggests.add("help");
			
			for (ISubCommand subcommand : this.subcommands) {
				if (subcommand.testPermission(source)) { 
					suggests.add(subcommand.getSubName());
				}
			}
		} else if (args.size() >= 2) {
			ArrayList<String> subArgs = new ArrayList<String>(args);
			subArgs.remove(0);
			
			for (ISubCommand subcommand : this.subcommands) {
				if (args.get(0).equalsIgnoreCase(subcommand.getSubName())) { 
					if (subcommand.testPermission(source)) {
						return subcommand.tabCompleter(source, subArgs);
					}
					
					break;
				}
			}
		}
		return suggests;
	}
	
	public void reload() {
		super.reload();
		
		this.commands.forEach(command -> command.reload());
		this.subcommands.forEach(command -> command.reload());
	}

	public Text help(final CommandSource source) {
		TreeMap<String, String> commands = new TreeMap<String, String>();

		commands.put(this.getName() + " help", "help");
		
		for (ISubCommand subcommand : this.subcommands) {
			if (subcommand.testPermission(source)) { 
				commands.put(subcommand.getName(), subcommand.getSubName());
			}
		}
		
		Builder build;
		if (!commands.isEmpty()) {
			build = Text.builder("/" + this.getName() + " <");
			
			int cpt = 0;
			for (Entry<String, String> command : commands.entrySet()) {
				build = build.append(Text.builder(command.getValue()).onClick(TextActions.suggestCommand("/" + command.getKey())).build());
				
				cpt++;
				if (cpt < commands.size()){
					build = build.append(Text.builder("|").build());
				}
			}
			
			build = build.append(Text.builder(">").build());
		} else {
			build = Text.builder(this.getName()).onClick(TextActions.suggestCommand(this.getName()));
		}
		return build.color(TextColors.RED).build();
	}

	public CompletableFuture<Boolean> execute(final CommandSource source, final List<String> args) throws CommandException, PluginDisableException, ServerDisableException, EMessageException {
		// HELP
		if (args.isEmpty()) {
			return this.commandDefault(source, args);
		}
		
		if (args.get(0).equalsIgnoreCase("help")) {
			if (!this.testPermissionHelp(source)) {
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
				return CompletableFuture.completedFuture(false);
			}
			
			return this.commandHelp(source);
		}
		
		ArrayList<String> subArgs = new ArrayList<String>(args);
		subArgs.remove(0);
		
		for (ISubCommand subcommand : this.subcommands) {
			if (args.get(0).equalsIgnoreCase(subcommand.getSubName())) {
				if (subcommand.testPermission(source)) { 
					return subcommand.execute(source, subArgs);
				}
				
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
				return CompletableFuture.completedFuture(false);
			}
		}
		
		for (ISubCommand subcommand : this.subcommands) {
			for (String alias : subcommand.getAlias()) {
				if (args.get(0).equalsIgnoreCase(alias)) {
					if (subcommand.testPermission(source)) { 
						return subcommand.execute(source, subArgs);
					}
					
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
					return CompletableFuture.completedFuture(false);
				}
			}
		}
		
		source.sendMessage(this.getHelp(source).get());
		return CompletableFuture.completedFuture(false);
	}
	
	protected CompletableFuture<Boolean> commandDefault(final CommandSource source, final List<String> args) {
		if (!this.testPermissionHelp(source)) {
			source.sendMessage(EAMessages.NO_PERMISSION.getText());
			return CompletableFuture.completedFuture(false);
		}
		
		return this.commandHelp(source);
	}
	
	private CompletableFuture<Boolean> commandHelp(final CommandSource source) {
		LinkedHashMap<String, ICommand> commands = new LinkedHashMap<String, ICommand>();
		
		for (ICommand command : this.commands) {
			if (command.testPermission(source)) { 
				commands.put(command.getName(), command);
			}
		}
		
		for (ISubCommand subcommand : this.subcommands) {
			if (subcommand.testPermission(source)) { 
				commands.put(subcommand.getName(), subcommand);
			}
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().helpSubCommand(commands, source, this.plugin);
		return CompletableFuture.completedFuture(false);
	}
	
	public abstract boolean testPermissionHelp(final CommandSource source);
}
