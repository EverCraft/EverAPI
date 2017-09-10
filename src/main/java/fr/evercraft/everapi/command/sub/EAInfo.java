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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.Tristate;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.sponge.UtilsCause;

public class EAInfo extends ESubCommand<EverAPI> {
	
	private final Set<UUID> players;
	
	public EAInfo(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "info");
        
        this.players = new HashSet<UUID>();
        this.plugin.getGame().getEventManager().registerListeners(plugin, this);
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.BLOCK_INFO.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("Info");
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
		if (!(source instanceof EPlayer)) return CompletableFuture.completedFuture(false);
		
		EPlayer player = (EPlayer) source;
		
		if (this.players.contains(player.getUniqueId())) {
			this.players.remove(player.getUniqueId());
			player.sendMessage("[Info] &cDisable");
		} else {
			this.players.add(player.getUniqueId());
			player.sendMessage("[Info] &aEnable");
		}
		return CompletableFuture.completedFuture(true);
	}
	
	@Listener(order=Order.FIRST)
	@IsCancelled(Tristate.UNDEFINED)
	public void onChangeBlock(ChangeBlockEvent.Place event) {
		UtilsCause.debug(event.getCause(), "ChangeBlockEvent.Place : " + String.join(", ", event.getTransactions().stream()
				.map(t -> "(" + t.getOriginal().getState().getType().getId() + " : " + t.getFinal().getState().getType().getId() + ")").collect(Collectors.toList())));
		
		Optional<User> optUser = event.getContext().get(EventContextKeys.OWNER);
		if (!optUser.isPresent()) return;
		
		Optional<Player> optPlayer = optUser.get().getPlayer();
		if (!optPlayer.isPresent()) return;
		
		Player player = optPlayer.get();
		if (!this.players.contains(player.getUniqueId())) return;
		
		event.setCancelled(true);
		
		event.getTransactions().forEach(transaction -> {
			player.sendMessage(Text.of(TextColors.GRAY, TextStyles.BOLD, "============================================"));
			player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Block Type: ", TextColors.BLUE, TextStyles.RESET, transaction.getOriginal().getState().getId()));
			player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Block Owner: ", TextColors.BLUE, TextStyles.RESET, transaction.getOriginal().getCreator()));
			player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Block Notifier: ", TextColors.BLUE, TextStyles.RESET, transaction.getOriginal().getNotifier()));
		});
	}
	
	@Listener(order=Order.FIRST)
	@IsCancelled(Tristate.UNDEFINED)
	public void onInteractBlock(InteractBlockEvent.Primary event) {
		Optional<User> optUser = event.getContext().get(EventContextKeys.OWNER);
		if (!optUser.isPresent()) return;
		
		Optional<Player> optPlayer = optUser.get().getPlayer();
		if (!optPlayer.isPresent()) return;
		
		Player player = optPlayer.get();
		if (!this.players.contains(player.getUniqueId())) return;
		
		event.setCancelled(true);
		
		player.sendMessage(Text.of(TextColors.GRAY, TextStyles.BOLD, "============================================"));
		player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Block Type: ", TextColors.BLUE, TextStyles.RESET, event.getTargetBlock().getState().getId()));
		player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Block Owner: ", TextColors.BLUE, TextStyles.RESET, event.getTargetBlock().getCreator()));
		player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Block Notifier: ", TextColors.BLUE, TextStyles.RESET, event.getTargetBlock().getNotifier()));
	}
	
	@Listener(order=Order.FIRST)
	@IsCancelled(Tristate.UNDEFINED)
	public void onInteractEntity(InteractEntityEvent.Primary event) {
		Optional<User> optUser = event.getContext().get(EventContextKeys.OWNER);
		if (!optUser.isPresent()) return;
		
		Optional<Player> optPlayer = optUser.get().getPlayer();
		if (!optPlayer.isPresent()) return;
		
		Player player = optPlayer.get();
		if (!this.players.contains(player.getUniqueId())) return;
		if (!optPlayer.isPresent()) return;
		
		event.setCancelled(true);
		
		player.sendMessage(Text.of(TextColors.GRAY, TextStyles.BOLD, "============================================"));
		player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Entity Type: ", TextColors.BLUE, TextStyles.RESET, event.getTargetEntity().getType().getId()));
		player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Entity Owner: ", TextColors.BLUE, TextStyles.RESET, event.getTargetEntity().getCreator()));
		player.sendMessage(Text.of(TextColors.DARK_GREEN, TextStyles.BOLD, "Entity Notifier: ", TextColors.BLUE, TextStyles.RESET, event.getTargetEntity().getNotifier()));
	}
}
