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
