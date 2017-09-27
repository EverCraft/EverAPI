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
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.selector.Selector;
import org.spongepowered.api.text.selector.SelectorType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.ImmutableList;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.event.ESpongeEventFactory;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.exception.message.EMessageException;
import fr.evercraft.everapi.java.UtilsString;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.pagination.CommandPagination;
import fr.evercraft.everapi.util.Chronometer;

public abstract class ECommand<T extends EPlugin<T>> extends CommandPagination<T> implements CommandCallable, ICommand {
	
	public static final String PERMISSION_SELECTOR = "minecraft.command.selector";

	private final Set<String> sources;
	
	private boolean enable;
	private final boolean subCommand;
	private final String name;
	private final List<String> alias;
	
	public ECommand(final T plugin, final String name, final String... alias) {
		this(plugin, name, false, alias);
	}

	public ECommand(final T plugin, final String name, boolean subCommand, final String... alias) {
		super(plugin, name);
		
		this.name = name;
		this.alias = ImmutableList.copyOf(Arrays.asList(alias));
		
		this.sources = new HashSet<String>();
		this.enable = false;
		this.subCommand = subCommand;
		
		if (!this.subCommand) {
			this.load();
		}
	}
	
	public List<String> getAlias() {
		return this.alias;
	}
	
	public void load() {
		boolean enable = !this.plugin.getConfigs().get(EConfig.DISABLE_COMMANDS).getChildrenList().stream()
				.filter(command -> this.getName().equalsIgnoreCase(command.getString("")))
				.findAny().isPresent();
		
		if (!enable) {
			this.plugin.getELogger().info("[Command] The '" + this.getName() + "' command is disabled");
		}
		
		if (enable && !this.enable) {
			this.enable = true;
			this.plugin.getGame().getCommandManager().register(this.plugin, this, ImmutableList.<String>builder().add(this.name).addAll(this.alias).build());
		} else if (!enable && this.enable) {
			this.enable = false;
			this.plugin.getGame().getCommandManager().getOwnedBy(this.plugin).stream()
					.filter(command -> command.getPrimaryAlias().equals(this.getName()))
					.forEach(command -> this.plugin.getGame().getCommandManager().removeMapping(command));
		}
	}
	
	public void reload() {
		if (this.subCommand) return;
		
		this.load();
	}
	
	@Override
	public CommandResult process(final CommandSource source, final String arg) throws CommandException {
		if (!this.plugin.isEnable()) return CommandResult.success();
		
		if (this.sources.contains(source.getIdentifier())) {
			EAMessages.COMMAND_ASYNC.sender()
				.prefix(this.plugin.getMessages().getPrefix())
				.sendTo(source);
			return CommandResult.success();
		}
		
		if (!this.testPermission(source)) {
			EAMessages.NO_PERMISSION.sender()
			.prefix(this.plugin.getMessages().getPrefix())
				.sendTo(source);
			return CommandResult.success();
		}
		
		this.sources.add(source.getIdentifier());
		
		try {
			return this.processExecute(source, arg);
		} catch (PluginDisableException e) {
			this.sources.remove(source.getIdentifier());
			EAMessages.COMMAND_ERROR.sender()
				.prefix(this.plugin.getMessages().getPrefix())
				.sendTo(source);
			this.plugin.getELogger().warn("PluginDisableException : " + e.getMessage());
			this.plugin.disable();
		} catch (ServerDisableException e) {
			this.sources.remove(source.getIdentifier());
			e.execute();
		} catch (EMessageException e) {
			this.sources.remove(source.getIdentifier());
			e.execute(this.plugin.getMessages().getPrefix());
		} catch(Exception e) {
			this.sources.remove(source.getIdentifier());
			throw e;
		}
		
		return CommandResult.empty();
	}
	
	private CommandResult processExecute(final CommandSource source, final String argument) throws CommandException, PluginDisableException, ServerDisableException, EMessageException {
		Chronometer chronometer = new Chronometer();
		
		List<String> arguments = this.getArg(argument);
		if (source instanceof Player) {
			this.processPlayer((Player) source, argument, arguments);
		} else {
			this.execute(source, arguments)
				.exceptionally(e -> {
					EAMessages.COMMAND_ERROR.sender()
						.prefix(this.plugin.getMessages().getPrefix())
						.sendTo(source);
					this.plugin.getELogger().warn("CompletableFuture : " + e.getMessage());
					e.printStackTrace();
					return false;
				})
				.thenAcceptAsync(result -> this.sources.remove(source.getIdentifier()), 
					this.plugin.getGame().getScheduler().createSyncExecutor(this.plugin));
		}
		
		this.plugin.getELogger().debug("The command '" + this.getName() + "' with arguments '" + argument + "' was to execute in " +  chronometer.getMilliseconds().toString() + " ms");
        return CommandResult.success();
	}
	
	private void processPlayer(final Player source, final String arg, final List<String> args) throws CommandException, PluginDisableException, ServerDisableException, EMessageException {
		EPlayer player = this.plugin.getEServer().getEPlayer(source);
		if (player.isDead()) {
			EAMessages.COMMAND_ERROR_PLAYER_DEAD.sender()
				.prefix(this.plugin.getMessages().getPrefix())
				.sendTo(source);
		}
		
		if (!this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createCommandEventSend(player, this.getName(), arg, args, Cause.source(this.plugin).build()))) {
			this.execute(player, args)
				.exceptionally(e -> {
					EAMessages.COMMAND_ERROR.sender()
						.prefix(this.plugin.getMessages().getPrefix())
						.sendTo(source);
					this.plugin.getELogger().warn("CompletableFuture : " + e.getMessage());
					e.printStackTrace();
					return false;
				})
				.thenAcceptAsync(result -> {
					this.sources.remove(player.getIdentifier());
					this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createCommandEventResult(player, this.getName(), arg, args, result, Cause.source(this.plugin).build()));
				}, this.plugin.getThreadSync());
		}
	}
	
	@SuppressWarnings("unused")
	private boolean processSelector(final CommandSource source, final String argument) throws CommandException, PluginDisableException, ServerDisableException, EMessageException {
		int start = -1;
		int cpt = -1;
		int open = 0;
		boolean type = false;
		
		for (char c : argument.toCharArray()) {
			cpt++;
			
			if (start == -1) {
				if (c != '@') continue;
				if (cpt != 0 && argument.charAt(cpt-1) != ' ') continue;
				start = cpt;
			} else if (cpt == start + 1) {
				if (this.plugin.getGame().getRegistry().getAllOf(SelectorType.class).stream().filter(t -> t.getName().equals(String.valueOf(c))).findAny().isPresent()) {
					type = true;
				} else {
					start = -1;
				}
			} else if (cpt == start + 2) {
				if (c == ' ') {
					cpt--;
					break;
				}
				if (c == '[') {
					open = 1;
				} else {
					start = -1;
					type = false;
				}
			} else {
				if (c == '[') {
					open++;
				} else if (c == ']') {
					open--;
					if (open == 0) {
						break;
					}
				}
			}
		}
		
		if (start != -1 && open == 0 && type) {
			String stringSelector = argument.substring(start, cpt+1);
			String first = (start == 0) ? "" : argument.substring(0, start);
			String last = (cpt+1 == argument.length()) ? "" : argument.substring(cpt+1, argument.length());
			
			try {
				List<Entity> players = new ArrayList<Entity>(Selector.parse(stringSelector).resolve(source));
				players.removeIf(entity -> !(entity instanceof Player));
				
				if (players.isEmpty()) return false;
				
				for (Entity player : players) {
					this.processExecute(source, first + ((Player) player).getName() + last);
				}
				return true;
			} catch (IllegalArgumentException e) {}
		}
		return false;
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
	
	public abstract CompletableFuture<Boolean> execute(CommandSource source, List<String> args) throws CommandException, PluginDisableException, ServerDisableException, EMessageException;
	
	public abstract Collection<String> tabCompleter(CommandSource source, List<String> args) throws CommandException;
	
	public abstract Text help(CommandSource source);
	
	public abstract Text description(CommandSource source);
}
