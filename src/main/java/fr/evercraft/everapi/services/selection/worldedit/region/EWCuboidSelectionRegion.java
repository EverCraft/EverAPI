package fr.evercraft.everapi.services.selection.worldedit.region;

import org.spongepowered.api.entity.living.player.Player;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;

public class EWCuboidSelectionRegion extends EWSelectionRegion implements SelectionRegion.Cuboid {
	
	public EWCuboidSelectionRegion(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector, CuboidRegion region) {
		super(worldedit, player, session, selector, region);
	}
	
	@Override
	public Vector3i getSecondaryPosition() {
		Vector vector = ((CuboidRegion) this.region).getPos2();
		return Vector3i.from(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
	}
	
}