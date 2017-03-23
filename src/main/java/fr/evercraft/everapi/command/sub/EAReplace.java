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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.replace.EReplacesPlayer;
import fr.evercraft.everapi.message.replace.EReplacesServer;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.server.player.EPlayer;

public class EAReplace extends ESubCommand<EverAPI> {
	
	public EAReplace(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "replace");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.TEST.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("La liste des replaces");
	}
	
	public Collection<String> subTabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return Arrays.asList();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName())
					.onClick(TextActions.suggestCommand("/" + this.getName()))
					.color(TextColors.RED)
					.build();
	}
	
	public boolean subExecute(final CommandSource source, final List<String> args) {
		if (source instanceof EPlayer) {
			return this.replace((EPlayer) source);
		} else {
			return this.replace(source);
		}
	}
	
	public boolean replace(final CommandSource source) {
		List<Text> list = new ArrayList<Text>();
		for(EReplacesServer value : EReplacesServer.values()) {
			if (value.getFunction().isPresent()) {
				list.add(EFormatString.of("&c" + value.name() + " : &r" + value.getName())
					.toText(this.plugin.getChat().getReplaceServer()));
			}
		}
		this.plugin.getManagerService().getEPagination().sendTo(Text.of("Replace Server"), list, source);
		return false;
	}
	
	public boolean replace(final EPlayer player) {
		List<Text> list = new ArrayList<Text>();
		
		for(EReplacesPlayer value : EReplacesPlayer.values()) {
			if (value.getBiFunction().isPresent()) {
				list.add(EFormatString.of("&c" + value.name() + " : &r" + value.getName())
					.toText(player.getReplaces()));
			}
		}
		for(EReplacesServer value : EReplacesServer.values()) {
			if (value.getFunction().isPresent()) {
				list.add(EFormatString.of("&c" + value.name() + " : &r" + value.getName())
					.toText(player.getReplaces()));
			}
		}
		
		list.add(EFormatString.of("&cOPTION=prefix : &r<OPTION=prefix>")
				.toText(player.getReplaces()));
		list.add(EFormatString.of("&cOPTION=suffix : &r<OPTION=suffix>")
				.toText(player.getReplaces()));
		list.add(EFormatString.of("&cOPTION_VALUE=prefix : &r<OPTION_VALUE=prefix>")
				.toText(player.getReplaces()));
		list.add(EFormatString.of("&cOPTION_VALUE=suffix : &r<OPTION_VALUE=suffix>")
				.toText(player.getReplaces()));
		this.plugin.getManagerService().getEPagination().sendTo(Text.of("Replace Player"), list, player);
		return false;
	}
}
