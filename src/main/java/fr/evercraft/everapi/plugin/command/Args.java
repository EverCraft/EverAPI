package fr.evercraft.everapi.plugin.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import org.spongepowered.api.command.CommandSource;

public class Args {
	
	private final List<String> args;
	private final List<String> options;
	private final Map<String, String> values;
	private final Map<String, List<String>> lists;
	private final Optional<String> lastMarker;
	private final boolean markerOpen;
	
	public Args(List<String> args, List<String> options, Map<String, String> values, Map<String, List<String>> lists, 
			String lastMarker, boolean markerOpen) {
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
	
	public Optional<String> getArg(int index) {
		if (index > this.args.size()-1) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.args.get(index));
	}
	
	public List<String> getArgs(int index) {
		List<String> list = new ArrayList<String>();
		int cpt = 0;
		for (String arg : args) {
			if (cpt >= index) {
				list.add(arg);
			}
			cpt++;
		}
		return list;
	}
	
	public boolean isMarkerOpen() {
		return this.markerOpen;
	}
	
	public boolean isOption(String marker) {
		return this.options.contains(marker);
	}
	
	public Optional<List<String>> getList(String marker) {
		return Optional.ofNullable(this.lists.get(marker));
	}
	
	public Optional<String> getValue(String marker) {
		return Optional.ofNullable(this.values.get(marker));
	}
	
	public static BuilderArgs builder() {
		return new BuilderArgs();
	}
	
	public interface Builder {
		BuilderArgs empty(String marker);
		Builder value(String marker, BiFunction<CommandSource, Args, Collection<String>> suggests);
		Builder list(String marker, BiFunction<CommandSource, Args, Collection<String>> suggests);
		Builder arg(BiFunction<CommandSource, Args, Collection<String>> suggests);
		Builder args(BiFunction<CommandSource, Args, Collection<String>> suggests);
		Args build(List<String> command);
		Collection<String> suggest(final CommandSource source, List<String> args);
	}
}
