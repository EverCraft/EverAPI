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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiPredicate;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;
import ninja.leaping.configurate.ConfigurationNode;

public class EEntityConfig extends EConfig<EverAPI> {

	public EEntityConfig(final EverAPI plugin) {
		super(plugin, "entities");
	}
	
	@Override
	public void loadDefault() {
		if (this.getNode().getValue() == null) {
			Map<String, Object> entity = new HashMap<String, Object>();
			
			entity.put("type", EntityTypes.CREEPER.getId());
			entity.put("data", ImmutableMap.of(Keys.CREEPER_CHARGED.getId(), true));
			addDefault("CHARGED_CREEPER", entity);
			
			entity.clear();
			entity.put("type", EntityTypes.WOLF.getId());
			entity.put("data", ImmutableMap.of(Keys.ANGRY.getId(), true));
			addDefault("WOLF_ANGRY", entity);
			
			entity.clear();
			entity.put("type", EntityTypes.WOLF.getId());
			entity.put("data", ImmutableMap.of(Keys.ANGRY.getId(), false));
			addDefault("WOLF_PASSIVE", entity);
			
			entity.clear();
			entity.put("type", EntityTypes.WOLF.getId());
			entity.put("data", ImmutableMap.of(Keys.TAMED_OWNER.getId(), "PLAYER"));
			addDefault("WOLF_OWNER", entity);
			
			entity.clear();
			entity.put("type", EntityTypes.HORSE.getId());
			entity.put("data", ImmutableMap.of(Keys.TAMED_OWNER.getId(), "PLAYER"));
			addDefault("HORSE_OWNER", entity);
		}
	}

	public Set<EntityTemplate> getEntities() {
		Set<EntityTemplate> entities = new HashSet<EntityTemplate>();
		this.getNode().getChildrenMap().forEach((key, config) -> {
			String identifier = key.toString();
			if (identifier.isEmpty()) {
				this.plugin.getLogger().warn("[Config] Error Identifier : (identifier=Empty)");
				return;
			}
			
			String typeString = config.getNode("type").getString("");
			Optional<EntityType> type = this.plugin.getGame().getRegistry().getType(EntityType.class, typeString);
			if (!type.isPresent()) {
				this.plugin.getLogger().warn("[Config] Error Type : (identifier='" + identifier + "';type='" + typeString + "')");
				return;
			}
			
			Set<BiPredicate<Entity, Optional<Player>>> apply = new HashSet<BiPredicate<Entity, Optional<Player>>>();
			Set<BiPredicate<Entity, Optional<Player>>> contains = new HashSet<BiPredicate<Entity, Optional<Player>>>();
			
			config.getNode("data").getChildrenMap().forEach((key2, value) -> {
				String data = key2.toString();
				
				try {
					if (data.equalsIgnoreCase(Keys.CREEPER_CHARGED.getId())) {
						this.creeperCharged(value, apply, contains);
					} else if (data.equalsIgnoreCase(Keys.ANGRY.getId())) {
						this.angry(value, apply, contains);
					} else if (data.equalsIgnoreCase(Keys.TAMED_OWNER.getId())) {
						this.tamedOwner(value, apply, contains);
					} else if (data.equalsIgnoreCase("OWNER")) {
						this.owner(value, apply, contains);
					}
				} catch (Exception e) {
					this.plugin.getLogger().warn("[Config] Error Value : (identifier='" + identifier + "';type='" + typeString + "';data='" + data + "')");
				}
			});
			
			BiPredicate<Entity, Optional<Player>> applyPredicate = apply.stream().reduce((p1, p2) -> p1.and(p2)).orElse((entity, player) -> true);
			BiPredicate<Entity, Optional<Player>> containsPredicate = contains.stream().reduce((p1, p2) -> p1.and(p2)).orElse((entity, player) -> true);
			
			entities.add(new EntityPatternTemplate(identifier, type.get(), applyPredicate, containsPredicate));
		});
		return entities;
	}
	
	private void creeperCharged(ConfigurationNode value, 
			Set<BiPredicate<Entity, Optional<Player>>> apply, Set<BiPredicate<Entity, Optional<Player>>> contains) {
		
		final boolean bool = value.getBoolean(false);
		apply.add((entity, player) -> entity.offer(Keys.CREEPER_CHARGED, bool).isSuccessful());
		contains.add((entity, player) -> entity.get(Keys.CREEPER_CHARGED).orElse(false).equals(bool));
	}
	
	private void angry(ConfigurationNode value, 
			Set<BiPredicate<Entity, Optional<Player>>> apply, Set<BiPredicate<Entity, Optional<Player>>> contains) {
		
		final boolean bool = value.getBoolean(false);
		apply.add((entity, player) -> {
			if (entity.offer(Keys.ANGRY, true).isSuccessful()) {
				if (entity instanceof Agent && player.isPresent()) {
					((Agent) entity).setTarget(player.get());
				}
				return true;
			}
			return false;
		});
		contains.add((entity, player) -> entity.get(Keys.ANGRY).orElse(false).equals(bool));
	}

	private void tamedOwner(ConfigurationNode value, 
			Set<BiPredicate<Entity, Optional<Player>>> apply, Set<BiPredicate<Entity, Optional<Player>>> contains) {
		
		String valueString = value.getString("");
		if (valueString.equalsIgnoreCase("EMPTY")) {
			apply.add((entity, player) -> entity.offer(Keys.TAMED_OWNER, Optional.empty()).isSuccessful());
			contains.add((entity, player) -> {
				Optional<Optional<UUID>> opt = entity.get(Keys.TAMED_OWNER);
				return !opt.isPresent() || opt.get().isPresent();
			});
			
		} else if (valueString.equalsIgnoreCase("PLAYER")) {
			apply.add((entity, player) -> player.isPresent() && entity.offer(Keys.TAMED_OWNER, Optional.of(player.get().getUniqueId())).isSuccessful());
			contains.add((entity, player) -> {
				Optional<Optional<UUID>> opt = entity.get(Keys.TAMED_OWNER);
				if (opt.isPresent() && opt.get().isPresent() && player.isPresent()) {
					return opt.get().get().equals(player.get().getUniqueId());
				}
				return false;
			});
			
		} else {
			UUID uuid = UUID.fromString(valueString);
			apply.add((entity, player) -> entity.offer(Keys.TAMED_OWNER, Optional.of(uuid)).isSuccessful());
			contains.add((entity, player) -> {
				Optional<Optional<UUID>> opt = entity.get(Keys.TAMED_OWNER);
				if (opt.isPresent() && opt.get().isPresent()) {
					return opt.get().get().equals(uuid);
				}
				return false;
			});
		}
	}
	
	private void owner(ConfigurationNode value, 
			Set<BiPredicate<Entity, Optional<Player>>> apply, Set<BiPredicate<Entity, Optional<Player>>> contains) {
		
		String valueString = value.getString("");
		if (valueString.equalsIgnoreCase("EMPTY")) {
			apply.add((entity, player) -> {
				entity.setCreator(null);
				return true;
			});
			contains.add((entity, player) -> !entity.getCreator().isPresent());
			
		} else if (valueString.equalsIgnoreCase("PLAYER")) {
			apply.add((entity, player) -> {
				if (player.isPresent()) {
					entity.setCreator(player.get().getUniqueId());
				}
				return true;
			});
			contains.add((entity, player) -> {
				Optional<UUID> opt = entity.getCreator();
				return opt.isPresent() && player.isPresent() && opt.get().equals(player.get().getUniqueId());
			});
			
		} else {
			UUID uuid = UUID.fromString(valueString);
			apply.add((entity, player) -> {
				entity.setCreator(uuid);
				return true;
			});
			contains.add((entity, player) -> {
				Optional<UUID> opt = entity.getCreator();
				return opt.isPresent() && opt.get().equals(uuid);
			});
		}
	}
}
