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

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.Professions;
import org.spongepowered.api.data.type.SkeletonTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public enum UtilsEntity {
	BLAZE(EntityTypes.BLAZE),
	CAVE_SPIDER(EntityTypes.CAVE_SPIDER),
	CHARGED_CREEPER(EntityTypes.CREEPER, "CHARGED_CREEPER"),
	CHICKEN(EntityTypes.CHICKEN),
	CHICKEN_JOCKEY(EntityTypes.CHICKEN),
	COW(EntityTypes.COW),
	CREEPER(EntityTypes.CREEPER),
	ELDER_GUARDIAN(EntityTypes.GUARDIAN, "ELDER_GUARDIAN"),
	ENDER_DRAGON(EntityTypes.ENDER_DRAGON),
	ENDERMAN(EntityTypes.ENDERMAN),
	ENDERMITE(EntityTypes.ENDERMITE),
	GHAST(EntityTypes.GHAST),
	GUARDIAN(EntityTypes.GUARDIAN),
	HORSE(EntityTypes.HORSE),
	IRON_GOLEM(EntityTypes.IRON_GOLEM),
	MAGMA_CUBE(EntityTypes.MAGMA_CUBE),
	MUSHROOM_COW(EntityTypes.MUSHROOM_COW),
	OCELOT(EntityTypes.OCELOT),
	PIG(EntityTypes.PIG),
	PIG_ZOMBIE(EntityTypes.PIG_ZOMBIE),
	RABBIT(EntityTypes.RABBIT),
	SHEEP(EntityTypes.SHEEP),
	SILVERFISH(EntityTypes.SILVERFISH),
	SKELETON(EntityTypes.SKELETON),
	SLIME(EntityTypes.SLIME),
	SNOWMAN(EntityTypes.SNOWMAN),
	SPIDER(EntityTypes.SPIDER),
	SPIDER_JOCKEY(EntityTypes.SKELETON, "SPIDER_JOCKEY"),
	SQUID(EntityTypes.SQUID),
	VILLAGER(EntityTypes.VILLAGER),
	WITCH(EntityTypes.WITCH),
	WITHER_SKELETON(EntityTypes.SKELETON, "WITHER_SKELETON"),
	WOLF(EntityTypes.WOLF),
	ZOMBIE(EntityTypes.ZOMBIE),
	ZOMBIE_VILLAGER(EntityTypes.ZOMBIE, "ZOMBIE_VILLAGER");

	private final EntityType type;
	private final String name;
	
	UtilsEntity(final EntityType type) {
		this.type = type;
		this.name = type.getTranslation().get().toUpperCase().replaceAll(" ", "_");
	}

	UtilsEntity(final EntityType type, final String name) {
		this.type = type;
		this.name = name;
	}

	public EntityType getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	public boolean spawnEntity(final Location<World> spawnLocation) {
	    Entity entity = spawnLocation.getExtent().createEntity(this.type, spawnLocation.getPosition());
	    spawnLocation.getExtent().spawnEntity(getEntity(entity), Cause.source(EntitySpawnCause.builder()
                .entity(entity).type(SpawnTypes.PLUGIN).build()).build());
        return true;
	}
	
	public Entity getEntity(final Entity entity){
		if(this.equals(UtilsEntity.ELDER_GUARDIAN)) {
			entity.offer(Keys.ELDER_GUARDIAN, true);
		} else if(this.equals(UtilsEntity.CHARGED_CREEPER)) {
			entity.offer(Keys.CREEPER_CHARGED, true);
		} else if(this.equals(UtilsEntity.ZOMBIE_VILLAGER)) {
			entity.offer(Keys.VILLAGER_ZOMBIE_PROFESSION, Professions.BUTCHER);
		} else if(this.equals(UtilsEntity.WITHER_SKELETON)) {
			entity.offer(Keys.SKELETON_TYPE, SkeletonTypes.WITHER);
		} else if (this.equals(UtilsEntity.SPIDER_JOCKEY)) {
		    Entity vehicle = entity.getLocation().getExtent().createEntity(EntityTypes.SPIDER, entity.getLocation().getPosition());
		    entity.setVehicle(vehicle);
		}
		return entity;
	}
	
	public static Optional<UtilsEntity> get(final String name) {
		UtilsEntity entity = null;
		int cpt = 0;
		while(cpt < values().length && entity == null){
			if (values()[cpt].getName().equalsIgnoreCase(name)) {
				entity = values()[cpt];
			}
			cpt++;
		}
		return Optional.ofNullable(entity);
	}
}
