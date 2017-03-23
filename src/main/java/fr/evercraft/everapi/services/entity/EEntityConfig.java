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
import java.util.Set;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;

public class EEntityConfig extends EConfig<EverAPI> {

	public EEntityConfig(final EverAPI plugin) {
		super(plugin, "entities");
	}
	
	@Override
	public void loadDefault() {
		if (this.getNode().getValue() == null) {
			Map<String, Object> entity = new HashMap<String, Object>();
			
			entity.put("type", EntityTypes.CREEPER.getId());
			entity.put("data", ImmutableMap.of(Keys.CREEPER_CHARGED.getId(), true));
			addDefault("CHARGED_CREEPER", entity);
			
			entity.clear();
			entity.put("type", EntityTypes.WOLF.getId());
			entity.put("data", ImmutableMap.of(Keys.ANGRY.getId(), true));
			addDefault("WOLF_ANGRY", entity);
			
			entity.clear();
			entity.put("type", EntityTypes.HORSE.getId());
			entity.put("data", ImmutableMap.of(Keys.TAMED_OWNER.getId(), "<UUID>"));
			addDefault("HORSE_OWNER", entity);
		}
	}

	public Set<EntityTemplate> getEntities() {
		Set<EntityTemplate> entities = new HashSet<EntityTemplate>();
		this.getNode().getChildrenMap().forEach((identifier, config) -> {
			try {
				entities.add(new EntityValuesTemplate(identifier.toString(), config.getNode("type").getString("evercraft:" + identifier), config.getNode("data")));
			} catch (Exception e) {
				
			}
		});
		return entities;
	}

}
