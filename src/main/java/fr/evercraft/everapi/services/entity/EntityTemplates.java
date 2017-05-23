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

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.util.Tristate;

import com.google.common.collect.ImmutableMap;

public interface EntityTemplates {
	
	public static final EntityTemplate CHARGED_CREEPER = new EntityPatternTemplate(
			"CHARGED_CREEPER", 
			EntityTypes.CREEPER, 
			ImmutableMap.of(EntityTemplate.Properties.CREEPER_CHARGED, true));
	
	public static final EntityTemplate WOLF_ANGRY = new EntityPatternTemplate(
			"WOLF_ANGRY", 
			EntityTypes.WOLF, 
			ImmutableMap.of(EntityTemplate.Properties.ANGRY, true));
	
	public static final EntityTemplate WOLF_PASSIVE = new EntityPatternTemplate(
			"WOLF_PASSIVE", 
			EntityTypes.WOLF, 
			ImmutableMap.of(EntityTemplate.Properties.ANGRY, false));
	
	public static final EntityTemplate WOLF_OWNER = new EntityPatternTemplate(
			"WOLF_OWNER", 
			EntityTypes.WOLF, 
			ImmutableMap.of(EntityTemplate.Properties.TAMED_OWNER, Tristate.TRUE));
	
	public static final EntityTemplate HORSE_OWNER = new EntityPatternTemplate(
			"HORSE_OWNER", 
			EntityTypes.HORSE, 
			ImmutableMap.of(EntityTemplate.Properties.TAMED_OWNER, Tristate.TRUE));
	
	public static final EntityTemplate MULE_OWNER = new EntityPatternTemplate(
			"MULE_OWNER", 
			EntityTypes.MULE, 
			ImmutableMap.of(EntityTemplate.Properties.TAMED_OWNER, Tristate.TRUE));
	
	public static final EntityTemplate OCELOT_OWNER = new EntityPatternTemplate(
			"OCELOT_OWNER", 
			EntityTypes.OCELOT, 
			ImmutableMap.of(EntityTemplate.Properties.TAMED_OWNER, Tristate.TRUE));
}
