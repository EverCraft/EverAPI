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
package fr.evercraft.everapi.services.fire;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.ItemTypes;

import com.google.common.collect.ImmutableSet;

public interface FireTypes {
	public static final FireType LAVA = new EFireType("LAVA", ImmutableSet.of(), ImmutableSet.of(BlockTypes.LAVA), ImmutableSet.of());
	
	public static final FireType FLINT_AND_STELL = new EFireType("FLINT_AND_STELL", ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of(ItemTypes.FLINT_AND_STEEL));
	
	public static final FireType FIRE_CHARGE = new EFireType("FIRE_CHARGE", ImmutableSet.of(EntityTypes.SMALL_FIREBALL), ImmutableSet.of(), ImmutableSet.of(ItemTypes.FIRE_CHARGE));
	
	public static final FireType LIGHTNING = new EFireType("LIGHTNING", ImmutableSet.of(EntityTypes.LIGHTNING), ImmutableSet.of(), ImmutableSet.of());
	
	public static final FireType EXPLOSION = new EFireType("EXPLOSION", ImmutableSet.of(EntityTypes.CREEPER, EntityTypes.FIREBALL, EntityTypes.PRIMED_TNT), ImmutableSet.of(), ImmutableSet.of());
	
	public static final FireType PROPAGATION = new EFireType("PROPAGATION", ImmutableSet.of(), ImmutableSet.of(BlockTypes.FIRE), ImmutableSet.of());
}
