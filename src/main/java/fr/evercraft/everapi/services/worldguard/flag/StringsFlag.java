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

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.services.worldguard.flag.value.EntryFlagValue;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public abstract class StringsFlag extends EFlag<EntryFlagValue<String>> {
	
	protected static final String PATTERN_SPLIT = "[,\\s]+";
	
	protected final Map<String, Set<String>> groups;
	protected EntryFlagValue<String> defaults;

	public StringsFlag(String name) {
		super(name);
		
		this.groups = new ConcurrentHashMap<String, Set<String>>();
		this.defaults = new EntryFlagValue<String>();
	}
	
	public void reload() {
		this.groups.clear();
		this.groups.putAll(this.getConfig());
		
		Set<String> keys = this.groups.keySet();
		Set<String> values = new HashSet<String>();
		this.groups.values().forEach(value -> values.addAll(value));
		this.defaults = new EntryFlagValue<String>(keys, values);
	}
	
	protected abstract Map<String, Set<String>> getConfig();
	
	@Override
	public EntryFlagValue<String> getDefault() {
		return this.defaults;
	}
	
	/*
	 * Parse
	 */
	
	@Override
	public EntryFlagValue<String> parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		EntryFlagValue<String> newFlag = null;
		if (args.isEmpty()) {
			newFlag = this.deserialize("");
		} else {
			newFlag = this.deserialize(String.join(", ", args));
		}
		
		Optional<EntryFlagValue<String>> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			return newFlag.addAll(flag.get());
		} else {
			return newFlag;
		}
	}
	
	@Override
	public Optional<EntryFlagValue<String>> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		if (args.isEmpty()) return Optional.empty();
		
		EntryFlagValue<String> newFlag = this.deserialize(String.join(", ", args));
		if (newFlag.getKeys().isEmpty()) return Optional.empty();
		
		Optional<EntryFlagValue<String>> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			newFlag = flag.get().removeAll(newFlag);
			if (!newFlag.getKeys().isEmpty()) {
				return Optional.of(newFlag);
			}
			return Optional.empty();
		} else {
			return Optional.of(this.getDefault().removeAll(newFlag));
		}
	}
	
	/*
	 * Serialize
	 */
	
	@Override
	public String serialize(EntryFlagValue<String> value) {
		return String.join(",", value.getKeys());
	}

	@Override
	public EntryFlagValue<String> deserialize(String value) throws IllegalArgumentException {
		if (value.isEmpty()) return new EntryFlagValue<String>();
		
		Set<String> keys = new HashSet<String>();
		Set<String> values = new HashSet<String>();
		for (String key : value.split(PATTERN_SPLIT)) {
			Set<String> blocks = this.groups.get(key.toUpperCase());
			if (blocks != null) {
				keys.add(key.toUpperCase());
				values.addAll(blocks);
			} else {
				throw new IllegalArgumentException(this.getName()  + " : " + key.toUpperCase());
			}
		}
		return new EntryFlagValue<String>(keys, values);
	}
	
	/*
	 * Suggest
	 */
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, List<String> args) {
		return this.groups.keySet().stream()
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
	public Text getValueFormat(EntryFlagValue<String> value) {
		Preconditions.checkNotNull(value);
		
		if (value.getKeys().isEmpty()) {
			return EAMessages.FLAG_MAP_EMPTY.getText();
		}
		
		List<Text> groups = new ArrayList<Text>();
		for (String group : value.getKeys()) {
			List<Text> types = new ArrayList<Text>();
			
			if (this.groups.containsKey(group)) {
				for (String type : this.groups.get(group)) {
					types.add(EAMessages.FLAG_MAP_HOVER.getFormat().toText("<value>", type));
				}
			} else {
				for (String type : this.getDefault().getValues()) {
					types.add(EAMessages.FLAG_MAP_HOVER.getFormat().toText("<value>", type));
				}
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