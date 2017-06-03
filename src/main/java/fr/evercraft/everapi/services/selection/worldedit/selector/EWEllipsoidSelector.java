package fr.evercraft.everapi.services.selection.worldedit.selector;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.selector.EllipsoidRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.worldedit.region.EWEllipsoidSelectionRegion;

public class EWEllipsoidSelector extends EWSelector implements Selector.Ellipsoid {
	
	public EWEllipsoidSelector(SpongeWorldEdit worldedit, Player player, LocalSession session, EllipsoidRegionSelector selector) {
		super(worldedit, player, session, selector);
	}

	@Override
	public Optional<SelectionRegion> getRegion() {
		try {
			return Optional.of(new EWEllipsoidSelectionRegion(this.worldedit, this.player, this.session, this.selector, (EllipsoidRegion) this.selector.getRegion()));
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SelectionRegion> Optional<T> getRegion(Class<T> type) {
		try {
			if (SelectionRegion.Polygonal.class.isAssignableFrom(type)) {
				return Optional.of((T) new EWEllipsoidSelectionRegion(this.worldedit, this.player, this.session, this.selector, (EllipsoidRegion) this.selector.getRegion()));
			}
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}
}
