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
package fr.evercraft.everapi.services.fire;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class EFireConfig extends EConfig<EverAPI> {

	public EFireConfig(final EverAPI plugin) {
		super(plugin, "types/fires");
	}
	
	@Override
	public void loadDefault() {
		if (this.getNode().getValue() == null) {
			/*Map<String, Object> fires = new HashMap<String, Object>();
			
			fires.put("entities", Arrays.asList(EntityTypes.SMALL_FIREBALL.getId()));
			fires.put("blocks", Arrays.asList(BlockTypes.DISPENSER.getId()));
			fires.put("items", Arrays.asList(ItemTypes.FIRE_CHARGE.getId()));
			addDefault("FIRE_CHARGE", fires);*/
		}
	}

	public Set<FireType> getFires() {
		Set<FireType> fires = new HashSet<FireType>();
		this.getNode().getChildrenMap().forEach((key, config) -> {
			String identifier = key.toString();
			if (identifier.isEmpty()) {
				this.plugin.getLogger().warn("[FireService][Config] Error Identifier : (identifier=Empty)");
				return;
			}
			
			ImmutableSet.Builder<EntityType> entities = ImmutableSet.builder();
			for (Entry<Object, ? extends CommentedConfigurationNode> entry : config.getNode("entities").getChildrenMap().entrySet()) {
				String entityString = entry.getKey().toString();
				Optional<EntityType> entity = this.plugin.getGame().getRegistry().getType(EntityType.class, entityString);
				if (!entity.isPresent()) {
					this.plugin.getLogger().warn("[FireService][Config] Error EntityType : (identifier='" + identifier + "';entityType='" + entityString + "')");
					return;
				}
				entities.add(entity.get());
			}
			
			ImmutableSet.Builder<BlockType> blocks = ImmutableSet.builder();
			for (Entry<Object, ? extends CommentedConfigurationNode> entry : config.getNode("blocks").getChildrenMap().entrySet()) {
				String blockString = entry.getKey().toString();
				Optional<BlockType> block = this.plugin.getGame().getRegistry().getType(BlockType.class, blockString);
				if (!block.isPresent()) {
					this.plugin.getLogger().warn("[FireService][Config] Error BlockType : (identifier='" + identifier + "';blockType='" + blockString + "')");
					return;
				}
				blocks.add(block.get());
			}
			
			ImmutableSet.Builder<ItemType> items = ImmutableSet.builder();
			for (Entry<Object, ? extends CommentedConfigurationNode> entry : config.getNode("items").getChildrenMap().entrySet()) {
				String blockString = entry.getKey().toString();
				Optional<ItemType> item = this.plugin.getGame().getRegistry().getType(ItemType.class, blockString);
				if (!item.isPresent()) {
					this.plugin.getLogger().warn("[FireService][Config] Error BlockType : (identifier='" + identifier + "';blockType='" + blockString + "')");
					return;
				}
				items.add(item.get());
			}

			fires.add(new EFireType(identifier, entities.build(), blocks.build(), items.build()));
		});
		return fires;
	}
}
