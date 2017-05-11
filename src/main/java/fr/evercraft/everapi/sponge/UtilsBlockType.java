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

import java.util.Set;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

import com.google.common.collect.ImmutableSet;

public class UtilsBlockType {
	
	public static final Set<BlockType> BLOCK_COLLISION = ImmutableSet.of(
			BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE,
			BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE,
			BlockTypes.STONE_PRESSURE_PLATE,
			BlockTypes.WOODEN_PRESSURE_PLATE,
			BlockTypes.TRIPWIRE
	);
}
