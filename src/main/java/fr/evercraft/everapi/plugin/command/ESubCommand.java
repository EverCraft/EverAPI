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

import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.pagination.CommandPagination;

public abstract class ESubCommand<T extends EPlugin> implements CommandPagination {
	protected final T plugin;
	
	private final String parent_name;
	private final String sub_name;
	
	public ESubCommand(final T plugin, final ECommand<T> command, final String sub_name){
		this.plugin = plugin;
		this.parent_name = command.getName();
		this.sub_name = sub_name;
	}
	
	public String getParentName() {
		return this.parent_name;
	}
	
	public String getSubName() {
		return this.sub_name;
	}
	
	public String getName() {
		return this.parent_name + " " + this.sub_name;
	}
	
	public boolean execute(CommandSource source, List<String> args) throws CommandException, PluginDisableException, ServerDisableException {
		if(this.testPermission(source)) {		
			if(!args.isEmpty()) {
				args.remove(0);
				return this.subExecute(source, args);
			}
		} else {
			source.sendMessage(EAMessages.NO_PERMISSION.getText());
		}
		return false;
	}
	
	public List<String> tabCompleter(CommandSource source, List<String> args) throws CommandException {
		if(!args.isEmpty() && this.testPermission(source)) {
			args.remove(0);
			return this.subTabCompleter(source, args);
		}
		return Arrays.asList();
	}
	
	public abstract boolean subExecute(CommandSource source, List<String> args) throws CommandException, PluginDisableException, ServerDisableException;
	
	public abstract List<String> subTabCompleter(CommandSource source, List<String> args) throws CommandException;
	
	public abstract Text help(CommandSource source);
	
	public abstract Text description(CommandSource source);
	
	public abstract boolean testPermission(CommandSource source);

}
