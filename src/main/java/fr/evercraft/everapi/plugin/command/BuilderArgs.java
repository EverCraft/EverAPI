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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.exception.message.EMessageException;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.plugin.command.Args.ArgBiFunction;

public class BuilderArgs implements Args.Builder {
	
	private enum Type {
		EMPTY,
		VALUE,
		LIST;
	}
	
	public class Value {
		private final Type type;
		private final ArgBiFunction<CommandSource, Args, Boolean> check;
		
		public Value(Type type, ArgBiFunction<CommandSource, Args, Boolean> check) {
			this.type = type;
			this.check = check;
		}

		public Type getType() {
			return type;
		}

		public ArgBiFunction<CommandSource, Args, Boolean> getCheck() {
			return check;
		}
	}
	
	private final Map<String, Value> types;
	private final List<ArgBiFunction<CommandSource, Args, Collection<String>>> suggests_args;
	private final Map<String, ArgBiFunction<CommandSource, Args, Collection<String>>> suggests_types;
	
	private boolean arg_list;
	
	public BuilderArgs() {
		this.types = new HashMap<String, Value>();
		this.suggests_args = new ArrayList<ArgBiFunction<CommandSource, Args, Collection<String>>>();
		this.suggests_types = new HashMap<String, ArgBiFunction<CommandSource, Args, Collection<String>>>();
		
		this.arg_list = false;
	}
	
	@Override
	public BuilderArgs empty(String marker) {
		return this.empty(marker, (source, args) -> true);
	}
	
	@Override
	public BuilderArgs empty(String marker, ArgBiFunction<CommandSource, Args, Boolean> check) {
		this.types.put(marker, new Value(Type.EMPTY, check));
		return this;
	}
	
	@Override
	public BuilderArgs value(String marker, ArgBiFunction<CommandSource, Args, Collection<String>> suggests) {
		return this.value(marker, suggests, (source, args) -> true);
	}
	
	@Override
	public BuilderArgs value(String marker, 
			ArgBiFunction<CommandSource, Args, Collection<String>> suggests,
			ArgBiFunction<CommandSource, Args, Boolean> check) {
		this.types.put(marker, new Value(Type.VALUE, check));
		this.suggests_types.put(marker, suggests);
		return this;
	}
	
	@Override
	public BuilderArgs list(String marker, ArgBiFunction<CommandSource, Args, Collection<String>> suggests) {
		return this.list(marker, suggests, (source, args) -> true);
	}
	
	@Override
	public BuilderArgs list(String marker, 
			ArgBiFunction<CommandSource, Args, Collection<String>> suggests, 
			ArgBiFunction<CommandSource, Args, Boolean> check) {
		this.types.put(marker, new Value(Type.LIST, check));
		this.suggests_types.put(marker, suggests);
		return this;
	}
	
	@Override
	public BuilderArgs arg(ArgBiFunction<CommandSource, Args, Collection<String>> suggests) {
		this.suggests_args.add(suggests);
		this.arg_list = false;
		return this;
	}
	
	@Override
	public BuilderArgs args(ArgBiFunction<CommandSource, Args, Collection<String>> suggests) {
		this.suggests_args.add(suggests);
		this.arg_list = true;
		return this;
	}
	
	@Override
	public Args build(final EPlugin<?> plugin, final CommandSource source, List<String> command) {
		return this.build(plugin, source, command, false);
	}
	
	private Args build(final EPlugin<?> plugin, final CommandSource source, List<String> command, boolean suggest) {
		List<String> args = new ArrayList<String>();
		List<String> options = new ArrayList<String>();
		Map<String, String> values = new HashMap<String, String>();
		Map<String, List<String>> lists = new HashMap<String, List<String>>();
		
		String lastMarker = null;
		Type lastType = null;
		int cpt = 0;
		
		for (String arg : command) {
			cpt++;
			
			Value value = this.types.get(arg);
			if (value != null) {
				if (value.getType().equals(Type.EMPTY)) {
					options.add(arg);
				} else {
					if (lastMarker != null && lastType.equals(Type.VALUE)) {
						values.put(lastMarker, "");
					}
					
					lastMarker = arg;
					lastType = value.getType();
					
					if (lastType.equals(Type.LIST) && lists.get(lastMarker) == null) {
						lists.put(lastMarker, new ArrayList<String>());
					}
				}
			} else {
				if (lastMarker != null) {
					if (lastType.equals(Type.VALUE)) {
						if (!suggest || cpt < command.size()) {
							values.put(lastMarker, arg);
							lastMarker = null;
							lastType = null;
						} else {
							values.put(lastMarker, arg);
						}
					} else if (lastType.equals(Type.LIST)) {
						lists.get(lastMarker).add(arg);
					}
				} else {
					args.add(arg);
				}
			}
		}
		
		boolean markerOpen = false;
		if (lastType != null) {
			if (lastType.equals(Type.VALUE)) {
				markerOpen = true;
			} else if (lastType.equals(Type.LIST) && lists.get(lastMarker).isEmpty()) {
				markerOpen = true;
			}
		}
		
		return new Args(plugin, source, args, options, values, lists, lastMarker, markerOpen);
	}
	
	@Override
	public Collection<String> suggest(final EPlugin<?> plugin, final CommandSource source, final List<String> command) {
		try {
			Args args = this.build(plugin, source, command, true);
			List<String> suggests = new ArrayList<String>();
			if (args.getLastMarker().isPresent()) {
				ArgBiFunction<CommandSource, Args, Collection<String>> function = this.suggests_types.get(args.getLastMarker().get());
				if (function != null) {
					return function.apply(source, args);
				}
			} else if (!this.suggests_args.isEmpty() && ! args.getArgs().isEmpty()) {
				if (args.getArgs().size() <= this.suggests_args.size()) {
					suggests.addAll(this.suggests_args.get(args.getArgs().size()-1).apply(source, args));
				} else if (this.arg_list) {
					suggests.addAll(this.suggests_args.get(this.suggests_args.size()-1).apply(source, args));
				}
			}
			
			if (!args.isMarkerOpen()) {
				for (Entry<String, Value> type : this.types.entrySet()) {
					if (!command.contains(type.getKey()) && type.getValue().getCheck().apply(source, args)) {
						if (type.getValue().getType().equals(Type.LIST)) {
							if (args.getArgs().size() > this.suggests_args.size()) {
								suggests.add(type.getKey());
							}
						} else {
							suggests.add(type.getKey());
						}
					}
				}
			}
			return suggests;
		} catch (EMessageException e) {
			return Arrays.asList();
		}
	}
}
