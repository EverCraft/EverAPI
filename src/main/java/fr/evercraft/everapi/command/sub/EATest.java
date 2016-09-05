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
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.server.player.EPlayer;

public class EATest extends ESubCommand<EverAPI> {
	public EATest(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "test");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.TEST.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("");
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
		if (args.size() == 0) {
			return commandTest((EPlayer) source);
		} else if (args.size() == 1) {
			return commandTest((EPlayer) source, args.get(0));
		}
		source.sendMessage(this.help(source));
		return false;
	}
	
	private boolean commandTest(final EPlayer player) {
		player.sendMessage("ItemType : " + player.getItemInMainHand().get());
		ItemStack itemstak = player.getItemInMainHand().get();
		itemstak.remove(Keys.SPAWNABLE_ENTITY_TYPE);
		player.setItemInMainHand(itemstak);
		player.sendMessage("update");
		return false;
	}

	private boolean commandTest(final EPlayer player, String name) {
		player.sendMessage("AREA_EFFECT_CLOUD : " + name);
		Entity entity = player.getWorld().createEntity(EntityTypes.AREA_EFFECT_CLOUD, player.getLocation().getBlockPosition().add(0, 2, 0));
		entity.offer(Keys.DISPLAY_NAME, EChat.of(name));
		entity.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		player.getWorld().spawnEntity(entity, Cause.source(EntitySpawnCause.builder().entity(entity).type(SpawnTypes.PLUGIN).build()).build());
		return true;
	}
}
