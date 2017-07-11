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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.spongepowered.api.command.CommandSource;

public class BuilderArgs implements Args.Builder {
	
	private enum Type {
		EMPTY,
		VALUE,
		LIST;
	}
	
	public class Value {
		private final Type type;
		private final BiFunction<CommandSource, Args, Boolean> check;
		
		public Value(Type type, BiFunction<CommandSource, Args, Boolean> check) {
			this.type = type;
			this.check = check;
		}

		public Type getType() {
			return type;
		}

		public BiFunction<CommandSource, Args, Boolean> getCheck() {
			return check;
		}
	}
	
	private final Map<String, Value> types;
	private final List<BiFunction<CommandSource, Args, Collection<String>>> suggests_args;
	private final Map<String, BiFunction<CommandSource, Args, Collection<String>>> suggests_types;
	
	private boolean arg_list;
	
	public BuilderArgs() {
		this.types = new HashMap<String, Value>();
		this.suggests_args = new ArrayList<BiFunction<CommandSource, Args, Collection<String>>>();
		this.suggests_types = new HashMap<String, BiFunction<CommandSource, Args, Collection<String>>>();
		
		this.arg_list = false;
	}
	
	@Override
	public BuilderArgs empty(String marker) {
		return this.empty(marker, (source, args) -> true);
	}
	
	@Override
	public BuilderArgs empty(String marker, BiFunction<CommandSource, Args, Boolean> check) {
		this.types.put(marker, new Value(Type.EMPTY, check));
		return this;
	}
	
	@Override
	public BuilderArgs value(String marker, BiFunction<CommandSource, Args, Collection<String>> suggests) {
		return this.value(marker, suggests, (source, args) -> true);
	}
	
	@Override
	public BuilderArgs value(String marker, 
			BiFunction<CommandSource, Args, Collection<String>> suggests,
			BiFunction<CommandSource, Args, Boolean> check) {
		this.types.put(marker, new Value(Type.VALUE, check));
		this.suggests_types.put(marker, suggests);
		return this;
	}
	
	@Override
	public BuilderArgs list(String marker, BiFunction<CommandSource, Args, Collection<String>> suggests) {
		return this.list(marker, suggests, (source, args) -> true);
	}
	
	@Override
	public BuilderArgs list(String marker, 
			BiFunction<CommandSource, Args, Collection<String>> suggests, 
			BiFunction<CommandSource, Args, Boolean> check) {
		this.types.put(marker, new Value(Type.LIST, check));
		this.suggests_types.put(marker, suggests);
		return this;
	}
	
	@Override
	public BuilderArgs arg(BiFunction<CommandSource, Args, Collection<String>> suggests) {
		this.suggests_args.add(suggests);
		this.arg_list = false;
		return this;
	}
	
	@Override
	public BuilderArgs args(BiFunction<CommandSource, Args, Collection<String>> suggests) {
		this.suggests_args.add(suggests);
		this.arg_list = true;
		return this;
	}
	
	@Override
	public Args build(List<String> command) {
		return this.build(command, false);
	}
	
	private Args build(List<String> command, boolean suggest) {
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
					
					if (lastType.equals(Type.LIST) && lists.get(lastType) == null) {
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
		
		return new Args(args, options, values, lists, lastMarker, markerOpen);
	}
	
	@Override
	public Collection<String> suggest(final CommandSource source, List<String> command) {
		Args args = this.build(command, true);
		List<String> suggests = new ArrayList<String>();
		if (args.getLastMarker().isPresent()) {
			BiFunction<CommandSource, Args, Collection<String>> function = this.suggests_types.get(args.getLastMarker().get());
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
			this.types.forEach((marker, value) -> {
				if (!command.contains(marker) && value.getCheck().apply(source, args)) {
					if (value.getType().equals(Type.LIST)) {
						if (args.getArgs().size() > this.suggests_args.size()) {
							suggests.add(marker);
						}
					} else {
						suggests.add(marker);
					}
				}
			});
		}
		
		return suggests;
	}
}
