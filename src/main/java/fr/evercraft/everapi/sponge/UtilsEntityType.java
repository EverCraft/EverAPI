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
			EntityTypes.COW,
			EntityTypes.CHICKEN,
			EntityTypes.HORSE,
			EntityTypes.MUSHROOM_COW,
			EntityTypes.OCELOT,
			EntityTypes.PIG,
			EntityTypes.RABBIT,
			EntityTypes.SHEEP,
			EntityTypes.SQUID,
			EntityTypes.VILLAGER,
			EntityTypes.WOLF
	);
	
	public static final List<EntityType> MONSTERS = Arrays.asList(
			EntityTypes.BLAZE,
			EntityTypes.CAVE_SPIDER,
			EntityTypes.CREEPER,
			EntityTypes.ENDERMAN,
			EntityTypes.ENDERMITE,
			EntityTypes.ENDER_DRAGON,
			EntityTypes.GHAST,
			EntityTypes.GIANT,
			EntityTypes.GUARDIAN,
			EntityTypes.IRON_GOLEM,
			EntityTypes.MAGMA_CUBE,
			EntityTypes.PIG_ZOMBIE,
			EntityTypes.POLAR_BEAR,
			EntityTypes.SHULKER,
			EntityTypes.SHULKER_BULLET,
			EntityTypes.SILVERFISH,
			EntityTypes.SKELETON,
			EntityTypes.SLIME,
			EntityTypes.SNOWMAN,
			EntityTypes.SPIDER,
			EntityTypes.WITCH,
			EntityTypes.WITHER,
			EntityTypes.ZOMBIE,
			EntityTypes.WOLF
	);
	
	public static final List<EntityType> OTHERS = Arrays.asList(
			EntityTypes.AREA_EFFECT_CLOUD,
			EntityTypes.ARMOR_STAND,
			EntityTypes.BOAT,
			EntityTypes.CHESTED_MINECART,
			EntityTypes.COMMANDBLOCK_MINECART,
			EntityTypes.COMPLEX_PART,
			EntityTypes.DRAGON_FIREBALL,
			EntityTypes.EGG,
			EntityTypes.ENDER_CRYSTAL,
			EntityTypes.ENDER_PEARL,
			EntityTypes.EXPERIENCE_ORB,
			EntityTypes.EYE_OF_ENDER,
			EntityTypes.FALLING_BLOCK,
			EntityTypes.FIREBALL,
			EntityTypes.FIREWORK,
			EntityTypes.FISHING_HOOK,
			EntityTypes.FURNACE_MINECART,
			EntityTypes.HOPPER_MINECART,
			EntityTypes.HUMAN,
			EntityTypes.FURNACE_MINECART,
			EntityTypes.ITEM,
			EntityTypes.ITEM_FRAME,
			EntityTypes.LEASH_HITCH,
			EntityTypes.LIGHTNING,
			EntityTypes.MOB_SPAWNER_MINECART,
			EntityTypes.PAINTING,
			EntityTypes.PLAYER,
			EntityTypes.PRIMED_TNT,
			EntityTypes.RIDEABLE_MINECART,
			EntityTypes.SMALL_FIREBALL,
			EntityTypes.SNOWBALL,
			EntityTypes.SPLASH_POTION,
			EntityTypes.THROWN_EXP_BOTTLE,
			EntityTypes.TNT_MINECART,
			EntityTypes.UNKNOWN,
			EntityTypes.WEATHER,
			EntityTypes.WITHER_SKULL
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
