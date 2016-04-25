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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.LiteralText.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.ECommand;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.text.ETextBuilder;

public class EACommand extends ECommand<EverAPI> {

	public EACommand(final EverAPI plugin) {
		super(plugin, "everapi");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(this.plugin.getPermissions().get("EVERAPI"));
	}
	
	@Override
	public Text description(final CommandSource source) {
		return Text.of();
	}

	@Override
	public Text help(final CommandSource source) {
		Text help;
		if(	source.hasPermission(this.plugin.getPermissions().get("RELOAD")) ||
			source.hasPermission(this.plugin.getPermissions().get("PLUGINS"))){
			Builder build = Text.builder("/everapi <");
			
			if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))){
				build = build.append(Text.builder("reload").onClick(TextActions.suggestCommand("/everapi reload")).build());
				if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))){
					build = build.append(Text.builder("|").build());
				}
			}
			
			if(source.hasPermission(this.plugin.getPermissions().get("PLUGINS"))){
				build = build.append(Text.builder("plugins").onClick(TextActions.suggestCommand("/everapi plugins")).build());
			}
			
			build = build.append(Text.builder(">").build());
			help = build.color(TextColors.RED).build();
		} else {
			help = Text.builder("/everapi").onClick(TextActions.suggestCommand("/everapi"))
					.color(TextColors.RED).build();
		}
		return help;
	}

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if(args.size() == 1) {
			if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
				suggests.add("reload");
			}
			if(source.hasPermission(this.plugin.getPermissions().get("PLUGINS"))) {
				suggests.add("plugins");
			}
		}
		return suggests;
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException, PluginDisableException {
		boolean resultat = false;
		if(args.size() == 1) {
			if(args.get(0).equalsIgnoreCase("reload")) {
				if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
					resultat = commandReload(source);
				} else {
					source.sendMessage(this.plugin.getPermissions().noPermission());
				}
			} else if(args.get(0).equalsIgnoreCase("plugins")) {
				if(source.hasPermission(this.plugin.getPermissions().get("PLUGINS"))) {
					resultat = commandPlugins(source);
				} else {
					source.sendMessage(this.plugin.getPermissions().noPermission());
				}
			// Commande de debug
			} else if(args.get(0).equalsIgnoreCase("player")){
				EPlayer player = this.plugin.getEServer().getEPlayer((Player) source).get();
				player.sendMessage(player.toString());
				resultat = true;
			} else if(args.get(0).equalsIgnoreCase("test")) {
				EPlayer player = ((EPlayer) source);
				if(this.plugin.getManagerService().getTitle().get().has(player.getUniqueId())) {
					player.sendMessage(this.plugin.getManagerService().getTitle().get().get(player.getUniqueId()).get().toString());
				} else {
					player.sendMessage("No title");
				}
				
				if(this.plugin.getManagerService().getActionBar().get().has(player.getUniqueId())) {
					player.sendMessage(this.plugin.getManagerService().getActionBar().get().get(player.getUniqueId()).get().toString());
				} else {
					player.sendMessage("No ActionBar");
				}
			} else {
				source.sendMessage(help(source));
			}
		} else {
			source.sendMessage(help(source));
		}
		return resultat;
	}
	
	private boolean commandReload(CommandSource player) {
		this.plugin.reload();
		player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getEverAPI().getMessages().getMessage("RELOAD_COMMAND")));
		return true;
	}
	
	private boolean commandPlugins(CommandSource player) {
		Collection<EPlugin> plugins = getEPlugins();
		List<Text> list = new ArrayList<Text>();
		for (EPlugin plugin :  plugins){
			List<Text> hover = new ArrayList<Text>();
			hover.add(EChat.of((this.plugin.getMessages().getMessage("PLUGINS_ID").replaceAll("<id>", plugin.getId()))));
			
			if(plugin.getVersion().isPresent()) {
				hover.add(EChat.of(this.plugin.getMessages().getMessage("PLUGINS_VERSION").replaceAll("<version>", plugin.getVersion().get())));
			}
			
			if(plugin.getDescription().isPresent()) {
				hover.add(EChat.of(this.plugin.getMessages().getMessage("PLUGINS_DESCRIPTION").replaceAll("<description>", plugin.getDescription().get())));
			}
			
			if(plugin.getUrl().isPresent()) {
				hover.add(EChat.of(this.plugin.getMessages().getMessage("PLUGINS_URL").replaceAll("<url>", plugin.getUrl().get())));
			}
			
			if(!plugin.getAuthors().isEmpty()) {
				hover.add(EChat.of(this.plugin.getMessages().getMessage("PLUGINS_AUTHOR").replaceAll("<author>", String.join(", ", plugin.getAuthors()))));
			}
			
			if (plugin.isEnable()){
				list.add(EChat.of(this.plugin.getMessages().getMessage("PLUGINS_ENABLE").replace("<plugin>", plugin.getName())).toBuilder()
						.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
						.build());	
			} else {
				list.add(EChat.of(this.plugin.getMessages().getMessage("PLUGINS_DISABLE").replace("<plugin>", plugin.getName())).toBuilder()
						.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
						.build());	
			}
		}
		player.sendMessage(ETextBuilder.toBuilder(this.plugin.getMessages().getMessage("PLUGINS_MESSAGE")
					.replaceAll("<count>", String.valueOf(plugins.size())))
				.replace("<plugins>", Text.joinWith(Text.of(", "), list))
				.build());
		return true;
	}
	
	private Collection<EPlugin> getEPlugins(){
		Collection<EPlugin> plugins = new ArrayList<EPlugin>();
		for (PluginContainer plugin : this.plugin.getGame().getPluginManager().getPlugins()){
			if (plugin.getInstance().isPresent())
				if (plugin.getInstance().get() instanceof EPlugin){
					EPlugin pl = (EPlugin) plugin.getInstance().get();
					plugins.add(pl);
				}
		}
		return plugins;
	}
}
