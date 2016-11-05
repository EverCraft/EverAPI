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
package fr.evercraft.everapi.services.pagination;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeSet;

import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;
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
		this.pagination_color = EAMessages.PAGINATION_COLOR.getColor();
		this.pagination_padding = EAMessages.PAGINATION_PADDING.getText();
		
		this.help_color_help = EAMessages.HELP_COLOR_HELP.getColor();
		this.help_color_padding = EAMessages.HELP_COLOR_PADDING.getColor();
		this.help_color_description = EAMessages.HELP_COLOR_DESCRIPTION.getColor();
		
		this.help_empty = EAMessages.HELP_EMPTY.getText();
		this.help_padding = EAMessages.HELP_PADDING.getText();
	}
	
	public void sendTo(Text title, List<Text> contents, CommandSource source) {		
		title = ETextBuilder.toBuilder(EAMessages.PAGINATION_TITLE.get())
				.replace("<title>", title)
				.build().toBuilder().color(this.pagination_color).build();
		
		this.send(title, this.pagination_padding, contents, source);
	}
	
	public void helpCommands(TreeSet<CommandMapping> commands, Text title, CommandSource source) {
		List<Text> contents = new ArrayList<Text>();
		
		for (CommandMapping command : commands) {
			if (command.getCallable().testPermission(source)) {
				Optional<Text> optHelp = command.getCallable().getHelp(source);
				Optional<Text> optDescription = command.getCallable().getShortDescription(source);
				if (optHelp.isPresent() && optDescription.isPresent() && !optHelp.get().isEmpty() && !optDescription.get().isEmpty()) {
					Text help = optHelp.get().toBuilder().color(this.help_color_help).build();
					Text description = optDescription.get().toBuilder().color(this.help_color_description).build();
					
					contents.add(ETextBuilder.toBuilder(EAMessages.HELP_LINE.get())
								.replace("<name>", getButtonName(command.getPrimaryAlias(), help))
								.replace("<description>", description)
								.build());
				}
			}
		}
		
		if (contents.isEmpty()) {
			contents.add(this.help_empty);
		}
		
		this.send(title.toBuilder().color(this.help_color_padding).build(), this.help_padding, contents, source);
	}
	
	public void helpSubCommand(LinkedHashMap<String, CommandPagination<?>> commands, CommandSource source, EPlugin<?> plugin) {
		List<Text> contents = new ArrayList<Text>();
		
		for (Entry<String, CommandPagination<?>> command : commands.entrySet()) {
			Text help = command.getValue().help(source);
			Text description = command.getValue().description(source);
			if (help != null && description != null && !help.isEmpty() && !description.isEmpty()) {
				help = help.toBuilder().color(this.help_color_help).build();
				description = description.toBuilder().color(this.help_color_description).build();
				
				contents.add(ETextBuilder.toBuilder(EAMessages.HELP_LINE.get())
							.replace("<name>", getButtonName(command.getKey(), help))
							.replace("<description>", description)
							.build());
			}
		}
		
		this.help(contents, source, plugin);
	}
	
	private void help(List<Text> contents, CommandSource source, EPlugin<?> plugin) {
		Builder title = EChat.of(EAMessages.HELP_TITLE.get()
							.replaceAll("<plugin>", plugin.getName())
							.replaceAll("<version>", plugin.getVersion().orElse("1")))
						.toBuilder().color(this.help_color_padding);
		
		String authors;
		if (plugin.getAuthors().isEmpty()) {
			authors = EAMessages.HELP_AUTHORS_EMPTY.get();
		} else {
			authors = String.join(EAMessages.HELP_AUTHORS_JOIN.get(), plugin.getAuthors());
		}
		
		if (EAMessages.HELP_TITLE_HOVER.has()) {
			title = title.onHover(TextActions.showText(EChat.of(EAMessages.HELP_TITLE_HOVER.get()
							.replaceAll("<authors>", authors)
							.replaceAll("<plugin>", plugin.getName())
							.replaceAll("<version>", plugin.getVersion().orElse("1")))));
		}

		if (contents.isEmpty()) {
			contents.add(this.help_empty);
		}
		
		this.send(title.build(), this.help_padding, contents, source);
	}
	
	public Text getButtonName(final String command, Text help){
		return EChat.of(EAMessages.HELP_LINE_NAME.get()
				.replaceAll("<command>", command)).toBuilder()
					.onHover(TextActions.showText(
							ETextBuilder.toBuilder(EAMessages.HELP_LINE_NAME_HOVER.get())
								.replace("<help>", help)
								.build()))
					.onClick(TextActions.suggestCommand("/" + command))
					.build();
	}
	
	private void send(final Text title, final Text padding, final List<Text> contents, CommandSource source) {
		if (source instanceof EPlayer) {
			source = ((EPlayer) source).get();
		}
		
		if (this.plugin.getEverAPI().getManagerService().getPagination().isPresent()) {
			this.plugin.getEverAPI().getManagerService().getPagination().get().builder()
			.padding(padding)
			.title(title)
			.contents(contents)
			.sendTo(source);
		} else {
			source.sendMessage(title);
			for (Text content : contents) {
				source.sendMessage(content);
			}
		}
	}
}
