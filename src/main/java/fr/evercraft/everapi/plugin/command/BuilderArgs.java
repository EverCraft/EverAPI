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
	
	private final Map<String, Type> types;
	private final List<BiFunction<CommandSource, Args, Collection<String>>> suggests_args;
	private final Map<String, BiFunction<CommandSource, Args, Collection<String>>> suggests_types;
	
	private boolean arg_list;
	
	public BuilderArgs() {
		this.types = new HashMap<String, Type>();
		this.suggests_args = new ArrayList<BiFunction<CommandSource, Args, Collection<String>>>();
		this.suggests_types = new HashMap<String, BiFunction<CommandSource, Args, Collection<String>>>();
		
		this.arg_list = false;
	}
	
	@Override
	public BuilderArgs empty(String marker) {
		this.types.put(marker, Type.EMPTY);
		return this;
	}
	
	@Override
	public BuilderArgs value(String marker, BiFunction<CommandSource, Args, Collection<String>> suggests) {
		this.types.put(marker, Type.VALUE);
		this.suggests_types.put(marker, suggests);
		return this;
	}
	
	@Override
	public BuilderArgs list(String marker, BiFunction<CommandSource, Args, Collection<String>> suggests) {
		this.types.put(marker, Type.LIST);
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
		List<String> args = new ArrayList<String>();
		List<String> options = new ArrayList<String>();
		Map<String, String> values = new HashMap<String, String>();
		Map<String, List<String>> lists = new HashMap<String, List<String>>();
		
		String lastMarker = null;
		Type lastType = null;
		int cpt = 0;
		
		for (String arg : command) {
			cpt++;
			
			Type type = this.types.get(arg);
			if (type != null) {
				if (type.equals(Type.EMPTY)) {
					options.add(arg);
				} else {
					if (lastMarker != null && lastType.equals(Type.VALUE)) {
						values.put(lastMarker, "");
					}
					
					lastMarker = arg;
					lastType = type;
					
					if (type.equals(Type.LIST) && lists.get(type) == null) {
						lists.put(lastMarker, new ArrayList<String>());
					}
				}
			} else {
				if (lastMarker != null) {
					if (lastType.equals(Type.VALUE)) {
						if (!arg.isEmpty() || cpt < command.size()) {
							values.put(lastMarker, arg);
							lastMarker = null;
							lastType = null;
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
		Args args = this.build(command);
		List<String> suggests = new ArrayList<String>();
		if (args.getLastMarker().isPresent()) {
			BiFunction<CommandSource, Args, Collection<String>> function = this.suggests_types.get(args.getLastMarker().get());
			if (function != null) {
				return function.apply(source, args);
			}
		} else if (!this.suggests_args.isEmpty()) {
			if (args.getArgs().size() <= this.suggests_args.size()) {
				suggests.addAll(this.suggests_args.get(args.getArgs().size()-1).apply(source, args));
			} else if (this.arg_list) {
				suggests.addAll(this.suggests_args.get(this.suggests_args.size()-1).apply(source, args));
			}
		}
		
		if (!args.isMarkerOpen()) {
			this.types.forEach((marker, type) -> {
				if (!command.contains(marker)) {
					if (type.equals(Type.VALUE)) {
						suggests.add(marker);
					} else if (type.equals(Type.LIST)) {
						if (args.getArgs().size() > this.suggests_args.size()) {
							suggests.add(marker);
						}
					}
				}
			});
		}
		
		return suggests;
	}
}
