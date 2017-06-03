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

import com.flowpowered.math.vector.Vector3d;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;

public class EWEllipsoidSelectionRegion extends EWSelectionRegion implements SelectionRegion.Ellipsoid {
	
	public EWEllipsoidSelectionRegion(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector, EllipsoidRegion region) {
		super(worldedit, player, session, selector, region);
	}

	@Override
	public Vector3d getRadius() {
		Vector vector = ((EllipsoidRegion) this.region).getRadius();
		return Vector3d.from(vector.getX(), vector.getY(), vector.getZ());
	}
	
}