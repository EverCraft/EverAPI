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
package fr.evercraft.everapi.sponge;

import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public class UtilsItemTypes {
	public static Collection<ItemType> getItems(){
		return  Sponge.getGame().getRegistry().getAllOf(ItemType.class);
	}
	
	public static Optional<ItemType> getItemType(final String type) {
		return Sponge.getGame().getRegistry().getType(ItemType.class, type);
	}
	
	public static Optional<Class<? extends CatalogType>> getCatalogType(final ItemStack item){
		Class<? extends CatalogType> type = null;
		if(item.supports(Keys.BRICK_TYPE)) {
			type = CatalogTypes.BRICK_TYPE;
		} else if(item.supports(Keys.COAL_TYPE)){
			type = CatalogTypes.COAL_TYPE;
		} else if(item.supports(Keys.DIRT_TYPE)){
			type = CatalogTypes.DIRT_TYPE;
		} else if(item.supports(Keys.DOUBLE_PLANT_TYPE)){
			type = CatalogTypes.DOUBLE_SIZE_PLANT_TYPE;
		} else if(item.supports(Keys.DYE_COLOR)){
			type = CatalogTypes.DYE_COLOR;
		} else if(item.supports(Keys.FISH_TYPE)){
			type = CatalogTypes.FISH;
		} else if(item.supports(Keys.GOLDEN_APPLE_TYPE)){
			type = CatalogTypes.GOLDEN_APPLE;
		} else if(item.supports(Keys.PISTON_TYPE)){
			type = CatalogTypes.PISTON_TYPE;
		} else if(item.supports(Keys.PLANT_TYPE)){
			type = CatalogTypes.PLANT_TYPE;
		} else if(item.supports(Keys.PRISMARINE_TYPE)){
			type = CatalogTypes.PRISMARINE_TYPE;
		} else if(item.supports(Keys.QUARTZ_TYPE)){
			type = CatalogTypes.QUARTZ_TYPE;
		} else if(item.supports(Keys.SAND_TYPE)){
			type = CatalogTypes.SAND_TYPE;
		} else if(item.supports(Keys.SANDSTONE_TYPE)){
			type = CatalogTypes.SANDSTONE_TYPE;
		} else if(item.supports(Keys.SHRUB_TYPE)){
			type = CatalogTypes.SHRUB_TYPE;
		} else if(item.supports(Keys.SKULL_TYPE)){
			type = CatalogTypes.SKULL_TYPE;
		} else if(item.supports(Keys.SLAB_TYPE)){
			type = CatalogTypes.SLAB_TYPE;
		} else if(item.supports(Keys.STONE_TYPE)){
			type = CatalogTypes.STONE_TYPE;
		} else if(item.supports(Keys.TREE_TYPE)){
			type = CatalogTypes.TREE_TYPE;
		} else if(item.supports(Keys.WALL_TYPE)){
			type = CatalogTypes.WALL_TYPE;
		}
		return Optional.ofNullable(type);
	}
}