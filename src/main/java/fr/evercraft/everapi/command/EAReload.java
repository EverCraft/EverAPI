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
package fr.evercraft.everapi.command;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.Messages;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.ReloadEvent;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.ECommand;

public class EAReload extends ECommand<EverAPI> {

	public EAReload(final EverAPI plugin) {
		super(plugin, "reload");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(this.plugin.getPermissions().get("RELOAD_ALL"));
	}
	
	@Override
	public Text description(final CommandSource source) {
		return Text.of();
	}

	@Override
	public Text help(final CommandSource source) {
		return Text.builder("/reload")
				.onClick(TextActions.suggestCommand("/reload"))
				.color(TextColors.RED)
				.build();
	}

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return new ArrayList<String>();
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException, PluginDisableException {
		return commandReload(source);
	}
	
	private boolean commandReload(CommandSource player) {
		this.plugin.getGame().getEventManager().post(new ReloadEvent(this.plugin));
		player.sendMessage(EChat.of(this.plugin.getMessages().getMessage(Messages.PREFIX) + this.plugin.getEverAPI().getMessages().getMessage(Messages.RELOAD_ALL_COMMAND)));
		return true;
	}
}
