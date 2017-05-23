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
package fr.evercraft.everapi.services.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.util.Tristate;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class EEntityConfig extends EConfig<EverAPI> {

	public EEntityConfig(final EverAPI plugin) {
		super(plugin, "types/entities");
	}
	
	@Override
	public void loadDefault() {
		if (this.getNode().getValue() == null) {
			Map<String, Object> entity = new HashMap<String, Object>();
			
			entity.put("type", EntityTypes.CREEPER.getId());
			entity.put("properties", ImmutableMap.of(EntityTemplate.Properties.CREEPER_CHARGED.getId(), true));
			addDefault("CHARGED_CREEPER", entity);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<EntityTemplate> getEntities() {
		Set<EntityTemplate> entities = new HashSet<EntityTemplate>();
		this.getNode().getChildrenMap().forEach((key, config) -> {
			String identifier = key.toString();
			if (identifier.isEmpty()) {
				this.plugin.getLogger().warn("[EntityService][Config] Error Identifier : (identifier=Empty)");
				return;
			}
			
			String typeString = config.getNode("type").getString("");
			Optional<EntityType> type = this.plugin.getGame().getRegistry().getType(EntityType.class, typeString);
			if (!type.isPresent()) {
				this.plugin.getLogger().warn("[EntityService][Config] Error Type : (identifier='" + identifier + "';type='" + typeString + "')");
				return;
			}
			
			ImmutableMap.Builder properties = ImmutableMap.builder();
			for (Entry<Object, ? extends CommentedConfigurationNode> entry : config.getNode("properties").getChildrenMap().entrySet()) {
				String propertyString = entry.getKey().toString();
				Optional<EntityTemplate.Property> property = this.plugin.getGame().getRegistry().getType(EntityTemplate.Property.class, propertyString);
				if (!property.isPresent()) {
					this.plugin.getLogger().warn("[EntityService][Config] Error Property : (identifier='" + identifier + "';type='" + typeString + "';property='" + propertyString + "')");
					return;
				}
				
				TypeToken<?> propertyValue = property.get().getType();
				if (propertyValue.getRawType().equals(Boolean.class)) {
					properties.put(property.get(), Boolean.valueOf(entry.getValue().getString("")));
				} else if (propertyValue.getRawType().equals(Tristate.class)) {
					properties.put(property.get(), Tristate.valueOf(entry.getValue().getString("")));
				} else if (propertyValue.getRawType().equals(String.class)) {
					properties.put(property.get(), Tristate.valueOf(entry.getValue().getString("")));
				} else {
					this.plugin.getLogger().warn("[EntityService][Config] Error Property TypeValue : (identifier='" + identifier + "';type='" + typeString + "';property='" + propertyString + "')");
					return;
				}
			}

			entities.add(new EntityPatternTemplate(identifier, type.get(), properties.build()));
		});
		return entities;
	}
}
