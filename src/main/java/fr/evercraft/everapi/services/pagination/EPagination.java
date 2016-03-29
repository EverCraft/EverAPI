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
package fr.evercraft.everapi.services.pagination;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.ECommand;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.sponge.UtilsChat;
import fr.evercraft.everapi.text.ETextBuilder;

public class EPagination {
	private final EverAPI plugin;
	
	private TextColor pagination_color;
	private Text pagination_padding;

	private Text help_empty;
	private Text help_padding;

	private TextColor help_color_help;
	private TextColor help_color_padding;
	private TextColor help_color_description;
	
	public EPagination(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.reload();
	}
	
	public void reload() {
		this.pagination_color = UtilsChat.getTextColor(this.plugin.getMessages().getMessage("PAGINATION_COLOR"));
		this.pagination_padding = this.plugin.getMessages().getText("PAGINATION_PADDING");
		
		this.help_color_help = UtilsChat.getTextColor(this.plugin.getMessages().getMessage("HELP_COLOR_HELP"));
		this.help_color_padding = UtilsChat.getTextColor(this.plugin.getMessages().getMessage("HELP_COLOR_PADDING"));
		this.help_color_description = UtilsChat.getTextColor(this.plugin.getMessages().getMessage("HELP_COLOR_DESCRIPTION"));
		
		this.help_empty = this.plugin.getMessages().getText("HELP_EMPTY");
		this.help_padding = this.plugin.getMessages().getText("HELP_PADDING");
	}
	
	public void sendTo(Text title, List<Text> contents, CommandSource source) {		
		title = ETextBuilder.toBuilder(this.plugin.getMessages().getMessage("PAGINATION_TITLE"))
				.replace("<title>", title)
				.build().toBuilder().color(this.pagination_color).build();
		
		this.send(title, this.pagination_padding, contents, source);
	}

	public <T extends EPlugin> void helpCommand(TreeMap<String, ECommand<T>> commands, CommandSource source, EPlugin plugin) {
		List<Text> contents = new ArrayList<Text>();
		
		for(Entry<String, ECommand<T>> command : commands.entrySet()) {
			if(command.getValue().testPermission(source)) {
				Text help = command.getValue().help(source);
				Text description = command.getValue().description(source);
				if(help != null && description != null && !help.isEmpty() && !description.isEmpty()) {
					help = help.toBuilder().color(this.help_color_help).build();
					description = description.toBuilder().color(this.help_color_description).build();
					
					contents.add(ETextBuilder.toBuilder(this.plugin.getMessages().getMessage("HELP_LINE"))
								.replace("<name>", getButtonName(command.getKey(), help))
								.replace("<description>", description)
								.build());
				}
			}
		}
		
		this.help(contents, source, plugin);
	}
	
	public void helpSubCommand(LinkedHashMap<String, ESubCommand> commands, CommandSource source, EPlugin plugin) {
		List<Text> contents = new ArrayList<Text>();
		
		for(Entry<String, ESubCommand> command : commands.entrySet()) {
			Text help = command.getValue().getUsage();
			Text description = command.getValue().getDescription();
			if(help != null && description != null && !help.isEmpty() && !description.isEmpty()) {
				help = help.toBuilder().color(this.help_color_help).build();
				description = description.toBuilder().color(this.help_color_description).build();
				
				contents.add(ETextBuilder.toBuilder(this.plugin.getMessages().getMessage("HELP_LINE"))
							.replace("<name>", getButtonName(command.getKey(), help))
							.replace("<description>", description)
							.build());
			}
		}
		
		this.help(contents, source, plugin);
	}
	
	private <T extends EPlugin> void help(List<Text> contents, CommandSource source, EPlugin plugin) {
		Text title = UtilsChat.of(this.plugin.getMessages().getMessage("HELP_TITLE")
							.replaceAll("<plugin>", plugin.getName())
							.replaceAll("<version>", plugin.getVersion().orElse("1")))
						.toBuilder().color(this.help_color_padding).build();
		
		
		if(contents.isEmpty()) {
			contents.add(this.help_empty);
		}
		
		this.send(title, this.help_padding, contents, source);
	}
	
	public Text getButtonName(final String command, Text help){
		return UtilsChat.of(this.plugin.getMessages().getMessage("HELP_LINE_NAME")
				.replaceAll("<command>", command)).toBuilder()
					.onHover(TextActions.showText(
							ETextBuilder.toBuilder(this.plugin.getMessages().getMessage("HELP_LINE_NAME_HOVER"))
								.replace("<help>", help)
								.build()))
					.onClick(TextActions.suggestCommand("/" + command))
					.build();
	}
	
	private void send(final Text title, final Text padding, final List<Text> contents, CommandSource source) {
		if(source instanceof EPlayer) {
			Optional<Player> player = ((EPlayer) source).getPlayer();
			if(player.isPresent()){
				source = player.get();
			}
		}
		
		if(this.plugin.getEverAPI().getManagerService().getPagination().isPresent()) {
			this.plugin.getEverAPI().getManagerService().getPagination().get().builder()
			.padding(padding)
			.title(title)
			.contents(contents)
			.sendTo(source);
		} else {
			source.sendMessage(title);
			for(Text content : contents) {
				source.sendMessage(content);
			}
		}
	}
}
