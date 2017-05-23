package fr.evercraft.everapi.services.fire;

import java.util.Set;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;

public interface FireType extends CatalogType {

	Set<EntityType> getEntities();

	Set<BlockType> getBlocks();

	Set<ItemType> getItems();
	
}
