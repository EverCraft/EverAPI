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

import java.util.Set;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;

public class EFireType implements FireType {

	private final String identifier;
	private final String name;
	
	private final Set<EntityType> entities;
	private final Set<BlockType> blocks;
	private final Set<ItemType> items;
	
	public EFireType(String name, Set<EntityType> entities, Set<BlockType> blocks, Set<ItemType> items) {
		this("evercraft:" + name, name, entities, blocks, items);
	}
	
	public EFireType(String identifier, String name, Set<EntityType> entities, Set<BlockType> blocks, Set<ItemType> items) {
		this.identifier = identifier.toLowerCase();
		this.name = name.toUpperCase();
		
		this.entities = entities;
		this.blocks = blocks;
		this.items = items;
	}

	@Override
	public String getId() {
		return this.identifier;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Set<EntityType> getEntities() {
		return this.entities;
	}
	
	@Override
	public Set<BlockType> getBlocks() {
		return this.blocks;
	}
	
	@Override
	public Set<ItemType> getItems() {
		return this.items;
	}
}
