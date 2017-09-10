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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.action.CollideEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.entity.explosive.DetonateExplosiveEvent;
import org.spongepowered.api.event.entity.explosive.PrimeExplosiveEvent;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.sponge.UtilsCause;

public class EAEvent extends ESubCommand<EverAPI> {
	
	private final ConcurrentMap<String, EListener<?>> events;
	
	public EAEvent(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "event");
        
        this.events = new ConcurrentHashMap<String, EListener<?>>();
        
        this.register();
    }

	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.DEBUG.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("Event");
	}
	
	public Collection<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		if (args.size() == 1) {
			return Arrays.asList("listener", "unlistener");
		} else if (args.get(0).equalsIgnoreCase("listener")) {
			return this.events.values().stream()
					.map(value -> value.getName())
					.collect(Collectors.toSet());
		} else if (args.get(0).equalsIgnoreCase("unlistener")) {
			return this.events.entrySet().stream()
					.filter(value -> value.getValue().getPlayers().contains(source.getIdentifier()))
					.map(value -> value.getValue().getName())
					.collect(Collectors.toSet());
		}
		return Arrays.asList();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName() + " <listener <event...>|unlistener [event...]>")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " "))
					.color(TextColors.RED)
					.build();
	}
	
	public CompletableFuture<Boolean> execute(final CommandSource source, final List<String> args) {
		if (args.get(0).equalsIgnoreCase("listener") && !args.isEmpty()) {
			return this.executeListener(source, args);
		} else if (args.get(0).equalsIgnoreCase("unlistener")) {
			return this.executeUnListener(source, args);
		} else {
			source.sendMessage(this.help(source));
			return CompletableFuture.completedFuture(false);
		}
	}

	private CompletableFuture<Boolean> executeListener(CommandSource source, List<String> args) {
		List <String> listeners = new ArrayList<String>();
		for (String arg : args) {
			EListener<?> event = this.events.get(arg.toLowerCase());
			if (event != null && !event.getPlayers().contains(source.getIdentifier())) {
				listeners.add(event.getName());
				
				this.listener(source, event);
			}
		}
		
		if (listeners.isEmpty()) {
			source.sendMessage(Text.of("Listener : EMPTY"));
		} else {
			source.sendMessage(Text.of("Listener : " + String.join(", ", listeners)));
		}
		return CompletableFuture.completedFuture(true);
	}

	private CompletableFuture<Boolean> executeUnListener(CommandSource source, List<String> args) {
		if (args.isEmpty()) {
			Optional<String> unlisteners = this.events.entrySet().stream()
				.filter(entry -> entry.getValue().getPlayers().contains(source.getIdentifier()))
				.peek(entry -> this.unListener(source, entry.getValue()))
				.map(entry -> entry.getKey())
				.reduce((v1, v2) -> v1 + ", " + v2);
			source.sendMessage(Text.of("UnListener : " + unlisteners.orElse("EMPTY")));
		} else {
			List <String> unlisteners = new ArrayList<String>();
			for (String arg : args) {
				EListener<?> event = this.events.get(arg.toLowerCase());
				if (event != null && event.getPlayers().contains(source.getIdentifier())) {
					unlisteners.add(event.getName());
					
					this.unListener(source, event);
				}
			}
			
			if (unlisteners.isEmpty()) {
				source.sendMessage(Text.of("UnListener : EMPTY"));
			} else {
				source.sendMessage(Text.of("UnListener : " + String.join(", ", unlisteners)));
			}
		}
		return CompletableFuture.completedFuture(true);
	}
	
	private <T extends Event> void listener(CommandSource source, EListener<T> listener) {
		Set<String> players = listener.getPlayers();
		if (!players.isEmpty()) {
			players.add(source.getIdentifier());
			return;
		}
		
		players.add(source.getIdentifier());
		this.plugin.getGame().getEventManager().registerListener(this.plugin, listener.getEvent(), Order.FIRST, listener);
		System.out.println("Register");
	}

	private <T extends Event> void unListener(CommandSource source, EListener<T> listener) {
		Set<String> players = listener.getPlayers();
		if (players.isEmpty()) return;
		
		players.remove(source.getIdentifier());
		if (players.isEmpty()) {
			this.plugin.getGame().getEventManager().unregisterListeners(listener);
		}
	}
	
	private void register() {		
		BiFunction<String, ChangeBlockEvent.Pre, Text> changeBlockPre = (name, event) -> 
			Text.joinWith(Text.of(", "), event.getLocations().stream()
				.map(t -> Text.of(t.getExtent().getName() + " : " + t.getPosition()))
				.collect(Collectors.toList()));
		
			
		register("ChangeBlockEvent.Pre", ChangeBlockEvent.Pre.class, changeBlockPre);
		registerBlock("ChangeBlockEvent.Place", ChangeBlockEvent.Place.class);
		registerBlock("ChangeBlockEvent.Break", ChangeBlockEvent.Break.class);
		registerBlock("ChangeBlockEvent.Modify", ChangeBlockEvent.Modify.class);
		registerBlock("ChangeBlockEvent.Post", ChangeBlockEvent.Post.class);
		registerBlock("ChangeBlockEvent.Decay", ChangeBlockEvent.Decay.class);
		registerBlock("ChangeBlockEvent.Grow", ChangeBlockEvent.Grow.class);
		
		register("InteractBlockEvent.Secondary", InteractBlockEvent.Secondary.class);
		register("InteractBlockEvent.Primary", InteractBlockEvent.Primary.class);
		
		register("InteractEntityEvent.Secondary", InteractEntityEvent.Secondary.class);
		register("InteractEntityEvent.Primary", InteractEntityEvent.Primary.class);
		
		register("DropItemEvent.Pre", DropItemEvent.Pre.class);
		register("DropItemEvent.Destruct", DropItemEvent.Destruct.class);
		register("DropItemEvent.Dispense", DropItemEvent.Dispense.class);
		register("DropItemEvent.Custom", DropItemEvent.Custom.class);
		
		register("DamageEntityEvent", DamageEntityEvent.class);
		register("SpawnEntityEvent", SpawnEntityEvent.class);
		register("SpawnEntityEvent", SpawnEntityEvent.class);
		
		register("ConstructEntityEvent.Pre", ConstructEntityEvent.Pre.class);
		register("ConstructEntityEvent.Post", ConstructEntityEvent.Post.class);
		
		register("CollideBlockEvent", CollideBlockEvent.class);
		register("CollideEntityEvent", CollideEntityEvent.class);
		register("CollideBlockEvent.Impact", CollideBlockEvent.Impact.class);
		register("CollideEntityEvent.Impact", CollideEntityEvent.Impact.class);
		register("CollideEvent.Impact", CollideEvent.Impact.class);
		
		register("ChangeInventoryEvent.Equipment", ChangeInventoryEvent.Equipment.class);
		register("ChangeInventoryEvent.Held", ChangeInventoryEvent.Held.class);
		register("ChangeInventoryEvent.Transfer", ChangeInventoryEvent.Transfer.class);
		register("ChangeInventoryEvent.Pickup", ChangeInventoryEvent.Pickup.class);
		
		register("PrimeExplosiveEvent", PrimeExplosiveEvent.class);
	}
	
	private <T extends Event> void register(String name, Class<T> c) {
		BiFunction<String, T, Text> fun = (name1, event) -> Text.EMPTY;
		this.register(name, c, fun);
	}
	
	private <T extends ChangeBlockEvent> void registerBlock(String name, Class<T> c) {
		BiFunction<String, T, Text> fun = (name1, event) -> 
		Text.joinWith(EChat.of("\n"), event.getTransactions().stream()
				.map(t -> Text.of(t.getOriginal().getState().getType().getId() + " => " + t.getFinal().getState().getType().getId()))
				.collect(Collectors.toList()));
		
		this.register(name, c, fun);
	}
	
	private <T extends Event> void register(String name, Class<T> c, BiFunction<String, T, Text> fun) {
		this.events.putIfAbsent(name.toLowerCase(), new EListener<T>(name, c, (event) ->
			EChat.of("&a" + name + "&f ").toBuilder()
				.onHover(TextActions.showText(EChat.of("&c" + event.getClass().getSimpleName() + " :&f\n").concat(fun.apply(name, event))))
				.build()
		));
	}
	
	public class EListener<T extends Event> implements EventListener<T> {
		
		private final String name;
		private final Class<T> c;
		private final Function<T, Text> fun;
		
		private final Set<String> players;
		
		public EListener(String name, Class<T> c, Function<T, Text> fun) {
			this.name = name;
			this.c = c;
			this.fun = fun;
			
			this.players = new HashSet<String>();
		}
		
		public String getName() {
			return this.name;
		}
		
		public Class<T> getEvent() {
			return this.c;
		}

		public Set<String> getPlayers() {
			return this.players;
		}

		@Override
		public void handle(T event) throws Exception {
			List<Text> list = new ArrayList<Text>();
			event.getCause().getContext().asMap().forEach((key, value) -> {
				list.add(Text.builder(key.getId())
						.onHover(TextActions.showText(Text.of(EChat.fixLength(value.toString(), 254))))
						.onClick(TextActions.suggestCommand(EChat.fixLength(value.toString(), 254)))
						.build());
			});
			Text text = fun.apply(event).concat(Text.of(" : ")).concat(Text.joinWith(Text.of(", "), list));
			
			for (String identifier : this.players) {
				try {
					Optional<Player> player = Sponge.getServer().getPlayer(UUID.fromString(identifier));
					if (player.isPresent()) {
						player.get().sendMessage(text);
						continue;
					} 
				} catch(Exception e) {}
					
				if (Sponge.getServer().getConsole().getIdentifier().equals(identifier)) {
					Sponge.getServer().getConsole().sendMessage(text);
				}
			}
		}
	}
}
