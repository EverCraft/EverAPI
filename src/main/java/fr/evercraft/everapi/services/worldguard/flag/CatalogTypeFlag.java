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
package fr.evercraft.everapi.services.worldguard.flag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.services.worldguard.flag.value.EntryFlagValue;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public abstract class CatalogTypeFlag<T extends CatalogType> extends EFlag<EntryFlagValue<T>> {
	
	protected static final String PATTERN_SPLIT = "[,\\s]+";
	protected static final String ALL = "ALL";
	
	protected final Map<String, Set<T>> groups;
	protected EntryFlagValue<T> defaults;

	public CatalogTypeFlag(String name) {
		super(name);
		
		this.groups = new ConcurrentHashMap<String, Set<T>>();
		this.defaults = new EntryFlagValue<T>();
	}
	
	public void reload() {
		this.groups.clear();
		this.groups.putAll(this.getConfig());
		
		Set<String> keys = this.groups.keySet();
		Set<T> values = new HashSet<T>();
		this.groups.values().forEach(value -> values.addAll(value));
		this.defaults = new EntryFlagValue<T>(keys, values);
	}
	
	protected abstract Map<String, Set<T>> getConfig();
	
	@Override
	public EntryFlagValue<T> getDefault() {
		return this.defaults;
	}
	
	/*
	 * Parse
	 */
	
	@Override
	public EntryFlagValue<T> parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		EntryFlagValue<T> newFlag = null;
		if (args.isEmpty()) {
			newFlag = this.deserialize("");
		} else {
			newFlag = this.deserialize(String.join(", ", args));
		}
		
		Optional<EntryFlagValue<T>> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			return newFlag.addAll(flag.get());
		} else {
			return newFlag;
		}
	}
	
	@Override
	public Optional<EntryFlagValue<T>> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		if (args.isEmpty()) return Optional.empty();
		
		EntryFlagValue<T> newFlag = this.deserialize(String.join(", ", args));
		if (newFlag.getKeys().isEmpty()) return Optional.of(newFlag);
		
		Optional<EntryFlagValue<T>> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			return Optional.of(flag.get().removeAll(newFlag));
		} else {
			return Optional.of(this.getDefault().removeAll(newFlag));
		}
	}
	
	/*
	 * Serialize
	 */
	
	@Override
	public String serialize(EntryFlagValue<T> value) {
		return String.join(",", value.getKeys());
	}

	@Override
	public EntryFlagValue<T> deserialize(String value) throws IllegalArgumentException {
		if (value.equalsIgnoreCase(ALL)) return this.defaults;
		if (value.isEmpty()) return new EntryFlagValue<T>();
		
		Set<String> keys = new HashSet<String>();
		Set<T> values = new HashSet<T>();
		for (String key : value.split(PATTERN_SPLIT)) {
			Set<T> blocks = this.groups.get(key.toUpperCase());
			if (blocks != null) {
				keys.add(key.toUpperCase());
				values.addAll(blocks);
			} else {
				throw new IllegalArgumentException(this.getName()  + " : " + key.toUpperCase());
			}
		}
		return new EntryFlagValue<T>(keys, values);
	}
	
	/*
	 * Suggest
	 */
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, List<String> args) {
		return Stream.concat(
				this.groups.keySet().stream(),
				Stream.of(ALL))
			.filter(suggest -> !args.stream().anyMatch(arg -> arg.equalsIgnoreCase(suggest)))
			.collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> getSuggestRemove(CommandSource source, List<String> args) {
		return this.getSuggestAdd(source, args);
	}
	
	/*
	 * Accesseur 
	 */
	
	@Override
	public Text getValueFormat(EntryFlagValue<T> value) {
		if (value.getKeys().isEmpty()) {
			return EAMessages.FLAG_MAP_EMPTY.getText();
		}
		
		List<Text> groups = new ArrayList<Text>();
		for (String group : value.getKeys()) {
			List<Text> types = new ArrayList<Text>();
			for (T type : this.groups.get(group)) {
				types.add(EAMessages.FLAG_MAP_HOVER.getFormat().toText("<value>", type.getName()));
			}
			if (types.size() > 100) {
				types = types.subList(0, 50);
				types.add(EAMessages.FLAG_MAP_MORE.getText());
			}
			
			groups.add(EAMessages.FLAG_MAP_GROUP.getFormat().toText("<group>", group).toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), types)))
				.build());
		}
		
		return Text.joinWith(EAMessages.FLAG_MAP_JOIN.getText(), groups);
	}
}