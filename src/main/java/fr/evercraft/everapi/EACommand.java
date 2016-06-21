/*
 * EverAPI
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
import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.LiteralText.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.command.ECommand;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.text.ETextBuilder;

public class EACommand extends ECommand<EverAPI> {

	public EACommand(final EverAPI plugin) {
		super(plugin, "everapi");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.EVERAPI.get());
	}
	
	@Override
	public Text description(final CommandSource source) {
		return Text.of();
	}

	@Override
	public Text help(final CommandSource source) {
		boolean reload = source.hasPermission(EAPermissions.RELOAD.get());
		boolean plugins = source.hasPermission(EAPermissions.PLUGINS.get());
		
		Text help;
		if(reload || plugins){
			Builder build = Text.builder("/everapi <");
			
			if(reload){
				build = build.append(Text.builder("reload").onClick(TextActions.suggestCommand("/everapi reload")).build());
				if(plugins){
					build = build.append(Text.builder("|").build());
				}
			}
			
			if(plugins){
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
			if(source.hasPermission(EAPermissions.RELOAD.get())) {
				suggests.add("reload");
			}
			if(source.hasPermission(EAPermissions.PLUGINS.get())) {
				suggests.add("plugins");
			}
		}
		return suggests;
	}
	
	

	public Text helpReload(final CommandSource source) {
		return Text.builder("/" + this.getName() + " reload")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " reload"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text descriptionReload(final CommandSource source) {
		return EAMessages.RELOAD_DESCRIPTION.getText();
	}
	
	public Text helpPlugins(final CommandSource source) {
		return Text.builder("/" + this.getName() + " plugins")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " plugins"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text descriptionPlugins(final CommandSource source) {
		return EAMessages.PLUGINS_DESCRIPTION.getText();
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException, PluginDisableException {
		boolean resultat = false;
		if(args.size() == 0 || args.get(0).equalsIgnoreCase("help")) {
			if(source.hasPermission(EAPermissions.HELP.get())) {
				resultat = commandReload(source);
			} else {
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
			}
		} else if(args.size() == 1) {
			if(args.get(0).equalsIgnoreCase("reload")) {
				if(source.hasPermission(EAPermissions.RELOAD.get())) {
					resultat = commandReload(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("plugins")) {
				if(source.hasPermission(EAPermissions.PLUGINS.get())) {
					resultat = commandPlugins(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			// Commande de debug
			} else if(args.get(0).equalsIgnoreCase("player")){
				EPlayer player = this.plugin.getEServer().getEPlayer((Player) source).get();
				player.sendMessage(player.toString());
				resultat = true;
			} else if(args.get(0).equalsIgnoreCase("test") && source instanceof EPlayer) {
				EPlayer player = ((EPlayer) source);
				Optional<List<PotionEffect>> potions = player.get(Keys.POTION_EFFECTS);
				if(potions.isPresent()) {
					player.sendMessage("Present");
					player.sendMessage("Size : " + potions.get().size());
				} else {
					player.sendMessage("No present");
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
		player.sendMessage(EAMessages.PREFIX.getText().concat(EAMessages.RELOAD_COMMAND.getText()));
		return true;
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
