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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.event.ESpongeEventFactory;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.java.UtilsString;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.pagination.CommandPagination;
import fr.evercraft.everapi.util.Chronometer;

public abstract class ECommand<T extends EPlugin<?>> extends CommandPagination<T> implements CommandCallable {
	
	private final Set<String> sources;
	
	public ECommand(final T plugin, final String name, final String... alias) {
		this(plugin, name, false, alias);
	}

	public ECommand(final T plugin, final String name, boolean subCommand, final String... alias) {
		super(plugin, name);
		
		String[] cmds = new String[1 + alias.length];
		cmds[0] = name; 
		for (int cpt = 0; cpt < alias.length; cpt++){
			cmds[cpt + 1] = alias[cpt];
		}
		
		if (!subCommand) {
			this.plugin.getGame().getCommandManager().register(this.plugin, this, cmds);
		}
		this.sources = new HashSet<String>();
	}
	
	public CommandResult process(final CommandSource source, final String arg) throws CommandException {
		if (!this.plugin.isEnable()) return CommandResult.success();
		if (this.sources.contains(source.getIdentifier())) return CommandResult.success();
		
		if (!this.testPermission(source)) {
			source.sendMessage(EAMessages.NO_PERMISSION.getText());
			return CommandResult.success();
		}
		
		Chronometer chronometer = new Chronometer();
		this.sources.add(source.getIdentifier());
		try {	
			if (source instanceof Player){
				this.processPlayer((Player) source, arg, this.getArg(arg));
			} else {
				this.execute(source, this.getArg(arg))
					.thenAcceptAsync(result -> {
						this.sources.remove(source.getIdentifier());
					}, this.plugin.getGame().getScheduler().createSyncExecutor(this.plugin));
			}
		} catch (PluginDisableException e) {
			source.sendMessage(EAMessages.PREFIX.getText().concat(EAMessages.COMMAND_ERROR.getText()));
			this.plugin.getELogger().warn(e.getMessage());
			this.plugin.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
		
		this.plugin.getELogger().debug("The command '" + this.getName() + "' with arguments '" + arg + "' was to execute in " +  chronometer.getMilliseconds().toString() + " ms");
        return CommandResult.success();
	}
	
	private void processPlayer(final Player source, final String arg, final List<String> args) throws CommandException, PluginDisableException, ServerDisableException {
		EPlayer player = this.plugin.getEServer().getEPlayer(source);
		if (player.isDead()) {
			player.sendMessage(EAMessages.COMMAND_ERROR_PLAYER_DEAD.getText());
		}
		
		if (!this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createCommandEventSend(player, this.getName(), arg, args, Cause.source(this.plugin).build()))) {
			this.execute(player, args)
				.thenAcceptAsync(result -> {
					this.sources.remove(player.getIdentifier());
					this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createCommandEventResult(player, this.getName(), arg, args, result, Cause.source(this.plugin).build()));
				}, this.plugin.getGame().getScheduler().createSyncExecutor(this.plugin));
		}
	}
	
	@Override
	public List<String> getSuggestions(final CommandSource source, final String arguments, Location<World> targetPosition) throws CommandException {
		Chronometer chronometer = new Chronometer();
		if (this.plugin.isEnable() && this.testPermission(source)) {
			ArrayList<String> args = new ArrayList<String>(this.getArg(arguments));
			
			if (args.isEmpty() || arguments.endsWith(" ")) {
				args.add("");
			}
			
			Collection<String> suggests = this.tabCompleter(source, args);
			List<String> result = new ArrayList<String>();
			if (suggests == null) {
				for (Player player : this.plugin.getEServer().getOnlinePlayers()){
					if (player.getName().toLowerCase().startsWith(args.get(args.size() - 1).toLowerCase())){
						result.add(player.getName());
					}
				}
			} else {
				if (args.size() > 0 && !args.get(args.size() - 1).isEmpty()){
					for (String suggest : suggests){
						if (suggest.toLowerCase().startsWith(args.get(args.size() - 1).toLowerCase())){
							result.add(suggest);
						}
					}
				} else {
					result.addAll(suggests);
				}
			}
			this.plugin.getELogger().debug("The tabulation '" + this.getName() + "' with arguments '" + arguments + "' was to execute in " +  chronometer.getMilliseconds().toString() + " ms");
			return result;
		}
		return Arrays.asList();
	}
	
	public Optional<Text> getShortDescription(final CommandSource source) {
		if (this.plugin.isEnable() && this.testPermission(source)) {
			return Optional.ofNullable(description(source));
		}
		return Optional.empty();
	}
	
	public Optional<Text> getHelp(final CommandSource source) {
		if (this.plugin.isEnable() && this.testPermission(source)) {
			return Optional.ofNullable(help(source));
		}
		return Optional.empty();
	}
	
	public Text getUsage(final CommandSource source) {
		if (this.plugin.isEnable()){
			if (this.testPermission(source)) {
				return EAMessages.COMMAND_USAGE.getText().toBuilder()
						.append(help(source))
						.color(TextColors.RED).build();
			}
			return EAMessages.NO_PERMISSION.getText();
		}
		return Text.EMPTY;
	}
	
	protected List<String> getArg(final String arg) {
		List<String> args = new ArrayList<String>();
		boolean alterne = true;
		List<String> guillemets = UtilsString.splitGuillemets(arg);
		for (int cpt = 0; cpt < guillemets.size(); cpt++) {
			if (alterne) {
				for (String espace : guillemets.get(cpt).split(" ")) {
					if (!espace.isEmpty()) {
						args.add(espace);
					}
				}
			} else {
				if (cpt == guillemets.size()-1) {
					args.add("\"" + guillemets.get(cpt));
				} else {
					args.add(guillemets.get(cpt));
				}
			}
			alterne = !alterne;
		}
		
		this.plugin.getELogger().debug("Arguments : '" + String.join("','", args) +  "'");
		return args;
	}
	
	public abstract CompletableFuture<Boolean> execute(CommandSource source, List<String> args) throws CommandException, PluginDisableException, ServerDisableException;
	
	public abstract Collection<String> tabCompleter(CommandSource source, List<String> args) throws CommandException;
	
	public abstract Text help(CommandSource source);
	
	public abstract Text description(CommandSource source);
}
