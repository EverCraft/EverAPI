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
package fr.evercraft.everapi.services.selection.worldedit.region;

import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;

public class EWCuboidSelectionRegion extends EWSelectionRegion implements SelectionRegion.Cuboid {
	
	public EWCuboidSelectionRegion(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector, CuboidRegion region) {
		super(worldedit, player, session, selector, region);
	}
	
	@Override
	public Vector3i getSecondaryPosition() {
		Vector vector = ((CuboidRegion) this.region).getPos2();
		return Vector3i.from(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
	}
	
}