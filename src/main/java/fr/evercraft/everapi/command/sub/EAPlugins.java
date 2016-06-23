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
package fr.evercraft.everapi.command.sub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.text.ETextBuilder;

public class EAPlugins extends ESubCommand<EverAPI> {
	public EAPlugins(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "plugins");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.PLUGINS.get());
	}

	public Text description(final CommandSource source) {
		return EAMessages.COMMAND_PLUGINS_DESCRIPTION.getText();
	}
	
	public List<String> subTabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return new ArrayList<String>();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName())
					.onClick(TextActions.suggestCommand("/" + this.getName()))
					.color(TextColors.RED)
					.build();
	}
	
	public boolean subExecute(final CommandSource source, final List<String> args) {
		if(args.size() == 0) {
			return commandPlugins(source);
		}
		source.sendMessage(this.help(source));
		return false;
	}

	private boolean commandPlugins(CommandSource player) {
		Collection<EPlugin> plugins = getEPlugins();
		List<Text> list = new ArrayList<Text>();
		for (EPlugin plugin :  plugins){
			List<Text> hover = new ArrayList<Text>();
			hover.add(EChat.of(EAMessages.PLUGINS_ID.get().replaceAll("<id>", plugin.getId())));
			
			if(plugin.getVersion().isPresent()) {
				hover.add(EChat.of(EAMessages.PLUGINS_VERSION.get().replaceAll("<version>", plugin.getVersion().get())));
			}
			
			if(plugin.getDescription().isPresent()) {
				hover.add(EChat.of(EAMessages.PLUGINS_DESCRIPTION.get().replaceAll("<description>", plugin.getDescription().get())));
			}
			
			if(plugin.getUrl().isPresent()) {
				hover.add(EChat.of(EAMessages.PLUGINS_URL.get().replaceAll("<url>", plugin.getUrl().get())));
			}
			
			if(!plugin.getAuthors().isEmpty()) {
				hover.add(EChat.of(EAMessages.PLUGINS_AUTHOR.get().replaceAll("<author>", String.join(", ", plugin.getAuthors()))));
			}
			
			if (plugin.isEnable()){
				list.add(EChat.of(EAMessages.PLUGINS_ENABLE.get().replace("<plugin>", plugin.getName())).toBuilder()
						.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
						.build());	
			} else {
				list.add(EChat.of(EAMessages.PLUGINS_DISABLE.get().replace("<plugin>", plugin.getName())).toBuilder()
						.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
						.build());	
			}
		}
		player.sendMessage(ETextBuilder.toBuilder(EAMessages.PLUGINS_MESSAGE.get()
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
