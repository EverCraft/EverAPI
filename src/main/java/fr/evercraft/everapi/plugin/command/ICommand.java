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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.exception.message.EMessageException;

public interface ICommand extends CommandCallable {

	void reload();
	
	String getName();
	
	List<String> getAlias();
	
	CommandResult process(CommandSource source, String arg) throws CommandException;
	
	List<String> getSuggestions(CommandSource source, String arguments, Location<World> targetPosition) throws CommandException;
	
	Optional<Text> getShortDescription(CommandSource source);
	
	Optional<Text> getHelp(CommandSource source);
	
	Text getUsage(CommandSource source);
	
	CompletableFuture<Boolean> execute(CommandSource source, List<String> args) throws CommandException, PluginDisableException, ServerDisableException, EMessageException;
	
	Collection<String> tabCompleter(CommandSource source, List<String> args) throws CommandException;
	
	Text help(CommandSource source);
	
	Text description(CommandSource source);
}
