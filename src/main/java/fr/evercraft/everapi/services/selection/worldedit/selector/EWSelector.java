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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extension.platform.permission.ActorSelectorLimits;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.CylinderRegionSelector;
import com.sk89q.worldedit.regions.selector.EllipsoidRegionSelector;
import com.sk89q.worldedit.regions.selector.ExtendingCuboidRegionSelector;
import com.sk89q.worldedit.regions.selector.Polygonal2DRegionSelector;
import com.sk89q.worldedit.regions.selector.SphereRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionType;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.exception.NoSelectedRegionException;
import fr.evercraft.everapi.services.selection.exception.RegionOperationException;
import fr.evercraft.everapi.services.selection.exception.SelectorSecondaryException;
import fr.evercraft.everapi.services.selection.worldedit.region.EWSelectionRegion;

public abstract class EWSelector implements Selector {

	public final SpongeWorldEdit worldedit;
	
	public final Player player;
	public final LocalSession session;
	public final RegionSelector selector;
	
	public EWSelector(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector) {
		this.worldedit = worldedit;
		this.player = player;
		this.session = session;
		this.selector = selector;
	}

	@Override
	public boolean selectPrimary(Vector3i position) {
		return this.selector.selectPrimary(Vector.toBlockPoint(position.getX(), position.getY(), position.getZ()), 
				ActorSelectorLimits.forActor(this.worldedit.wrapPlayer(this.player)));
	}

	@Override
	public boolean selectSecondary(Vector3i position) throws SelectorSecondaryException {
		return this.selector.selectSecondary(Vector.toBlockPoint(position.getX(), position.getY(), position.getZ()), 
				ActorSelectorLimits.forActor(this.worldedit.wrapPlayer(this.player)));
	}

	@Override
	public boolean clear() {
		this.selector.clear();
		return true;
	}

	@Override
	public int getVolume() {
		return this.selector.getArea();
	}

	@Override
	public boolean expand(Vector3i... changes) throws RegionOperationException, NoSelectedRegionException {
		try {
			return (new EWSelectionRegion(this.worldedit, this.player, this.session, this.selector, this.selector.getRegion())).expand(changes);
		} catch (IncompleteRegionException e) {
			throw new NoSelectedRegionException(e.getMessage());
		}
	}

	@Override
	public boolean contract(Vector3i... changes) throws RegionOperationException, NoSelectedRegionException {
		try {
			return (new EWSelectionRegion(this.worldedit, this.player, this.session, this.selector, this.selector.getRegion())).contract(changes);
		} catch (IncompleteRegionException e) {
			throw new NoSelectedRegionException(e.getMessage());
		}
	}

	@Override
	public boolean shift(Vector3i change) throws NoSelectedRegionException {
		try {
			return (new EWSelectionRegion(this.worldedit, this.player, this.session, this.selector, this.selector.getRegion())).shift(change);
		} catch (IncompleteRegionException e) {
			throw new NoSelectedRegionException(e.getMessage());
		}
	}

	@Override
	public Optional<Vector3i> getPrimaryPosition() {
		try {
			BlockVector vector = this.selector.getPrimaryPosition();
			return Optional.of(Vector3i.from(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()));
		} catch (IncompleteRegionException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Vector3i> getPositions() {
		try {
			return (new EWSelectionRegion(this.worldedit, this.player, this.session, this.selector, this.selector.getRegion())).getPositions();
		} catch (IncompleteRegionException e) {
			Optional<Vector3i> vector = getPrimaryPosition();
			if (vector.isPresent()) {
				return Arrays.asList(vector.get());
			}
			return Arrays.asList();
		}
	}

	@Override
	public SelectionType getType() {
		if (this.selector instanceof ExtendingCuboidRegionSelector) {
			return SelectionType.EXTEND;
		} else if (this.selector instanceof Polygonal2DRegionSelector) {
			return SelectionType.POLYGONAL;
		} else if (this.selector instanceof EllipsoidRegionSelector) {
			return SelectionType.ELLIPSOID;
		} else if (this.selector instanceof SphereRegionSelector) {
			return SelectionType.SPHERE;
		} else if (this.selector instanceof CylinderRegionSelector) {
			return SelectionType.CYLINDER;
		}
		return SelectionType.CUBOID;
	}

}
