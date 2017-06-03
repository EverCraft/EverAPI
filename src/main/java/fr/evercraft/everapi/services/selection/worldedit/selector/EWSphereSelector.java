package fr.evercraft.everapi.services.selection.worldedit.selector;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.selector.SphereRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.worldedit.region.EWPolygonalSelectionRegion;

public class EWSphereSelector extends EWSelector implements Selector.Ellipsoid {
	
	public EWSphereSelector(SpongeWorldEdit worldedit, Player player, LocalSession session, SphereRegionSelector selector) {
		super(worldedit, player, session, selector);
	}

	@Override
	public Optional<SelectionRegion> getRegion() {
		try {
			return Optional.of(new EWPolygonalSelectionRegion(this.worldedit, this.player, this.session, this.selector, (Polygonal2DRegion) this.selector.getRegion()));
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SelectionRegion> Optional<T> getRegion(Class<T> type) {
		try {
			if (SelectionRegion.Polygonal.class.isAssignableFrom(type)) {
				return Optional.of((T) new EWPolygonalSelectionRegion(this.worldedit, this.player, this.session, this.selector, (Polygonal2DRegion) this.selector.getRegion()));
			}
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}
}
