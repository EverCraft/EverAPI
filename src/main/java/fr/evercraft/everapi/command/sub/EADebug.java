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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.sponge.UtilsCause;

public class EADebug extends ESubCommand<EverAPI> {
	
	public EADebug(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "debug");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.DEBUG.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("Debug");
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
		if (UtilsCause.debug) {
			UtilsCause.debug = false;
			source.sendMessage(EChat.of("[Debug] &cDisable"));
		} else {
			UtilsCause.debug = true;
			source.sendMessage(EChat.of("[Debug] &aEnable"));
		}
		return CompletableFuture.completedFuture(true);
	}
}
