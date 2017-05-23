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
