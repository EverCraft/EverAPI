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