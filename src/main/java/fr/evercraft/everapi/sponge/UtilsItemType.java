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
import org.spongepowered.api.data.type.BrickType;
import org.spongepowered.api.data.type.CoalType;
import org.spongepowered.api.data.type.DirtType;
import org.spongepowered.api.data.type.DoublePlantType;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.Fish;
import org.spongepowered.api.data.type.GoldenApple;
import org.spongepowered.api.data.type.PistonType;
import org.spongepowered.api.data.type.PlantType;
import org.spongepowered.api.data.type.PrismarineType;
import org.spongepowered.api.data.type.QuartzType;
import org.spongepowered.api.data.type.SandType;
import org.spongepowered.api.data.type.SandstoneType;
import org.spongepowered.api.data.type.ShrubType;
import org.spongepowered.api.data.type.SkullType;
import org.spongepowered.api.data.type.SlabType;
import org.spongepowered.api.data.type.StoneType;
import org.spongepowered.api.data.type.TreeType;
import org.spongepowered.api.data.type.WallType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

public class UtilsItemType {
	public static Collection<ItemType> getItems(){
		return  Sponge.getGame().getRegistry().getAllOf(ItemType.class);
	}
	
	public static Optional<ItemType> getItemType(final String type) {
		return Sponge.getGame().getRegistry().getType(ItemType.class, type);
	}
	
	public static Optional<Class<? extends CatalogType>> getCatalogType(final ItemStack item){
		Class<? extends CatalogType> type = null;
		if (item.supports(Keys.BRICK_TYPE)) {
			type = CatalogTypes.BRICK_TYPE;
		} else if (item.supports(Keys.COAL_TYPE)){
			type = CatalogTypes.COAL_TYPE;
		} else if (item.supports(Keys.DIRT_TYPE)){
			type = CatalogTypes.DIRT_TYPE;
		} else if (item.supports(Keys.DOUBLE_PLANT_TYPE)){
			type = CatalogTypes.DOUBLE_SIZE_PLANT_TYPE;
		} else if (item.supports(Keys.DYE_COLOR)){
			type = CatalogTypes.DYE_COLOR;
		} else if (item.supports(Keys.FISH_TYPE)){
			type = CatalogTypes.FISH;
		} else if (item.supports(Keys.GOLDEN_APPLE_TYPE)){
			type = CatalogTypes.GOLDEN_APPLE;
		} else if (item.supports(Keys.PISTON_TYPE)){
			type = CatalogTypes.PISTON_TYPE;
		} else if (item.supports(Keys.PLANT_TYPE)){
			type = CatalogTypes.PLANT_TYPE;
		} else if (item.supports(Keys.PRISMARINE_TYPE)){
			type = CatalogTypes.PRISMARINE_TYPE;
		} else if (item.supports(Keys.QUARTZ_TYPE)){
			type = CatalogTypes.QUARTZ_TYPE;
		} else if (item.supports(Keys.SAND_TYPE)){
			type = CatalogTypes.SAND_TYPE;
		} else if (item.supports(Keys.SANDSTONE_TYPE)){
			type = CatalogTypes.SANDSTONE_TYPE;
		} else if (item.supports(Keys.SHRUB_TYPE)){
			type = CatalogTypes.SHRUB_TYPE;
		} else if (item.supports(Keys.SKULL_TYPE)){
			type = CatalogTypes.SKULL_TYPE;
		} else if (item.supports(Keys.SLAB_TYPE)){
			type = CatalogTypes.SLAB_TYPE;
		} else if (item.supports(Keys.STONE_TYPE)){
			type = CatalogTypes.STONE_TYPE;
		} else if (item.supports(Keys.TREE_TYPE)){
			type = CatalogTypes.TREE_TYPE;
		} else if (item.supports(Keys.WALL_TYPE)){
			type = CatalogTypes.WALL_TYPE;
		} else if (item.supports(Keys.SPAWNABLE_ENTITY_TYPE)){
			type = CatalogTypes.ENTITY_TYPE;
		}
		return Optional.ofNullable(type);
	}
	
	public static Optional<ItemStack> getCatalogType(final ItemStack item, String type_string){
		boolean found = false;
		if (item.supports(Keys.BRICK_TYPE)) {
			Optional<BrickType> key = Sponge.getGame().getRegistry().getType(BrickType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.BRICK_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.COAL_TYPE)){
			Optional<CoalType> key = Sponge.getGame().getRegistry().getType(CoalType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.COAL_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.DIRT_TYPE)){
			Optional<DirtType> key = Sponge.getGame().getRegistry().getType(DirtType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.DIRT_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.DOUBLE_PLANT_TYPE)){
			Optional<DoublePlantType> key = Sponge.getGame().getRegistry().getType(DoublePlantType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.DOUBLE_PLANT_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.DYE_COLOR)){
			Optional<DyeColor> key = Sponge.getGame().getRegistry().getType(DyeColor.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.DYE_COLOR, key.get());
				found = true;
			}
		} else if (item.supports(Keys.FISH_TYPE)){
			Optional<Fish> key = Sponge.getGame().getRegistry().getType(Fish.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.FISH_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.GOLDEN_APPLE_TYPE)){
			Optional<GoldenApple> key = Sponge.getGame().getRegistry().getType(GoldenApple.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.GOLDEN_APPLE_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.PISTON_TYPE)){
			Optional<PistonType> key = Sponge.getGame().getRegistry().getType(PistonType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.PISTON_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.PLANT_TYPE)){
			Optional<PlantType> key = Sponge.getGame().getRegistry().getType(PlantType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.PLANT_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.PRISMARINE_TYPE)){
			Optional<PrismarineType> key = Sponge.getGame().getRegistry().getType(PrismarineType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.PRISMARINE_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.QUARTZ_TYPE)){
			Optional<QuartzType> key = Sponge.getGame().getRegistry().getType(QuartzType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.QUARTZ_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.SAND_TYPE)){
			Optional<SandType> key = Sponge.getGame().getRegistry().getType(SandType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.SAND_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.SANDSTONE_TYPE)){
			Optional<SandstoneType> key = Sponge.getGame().getRegistry().getType(SandstoneType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.SANDSTONE_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.SHRUB_TYPE)){
			Optional<ShrubType> key = Sponge.getGame().getRegistry().getType(ShrubType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.SHRUB_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.SKULL_TYPE)){
			Optional<SkullType> key = Sponge.getGame().getRegistry().getType(SkullType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.SKULL_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.SLAB_TYPE)){
			Optional<SlabType> key = Sponge.getGame().getRegistry().getType(SlabType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.SLAB_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.STONE_TYPE)){
			Optional<StoneType> key = Sponge.getGame().getRegistry().getType(StoneType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.STONE_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.TREE_TYPE)){
			Optional<TreeType> key = Sponge.getGame().getRegistry().getType(TreeType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.TREE_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.WALL_TYPE)){
			Optional<WallType> key = Sponge.getGame().getRegistry().getType(WallType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.WALL_TYPE, key.get());
				found = true;
			}
		} else if (item.supports(Keys.SPAWNABLE_ENTITY_TYPE)){
			Optional<EntityType> key = Sponge.getGame().getRegistry().getType(EntityType.class, type_string);
			if (key.isPresent()) {
				item.offer(Keys.SPAWNABLE_ENTITY_TYPE, key.get());
				found = true;
			}
		}
		
		if (found) {
			return Optional.of(item);
		} else {
			return Optional.empty();
		}
	}
	
	public static boolean isHelmet(ItemType type) {
		return type.equals(ItemTypes.DIAMOND_HELMET) ||
			type.equals(ItemTypes.GOLDEN_HELMET) ||
			type.equals(ItemTypes.IRON_HELMET) ||
			type.equals(ItemTypes.LEATHER_HELMET) ||
			type.equals(ItemTypes.CHAINMAIL_HELMET);
	}
}