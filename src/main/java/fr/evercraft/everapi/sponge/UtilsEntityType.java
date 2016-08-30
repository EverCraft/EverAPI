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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;

public class UtilsEntityType {
	public static final List<EntityType> ANIMALS = Arrays.asList(
			EntityTypes.BAT,
			EntityTypes.PIG,
			EntityTypes.SHEEP,
			EntityTypes.COW,
			EntityTypes.CHICKEN,
			EntityTypes.SQUID,
			EntityTypes.MUSHROOM_COW,
			EntityTypes.OCELOT,
			EntityTypes.HORSE,
			EntityTypes.VILLAGER,
			EntityTypes.HORSE,
			EntityTypes.RABBIT,
			EntityTypes.WOLF
	);
	
	public static final List<EntityType> MONSTERS = Arrays.asList(
			EntityTypes.CREEPER,
			EntityTypes.SKELETON,
			EntityTypes.SPIDER,
			EntityTypes.GIANT,
			EntityTypes.ZOMBIE,
			EntityTypes.SLIME,
			EntityTypes.GHAST,
			EntityTypes.PIG_ZOMBIE,
			EntityTypes.ENDERMAN,
			EntityTypes.CAVE_SPIDER,
			EntityTypes.SILVERFISH,
			EntityTypes.BLAZE,
			EntityTypes.MAGMA_CUBE,
			EntityTypes.ENDER_DRAGON,
			EntityTypes.WITHER,
			EntityTypes.WITCH,
			EntityTypes.WOLF,
			EntityTypes.IRON_GOLEM,
			EntityTypes.SNOWMAN,
			EntityTypes.GUARDIAN,
			EntityTypes.ENDERMITE
	);
	
	public static final List<EntityType> OTHERS = Arrays.asList(
			EntityTypes.ITEM,
			EntityTypes.EXPERIENCE_ORB,
			EntityTypes.LEASH_HITCH,
			EntityTypes.PAINTING,
			EntityTypes.SNOWBALL,
			EntityTypes.FIREBALL,
			EntityTypes.SMALL_FIREBALL,
			EntityTypes.ENDER_PEARL,
			EntityTypes.EYE_OF_ENDER,
			EntityTypes.THROWN_EXP_BOTTLE,
			EntityTypes.ITEM_FRAME,
			EntityTypes.WITHER_SKULL,
			EntityTypes.PRIMED_TNT,
			EntityTypes.FALLING_BLOCK,
			EntityTypes.FIREWORK,
			EntityTypes.COMMANDBLOCK_MINECART,
			EntityTypes.ARMOR_STAND,
			EntityTypes.BOAT,
			EntityTypes.RIDEABLE_MINECART,
			EntityTypes.CHESTED_MINECART,
			EntityTypes.FURNACE_MINECART,
			EntityTypes.TNT_MINECART,
			EntityTypes.HOPPER_MINECART,
			EntityTypes.MOB_SPAWNER_MINECART,
			EntityTypes.ENDER_CRYSTAL,
			EntityTypes.SPLASH_POTION,
			EntityTypes.EGG,
			EntityTypes.FISHING_HOOK,
			EntityTypes.LIGHTNING,
			EntityTypes.WEATHER,
			EntityTypes.COMPLEX_PART
	);
	
	public static final List<EntityType> getAll(){
		List<EntityType> entities = new ArrayList<EntityType>();
		entities.addAll(ANIMALS);
		entities.addAll(MONSTERS);
		entities.addAll(OTHERS);
		return entities;
	}
	
	public static Optional<EntityType> getAnimals(final String name) {
		EntityType entity = null;
		int cpt = 0;
		while(cpt < ANIMALS.size() && entity == null){
			if (ANIMALS.get(cpt).getName().equalsIgnoreCase(name)) {
				entity = ANIMALS.get(cpt);
			}
			cpt++;
		}
		return Optional.ofNullable(entity);
	}
	
	public static Optional<EntityType> getMonsters(final String name) {
		EntityType entity = null;
		int cpt = 0;
		while(cpt < MONSTERS.size() && entity == null){
			if (MONSTERS.get(cpt).getName().equalsIgnoreCase(name)) {
				entity = MONSTERS.get(cpt);
			}
			cpt++;
		}
		return Optional.ofNullable(entity);
	}
	
	public static Optional<EntityType> getOthers(final String name) {
		EntityType entity = null;
		int cpt = 0;
		while(cpt < OTHERS.size() && entity == null){
			if (OTHERS.get(cpt).getName().equalsIgnoreCase(name)) {
				entity = OTHERS.get(cpt);
			}
			cpt++;
		}
		return Optional.ofNullable(entity);
	}
	
	public static boolean isAnimal(Entity entity) {
		return UtilsEntityType.ANIMALS.contains(entity.getType()) && !entity.get(Keys.ANGRY).orElse(false);
	}
	
	public static boolean isMonster(Entity entity) {
		return UtilsEntityType.MONSTERS.contains(entity.getType()) && entity.get(Keys.ANGRY).orElse(true);
	}
}
