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

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.Tristate;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.java.UtilsPredicate.TriPredicate;
import fr.evercraft.everapi.services.entity.property.EntityTemplateProperty;

public interface EntityTemplate extends CatalogType {
	
	EntityType getType();
	
	boolean apply(Entity entity);
	boolean contains(Entity entity);
	
	boolean apply(Entity entity, Player player);
	boolean contains(Entity entity, Player player);
	
	static EntityTemplate of(EntityType type) {
		return new EntityTypeTemplate(type);
	}
	
	public interface Property<T> extends CatalogType {
		TypeToken<T> getType();
		
		TriPredicate<T, Entity, Optional<Player>> getApply();
		TriPredicate<T, Entity, Optional<Player>> getContains();
	}
	
	public interface Properties<T> extends CatalogType {
		public static final EntityTemplate.Property<Boolean> CREEPER_CHARGED = new EntityTemplateProperty<Boolean>("CREEPER_CHARGED", TypeToken.of(Boolean.class),
			(value, entity, player) -> entity.offer(Keys.CREEPER_CHARGED, value).isSuccessful(), 
			(value, entity, player) -> entity.get(Keys.CREEPER_CHARGED).orElse(false).equals(value));
			
		public static final EntityTemplate.Property<Boolean> ANGRY = new EntityTemplateProperty<Boolean>("ANGRY", TypeToken.of(Boolean.class),
			(value, entity, player) -> {
				if (entity.offer(Keys.ANGRY, value).isSuccessful()) {
					if (value && entity instanceof Agent && player.isPresent()) {
						((Agent) entity).setTarget(player.get());
					}
					return true;
				}
				return false;
			}, 
			(value, entity, player) -> entity.get(Keys.ANGRY).orElse(false).equals(value));
		
		public static final EntityTemplate.Property<Tristate> OWNER = new EntityTemplateProperty<Tristate>("OWNER", TypeToken.of(Tristate.class),
			(value, entity, player) -> {
				if (value.equals(Tristate.UNDEFINED)) {
					entity.setCreator(null);
					return player.isPresent() && entity.offer(Keys.TAMED_OWNER, Optional.of(player.get().getUniqueId())).isSuccessful();
				} else if (value.equals(Tristate.TRUE)) {
					if (player.isPresent()) {
						entity.setCreator(player.get().getUniqueId());
						return true;
					}
					return false;
				} else if (value.equals(Tristate.FALSE)) {
					entity.setCreator(UUID.randomUUID());
					return true;
				}
				return false;
			}, 
			(value, entity, player) -> {
				Optional<UUID> creator = entity.getCreator();
				if (value.equals(Tristate.UNDEFINED)) {
					return !creator.isPresent();
				} else if (value.equals(Tristate.TRUE)) {
					return creator.isPresent() && player.isPresent() && creator.get().equals(player.get().getUniqueId());
				} else if (value.equals(Tristate.FALSE)) {
					return creator.isPresent() && (!player.isPresent() || !creator.get().equals(player.get().getUniqueId()));
				}
				return false;
			});
		
		public static final EntityTemplate.Property<Tristate> TAMED_OWNER = new EntityTemplateProperty<Tristate>("TAMED_OWNER", TypeToken.of(Tristate.class),
			(value, entity, player) -> {
				if (value.equals(Tristate.UNDEFINED)) {
					return entity.offer(Keys.TAMED_OWNER, Optional.empty()).isSuccessful();
				} else if (value.equals(Tristate.TRUE)) {
					return player.isPresent() && entity.offer(Keys.TAMED_OWNER, Optional.of(player.get().getUniqueId())).isSuccessful();
				} else if (value.equals(Tristate.FALSE)) {
					return entity.offer(Keys.TAMED_OWNER, Optional.of(UUID.randomUUID())).isSuccessful();
				}
				return false;
			}, 
			(value, entity, player) -> {
				Optional<Optional<UUID>> opt = entity.get(Keys.TAMED_OWNER);
				if (value.equals(Tristate.UNDEFINED)) {
					return !opt.isPresent() || !opt.get().isPresent();
				} else if (value.equals(Tristate.TRUE)) {
					if (opt.isPresent() && opt.get().isPresent() && player.isPresent()) {
						return opt.get().get().equals(player.get().getUniqueId());
					}
					return false;
				} else if (value.equals(Tristate.FALSE)) {
					if (opt.isPresent() && opt.get().isPresent()) {
						return !player.isPresent() || !opt.get().get().equals(player.get().getUniqueId());
					}
					return false;
				}
				return false;
			});
	}
}
