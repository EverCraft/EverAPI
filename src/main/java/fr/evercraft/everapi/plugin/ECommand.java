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
package fr.evercraft.everapi.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.Messages;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.java.Chronometer;
import fr.evercraft.everapi.java.UtilsString;
import fr.evercraft.everapi.server.player.EPlayer;

public abstract class ECommand<T extends EPlugin> implements CommandCallable {
	protected final T plugin;
	
	protected final String name;
	
	public ECommand(final T plugin, final String name, final String... alias){
		this.plugin = plugin;
		this.name = name;
		
		String[] cmds = new String[1 + alias.length];
		cmds[0] = name; 
		for(int cpt = 0; cpt < alias.length; cpt++){
			cmds[cpt + 1] = alias[cpt];
		}
		
		this.plugin.getGame().getCommandManager().register(this.plugin, this, cmds);
	}
	
	public CommandResult process(final CommandSource source, final String arg) throws CommandException {
		Chronometer chronometer = new Chronometer();
		try {
			if(this.plugin.isEnable()) {
				if(this.testPermission(source)) {		
					if(source instanceof Player){
						processPlayer((Player) source, getArg(arg));
					} else {
						execute(source, getArg(arg));
					}
				} else {
					source.sendMessage(this.plugin.getEverAPI().getPermissions().noPermission());
				}
				this.plugin.getLogger().debug("The command '" + this.name + "' with arguments '" + arg + "' was to execute in " +  chronometer.getMilliseconds().toString() + " ms");
				return CommandResult.success();
			}
		} catch (PluginDisableException e) {
			source.sendMessage(this.plugin.getMessages().getText(Messages.PREFIX).concat(this.plugin.getEverAPI().getMessages().getText(Messages.COMMAND_ERROR)));
			this.plugin.getLogger().warn(e.getMessage());
			this.plugin.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
		this.plugin.getLogger().debug("Error : The command '" + this.name + "' with arguments '" + arg + "' was to execute in " +  chronometer.getMilliseconds().toString() + " ms");
        return CommandResult.success();
	}
	
	public void processPlayer(final Player source, final List<String> args) throws CommandException, PluginDisableException, ServerDisableException {
		Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(source);
		if(player.isPresent()) {
			if(!player.get().isDead()) {
				if(source.hasPermission(this.plugin.getEverAPI().getPermissions().get("COOLDOWN_BYPASS"))) {
					execute(player.get(), args);
				} else {
					long cooldown = player.get().getCooldownTime(this.name);
					if(cooldown <= 0) {
						if(execute(player.get(), args)){
							player.get().addCooldown(this.name);
						}
					} else {
						//player.get().sendMessage(this.plugin.getEverAPI().getMessages().getMessage(Messages.)
						//		.replaceAll("<time>", this.plugin.getEverAPI().getManagerUtils().getDate().formatDateDiff(cooldown)));
					}
				}
			} else {
				player.get().sendMessage(this.plugin.getEverAPI().getMessages().getText(Messages.COMMAND_ERROR_PLAYER_DEAD));
			}
		} else {
			source.sendMessage(this.plugin.getEverAPI().getMessages().getText(Messages.PLAYER_NOT_FOUND));
		}
	}

	public List<String> getSuggestions(final CommandSource source, final String arg) throws CommandException {
		Chronometer chronometer = new Chronometer();
		if(this.plugin.isEnable() && this.testPermission(source)) {
			List<String> args = new ArrayList<String>(Arrays.asList(arg.split(" ")));
			if(args.size() >= 1 && !args.get(0).isEmpty() && arg.endsWith(" ")){
				args.add("");
			}
			
			List<String> suggests = tabCompleter(source, args);
			if(suggests == null){
				suggests = new ArrayList<String>();
				for(Player player : this.plugin.getEServer().getOnlinePlayers()){
					if(player.getName().toLowerCase().startsWith(args.get(args.size() - 1).toLowerCase())){
						suggests.add(player.getName());
					}
				}
			} else {
				if(args.size() > 0 && !args.get(args.size() - 1).isEmpty()){
					for(String suggest : new ArrayList<String>(suggests)){
						if(!suggest.toLowerCase().startsWith(args.get(args.size() - 1).toLowerCase())){
							suggests.remove(suggest);
						}
					}
				}
			}
			this.plugin.getLogger().debug("The tabulation '" + this.name + "' with arguments '" + arg + "' was to execute in " +  chronometer.getMilliseconds().toString() + " ms");
			return suggests;
		}
		return null;
	}
	
	public Optional<Text> getShortDescription(final CommandSource source) {
		if(this.plugin.isEnable() && this.testPermission(source)) {
			return Optional.ofNullable(description(source));
		}
		return Optional.empty();
	}
	
	public Optional<Text> getHelp(final CommandSource source) {
		if(this.plugin.isEnable() && this.testPermission(source)) {
			return Optional.ofNullable(help(source));
		}
		return Optional.empty();
	}
	
	public Text getUsage(final CommandSource source) {
		if(this.plugin.isEnable()){
			if(this.testPermission(source)) {
				return this.plugin.getEverAPI().getMessages().getText(Messages.COMMAND_USAGE).toBuilder()
						.append(help(source))
						.color(TextColors.RED).build();
			}
			return this.plugin.getPermissions().noPermission();
		}
		return Text.EMPTY;
	}
	
	protected List<String> getArg(final String arg) {
		List<String> args = new ArrayList<String>();
		boolean alterne = true;
		for(String guillemet : UtilsString.splitGuillemets(arg)) {
			if(alterne) {
				for(String espace : guillemet.split(" ")) {
					if(!espace.isEmpty()) {
						args.add(espace);
					}
				}
			} else {
				if(!guillemet.isEmpty()) {
					args.add(guillemet);
				}
			}
			alterne = !alterne;
		}
		this.plugin.getLogger().debug("Arguments : '" + String.join("','", args) +  "'");
		return args;
	}
	
	public String getMessage(final List<String> args){
		return String.join(" ", args);
	}
	
	public String getName(){
		return this.name;
	}
	
	public abstract boolean execute(CommandSource source, List<String> args) throws CommandException, PluginDisableException, ServerDisableException;
	
	public abstract List<String> tabCompleter(CommandSource source, List<String> args) throws CommandException;
	
	public abstract Text help(CommandSource source);
	
	public abstract Text description(CommandSource source);
}
