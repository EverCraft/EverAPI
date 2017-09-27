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
package fr.evercraft.everapi.plugin.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.selector.Selector;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.exception.message.BooleanNotFoundException;
import fr.evercraft.everapi.exception.message.EMessageException;
import fr.evercraft.everapi.exception.message.PlayerNotFoundException;
import fr.evercraft.everapi.exception.message.SelectorIllegalException;
import fr.evercraft.everapi.exception.message.SelectorNotFoundException;
import fr.evercraft.everapi.exception.message.WorldNotFoundException;
import fr.evercraft.everapi.java.UtilsBoolean;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;

public class Args {
	
	@FunctionalInterface
	public interface ArgBiFunction<A,B,R> {

	    R apply(A a, B b) throws EMessageException;
	}
	
	@FunctionalInterface
	public interface ArgTriFunction<A,B,C,R> {

	    R apply(A a, B b, C c) throws EMessageException;

	    default <V> ArgTriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
	        Objects.requireNonNull(after);
	        return (A a, B b, C c) -> after.apply(apply(a, b, c));
	    }
	}
	
	public static final ArgTriFunction<EPlugin<?>, CommandSource, String, List<EPlayer>> PLAYERS = (plugin, source, value) -> {
		if (!value.startsWith("@") || !source.hasPermission(ECommand.PERMISSION_SELECTOR)) {
			Optional<EPlayer> player = plugin.getEServer().getEPlayer(value);
			if (!player.isPresent()) new PlayerNotFoundException(source, value);
				
			return Arrays.asList(player.get());
		}
		
		try {
			List<EPlayer> players =  Selector.parse(value).resolve(source).stream()
				.filter(entity -> entity instanceof Player)
				.map(entity -> plugin.getEServer().getEPlayer((Player) entity))
				.collect(Collectors.toList());
			if (players.isEmpty()) throw new SelectorNotFoundException(source, value);
			return players;
		} catch (IllegalArgumentException e) {
			throw new SelectorIllegalException(source, value);
		}
	};
	
	public static final ArgTriFunction<EPlugin<?>, CommandSource, String, List<EUser>> USERS = (plugin, source, value) -> {
		if (!value.startsWith("@") || !source.hasPermission(ECommand.PERMISSION_SELECTOR)) {
			Optional<EUser> player = plugin.getEServer().getEUser(value);
			if (!player.isPresent()) new PlayerNotFoundException(source, value);
			
			return Arrays.asList(player.get());
		}
		
		try {
			List<EUser> players =  Selector.parse(value).resolve(source).stream()
				.filter(entity -> entity instanceof Player)
				.map(entity -> plugin.getEServer().getEUser((Player) entity))
				.collect(Collectors.toList());
			if (players.isEmpty()) throw new SelectorNotFoundException(source, value);
			return players;
		} catch (IllegalArgumentException e) {
			throw new SelectorIllegalException(source, value);
		}
	};
	
	public static final ArgTriFunction<EPlugin<?>, CommandSource, String, List<Entity>> ENTITIES = (plugin, source, value) -> {
		if (!value.startsWith("@") || !source.hasPermission(ECommand.PERMISSION_SELECTOR)) {
			Optional<EPlayer> player = plugin.getEServer().getEPlayer(value);
			if (!player.isPresent()) new PlayerNotFoundException(source, value);
			
			return Arrays.asList(player.get());
		}
		
		try {
			List<Entity> entities =  Selector.parse(value).resolve(source);
			if (entities.isEmpty()) throw new SelectorNotFoundException(source, value);
			return entities;
		} catch (IllegalArgumentException e) {
			throw new SelectorIllegalException(source, value);
		}
	};
	
	public static final ArgTriFunction<EPlugin<?>, CommandSource, String, World> WORLD = (plugin, source, value) -> {
		Optional<World> world = plugin.getEServer().getEWorld(value);
		if (!world.isPresent()) throw new WorldNotFoundException(source, value);
		
		return world.get();
	};
	
	public static final ArgTriFunction<EPlugin<?>, CommandSource, String, Boolean> BOOLEAN = (plugin, source, value) -> {
		Optional<Boolean> bool = UtilsBoolean.parseBoolean(value);
		if (!bool.isPresent()) throw new BooleanNotFoundException(source, value);
		
		return bool.get();
	};
	
	public static final String MARKER_CONFIRMATION = "-confirmation";
	public static final String MARKER_WORLD = "-w";
	public static final String MARKER_PLAYER = "-p";
	public static final String MARKER_GROUP = "-g";
	public static final String MARKER_FLAG = "-f";
	
	private EPlugin<?> plugin;
	private CommandSource source;
	
	private final List<String> args;
	private final List<String> options;
	private final Map<String, String> values;
	private final Map<String, List<String>> lists;
	private final Optional<String> lastMarker;
	private final boolean markerOpen;
	
	public Args(EPlugin<?> plugin, CommandSource source, List<String> args, List<String> options, 
			Map<String, String> values, Map<String, List<String>> lists, String lastMarker, boolean markerOpen) {
		this.plugin = plugin;
		this.source = source;
		this.args = args;
		this.options = options;
		this.values = values;
		this.lists = lists;
		this.lastMarker = Optional.ofNullable(lastMarker);
		this.markerOpen = markerOpen;
	}
	
	public Optional<String> getLastMarker() {
		return this.lastMarker;
	}

	public List<String> getArgs() {
		return this.args;
	}
	
	public boolean isMarkerOpen() {
		return this.markerOpen;
	}
	
	public int countValues() {
		return this.values.size();
	}
	
	public int countOptions() {
		return this.options.size();
	}
	
	public boolean isOption(String marker) {
		return this.options.contains(marker);
	}
	
	public World getWorld() throws EMessageException {
		Optional<String> worldMarker = this.getValue(MARKER_WORLD);
		if (worldMarker.isPresent()) {
			Optional<World> world = plugin.getEServer().getEWorld(worldMarker.get());
			if (!world.isPresent()) throw new WorldNotFoundException(this.source, worldMarker.get());
			return world.get();
		}
		
		if (this.source instanceof Locatable) {
			return ((Locatable) source).getWorld();
		} else {
			return this.plugin.getEServer().getDefaultEWorld();
		}
	}
	
	public Optional<List<String>> getList(String marker) {
		return Optional.ofNullable(this.lists.get(marker));
	}
	
	public Optional<String> getArg(int index) {
		if (index > this.args.size()-1) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.args.get(index));
	}
	
	public <T> Optional<T> getArg(int index, ArgTriFunction<EPlugin<?>, CommandSource, String, T> type) throws EMessageException {
		if (index > this.args.size()-1) {
			return Optional.empty();
		}
		return Optional.of(type.apply(this.plugin, this.source, this.args.get(index)));
	}
	
	public List<String> getArgs(int index) {
		List<String> values = new ArrayList<String>();
		int cpt = 0;
		for (String arg : this.args) {
			if (cpt >= index) {
				values.add(arg);
			}
			cpt++;
		}
		return values;
	}
	
	public <T> List<T> getArgs(int index, ArgTriFunction<EPlugin<?>, CommandSource, String, T> type) throws EMessageException {
		List<String> values = new ArrayList<String>();
		int cpt = 0;
		for (String arg : this.args) {
			if (cpt >= index) {
				values.add(arg);
			}
			cpt++;
		}
		
		List<T> result = new ArrayList<T>();
		for (String value : values) {
			result.add(type.apply(this.plugin, this.source, this.values.get(value)));
		}
		return result;
	}
	
	public <V, T extends Collection<V>> List<V> getArgsUnion(int index, ArgTriFunction<EPlugin<?>, CommandSource, String, T> type) throws EMessageException {
		List<String> values = new ArrayList<String>();
		int cpt = 0;
		for (String arg : this.args) {
			if (cpt >= index) {
				values.add(arg);
			}
			cpt++;
		}
		
		List<V> result = new ArrayList<V>();
		for (String value : values) {
			result.addAll(type.apply(this.plugin, this.source, this.values.get(value)));
		}
		return result;
	}
	
	public <T> Optional<List<T>> getList(String marker, ArgTriFunction<EPlugin<?>, CommandSource, String, T> type) throws EMessageException {
		List<String> values = this.lists.get(marker);
		if (values == null) return Optional.empty();
		
		List<T> result = new ArrayList<T>();
		for (String value : values) {
			result.add(type.apply(this.plugin, this.source, this.values.get(value)));
		}
		return Optional.of(result);
	}
	
	public <V, T extends Collection<V>> Optional<List<V>> getListUnion(String marker, ArgTriFunction<EPlugin<?>, CommandSource, String, T> type) throws EMessageException {
		List<String> values = this.lists.get(marker);
		if (values == null) return Optional.empty();
		
		List<V> result = new ArrayList<V>();
		for (String value : values) {
			result.addAll(type.apply(this.plugin, this.source, this.values.get(value)));
		}
		return Optional.of(result);
	}
	
	public Optional<String> getValue(String marker) {
		return Optional.ofNullable(this.values.get(marker));
	}
	
	public <T> Optional<T> getValue(String marker, ArgTriFunction<EPlugin<?>, CommandSource, String, T> type) throws EMessageException {
		String value = this.values.get(marker);
		if (value == null) return Optional.empty();
		
		return Optional.of(type.apply(this.plugin, this.source, this.values.get(value)));
	}
	
	public static BuilderArgs builder() {
		return new BuilderArgs();
	}
	
	public interface Builder {
		Builder arg(ArgBiFunction<CommandSource, Args, Collection<String>> suggests);
		Builder args(ArgBiFunction<CommandSource, Args, Collection<String>> suggests);
		
		Args build(EPlugin<?> plugin, CommandSource source, List<String> command);
		Collection<String> suggest(EPlugin<?> plugin, CommandSource source, List<String> args);
		
		BuilderArgs empty(String marker);
		BuilderArgs empty(String marker, ArgBiFunction<CommandSource, Args, Boolean> check);
		Builder value(String marker, ArgBiFunction<CommandSource, Args, Collection<String>> suggests);
		BuilderArgs value(String marker, 
				ArgBiFunction<CommandSource, Args, Collection<String>> suggests,
				ArgBiFunction<CommandSource, Args, Boolean> check);
		Builder list(String marker, ArgBiFunction<CommandSource, Args, Collection<String>> suggests);
		BuilderArgs list(String marker, 
				ArgBiFunction<CommandSource, Args, Collection<String>> suggests,
				ArgBiFunction<CommandSource, Args, Boolean> check);
	}

	@Override
	public String toString() {
		return "Args ["
				+ "args=" + String.join("','", this.args) + ", "
				+ "options=" + String.join("','", this.options) + ", "
				+ "values=" + values + ", lists=" + lists + ", "
				+ "lastMarker=" + lastMarker + ", "
				+ "markerOpen=" + markerOpen + "]";
	}
}
