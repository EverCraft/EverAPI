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

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.services.entity.EntityTemplate;
import fr.evercraft.everapi.services.worldguard.flag.value.EntityTemplateFlagValue;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public abstract class EntityTemplateFlag extends EFlag<EntityTemplateFlagValue> {
	
	protected static final String ALL = "ALL";
	protected static final String PATTERN_SPLIT = "[,\\s]+";
	
	protected final Map<String, Set<EntityTemplate>> groups;
	protected EntityTemplateFlagValue defaults;

	public EntityTemplateFlag(String name) {
		super(name);
		
		this.groups = new ConcurrentHashMap<String, Set<EntityTemplate>>();
		this.defaults = new EntityTemplateFlagValue();
	}
	
	protected abstract Map<String, Set<EntityTemplate>> getConfig();
	
	public void reload() {
		this.groups.clear();
		this.groups.putAll(this.getConfig());
		
		Set<String> keys = this.groups.keySet();
		Set<EntityTemplate> values = new HashSet<EntityTemplate>();
		this.groups.values().forEach(value -> values.addAll(value));
		this.defaults = new EntityTemplateFlagValue(keys, values);
	}
	
	/*
	 * Parse
	 */
	
	@Override
	public EntityTemplateFlagValue parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		EntityTemplateFlagValue newFlag = null;
		if (args.isEmpty()) {
			newFlag = this.deserialize("");
		} else {
			newFlag = this.deserialize(String.join(", ", args));
		}
		
		Optional<EntityTemplateFlagValue> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			return newFlag.addAll(flag.get());
		} else {
			return newFlag;
		}
	}
	
	@Override
	public Optional<EntityTemplateFlagValue> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		if (args.isEmpty()) return Optional.empty();
		
		EntityTemplateFlagValue newFlag = this.deserialize(String.join(", ", args));
		if (newFlag.getKeys().isEmpty()) return Optional.empty();
		
		Optional<EntityTemplateFlagValue> flag = region.getFlag(this).get(group);
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
	public String serialize(EntityTemplateFlagValue value) {
		return String.join(",", value.getKeys());
	}

	@Override
	public EntityTemplateFlagValue deserialize(String value) throws IllegalArgumentException {
		if (value.equalsIgnoreCase(ALL)) return this.defaults;
		if (value.isEmpty()) return new EntityTemplateFlagValue();
		
		Set<String> keys = new HashSet<String>();
		Set<EntityTemplate> values = new HashSet<EntityTemplate>();
		for (String key : value.split(PATTERN_SPLIT)) {
			Set<EntityTemplate> blocks = this.groups.get(key.toUpperCase());
			if (blocks != null) {
				keys.add(key.toUpperCase());
				values.addAll(blocks);
			} else {
				throw new IllegalArgumentException();
			}
		}
		return new EntityTemplateFlagValue(keys, values);
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
	public Text getValueFormat(EntityTemplateFlagValue value) {
		if (value.getKeys().isEmpty()) {
			return EAMessages.FLAG_MAP_EMPTY.getText();
		}
		
		List<Text> groups = new ArrayList<Text>();
		for (String group : value.getKeys()) {
			List<Text> entities = new ArrayList<Text>();
			for (EntityTemplate entity : this.groups.get(group)) {
				entities.add(EAMessages.FLAG_MAP_HOVER.getFormat().toText("<value>", entity.getId()));
			}
			groups.add(EAMessages.FLAG_MAP_GROUP.getFormat().toText("<group>", group).toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), entities)))
				.build());
		}
		
		return Text.joinWith(EAMessages.FLAG_MAP_JOIN.getText(), groups);
	}
	
	@Override
	public EntityTemplateFlagValue getDefault() {
		return this.defaults;
	}
}
