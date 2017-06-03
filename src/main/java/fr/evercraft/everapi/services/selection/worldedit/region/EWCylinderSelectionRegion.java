package fr.evercraft.everapi.services.selection.worldedit.region;

import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3d;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;

public class EWCylinderSelectionRegion extends EWSelectionRegion implements SelectionRegion.Cylinder {
	
	public EWCylinderSelectionRegion(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector, CylinderRegion region) {
		super(worldedit, player, session, selector, region);
	}

	@Override
	public Vector3d getRadius() {
		Vector2D vector = ((CylinderRegion) this.region).getRadius();
		return Vector3d.from(vector.getX(), this.region.getHeight(), vector.getZ());
	}

	@Override
	public int getMinimumY() {
		return ((CylinderRegion) this.region).getMinimumY();
	}

	@Override
	public int getMaximumY() {
		return ((CylinderRegion) this.region).getMaximumY();
	}	
}