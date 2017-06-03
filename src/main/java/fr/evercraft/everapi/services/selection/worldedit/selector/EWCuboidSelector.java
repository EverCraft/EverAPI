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
package fr.evercraft.everapi.services.selection.worldedit.selector;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import com.sk89q.worldedit.regions.selector.ExtendingCuboidRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.worldedit.region.EWCuboidSelectionRegion;
import fr.evercraft.everapi.services.selection.worldedit.region.EWExtendSelectionRegion;

public class EWCuboidSelector extends EWSelector implements Selector.Cuboid {
	
	public EWCuboidSelector(SpongeWorldEdit worldedit, Player player, LocalSession session, CuboidRegionSelector selector) {
		super(worldedit, player, session, selector);
	}

	@Override
	public Optional<SelectionRegion> getRegion() {
		try {
			if (this.selector instanceof ExtendingCuboidRegionSelector) {
				return Optional.of(new EWExtendSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CuboidRegion) this.selector.getRegion()));
			} else if (this.selector instanceof CuboidRegionSelector) {
				return Optional.of(new EWCuboidSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CuboidRegion) this.selector.getRegion()));
			}
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SelectionRegion> Optional<T> getRegion(Class<T> type) {
		try {
			if (this.selector instanceof ExtendingCuboidRegionSelector && SelectionRegion.Extend.class.isAssignableFrom(type)) {
				return Optional.of((T) new EWExtendSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CuboidRegion) this.selector.getRegion()));
			} else if (this.selector instanceof CuboidRegionSelector && SelectionRegion.Cuboid.class.isAssignableFrom(type)) {
				return Optional.of((T) new EWCuboidSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CuboidRegion) this.selector.getRegion()));
			}
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}

	@Override
	public Optional<Vector3i> getSecondaryPosition() {
		try {
			return Optional.of((new EWCuboidSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CuboidRegion) this.selector.getRegion())).getSecondaryPosition());
		} catch (IncompleteRegionException e) {
			return Optional.empty();
		}
	}

}
