package fr.evercraft.everapi.services.selection.worldedit.selector;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.selector.CylinderRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.worldedit.region.EWCylinderSelectionRegion;

public class EWCylinderSelector extends EWSelector implements Selector.Cylinder {
	
	public EWCylinderSelector(SpongeWorldEdit worldedit, Player player, LocalSession session, CylinderRegionSelector selector) {
		super(worldedit, player, session, selector);
	}

	@Override
	public Optional<SelectionRegion> getRegion() {
		try {
			return Optional.of(new EWCylinderSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CylinderRegion) this.selector.getRegion()));
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SelectionRegion> Optional<T> getRegion(Class<T> type) {
		try {
			if (SelectionRegion.Polygonal.class.isAssignableFrom(type)) {
				return Optional.of((T) new EWCylinderSelectionRegion(this.worldedit, this.player, this.session, this.selector, (CylinderRegion) this.selector.getRegion()));
			}
		} catch (IncompleteRegionException e) {}
		return Optional.empty();
	}
}
