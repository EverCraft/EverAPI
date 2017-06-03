package fr.evercraft.everapi.services.selection.worldedit.region;

import org.spongepowered.api.entity.living.player.Player;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;

public class EWPolygonalSelectionRegion extends EWSelectionRegion implements SelectionRegion.Polygonal {
	
	public EWPolygonalSelectionRegion(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector, Polygonal2DRegion region) {
		super(worldedit, player, session, selector, region);
	}	
}