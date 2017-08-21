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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.plugin.command.ESubCommand;

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
	
	public Collection<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return Arrays.asList();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName())
					.onClick(TextActions.suggestCommand("/" + this.getName()))
					.color(TextColors.RED)
					.build();
	}
	
	public CompletableFuture<Boolean> execute(final CommandSource source, final List<String> args) {
		if (args.size() == 0) {
			return this.commandPlugins(source);
		}
		source.sendMessage(this.help(source));
		return CompletableFuture.completedFuture(false);
	}

	private CompletableFuture<Boolean> commandPlugins(CommandSource player) {
		Collection<EPlugin<?>> plugins = getEPlugins();
		List<Text> list = new ArrayList<Text>();
		for (EPlugin<?> plugin :  plugins){
			List<Text> hover = new ArrayList<Text>();
			hover.add(EAMessages.PLUGINS_ID.getFormat().toText("{id}", () -> plugin.getId()));
			if (plugin.getVersion().isPresent()) {
				hover.add(EAMessages.PLUGINS_VERSION.getFormat().toText("{version}", () -> plugin.getVersion().get()));
			}
			
			if (plugin.getDescription().isPresent()) {
				hover.add(EAMessages.PLUGINS_DESCRIPTION.getFormat().toText("{description}", () -> plugin.getDescription().get()));
			}
			
			if (plugin.getUrl().isPresent()) {
				hover.add(EAMessages.PLUGINS_URL.getFormat().toText("{url}", () -> plugin.getUrl().get()));
			}
			
			if (!plugin.getAuthors().isEmpty()) {
				hover.add(EAMessages.PLUGINS_AUTHOR.getFormat().toText("{author}", () -> String.join(", ", plugin.getAuthors())));
			}
			
			list.add((plugin.isEnable() ? EAMessages.PLUGINS_ENABLE : EAMessages.PLUGINS_DISABLE).getFormat().toText("{plugin}", () -> plugin.getName()).toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
				.build());
		}
		
		EAMessages.PLUGINS_MESSAGE.sender()
			.replace("{count}", () -> String.valueOf(plugins.size()))
			.replace("{plugins}", () -> Text.joinWith(Text.of(", "), list))
			.sendTo(player);
		return CompletableFuture.completedFuture(true);
	}
	
	private Collection<EPlugin<?>> getEPlugins(){
		Collection<EPlugin<?>> plugins = new ArrayList<EPlugin<?>>();
		for (PluginContainer plugin : this.plugin.getGame().getPluginManager().getPlugins()){
			if (plugin.getInstance().isPresent())
				if (plugin.getInstance().get() instanceof EPlugin){
					EPlugin<?> pl = (EPlugin<?>) plugin.getInstance().get();
					plugins.add(pl);
				}
		}
		return plugins;
	}
}
