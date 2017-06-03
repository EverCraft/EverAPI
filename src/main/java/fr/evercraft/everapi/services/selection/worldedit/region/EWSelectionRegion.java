package fr.evercraft.everapi.services.selection.worldedit.region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.CylinderRegionSelector;
import com.sk89q.worldedit.regions.selector.EllipsoidRegionSelector;
import com.sk89q.worldedit.regions.selector.ExtendingCuboidRegionSelector;
import com.sk89q.worldedit.regions.selector.Polygonal2DRegionSelector;
import com.sk89q.worldedit.regions.selector.SphereRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorld;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.services.selection.SelectionRegion;
import fr.evercraft.everapi.services.selection.SelectionType;
import fr.evercraft.everapi.services.selection.exception.RegionOperationException;

public class EWSelectionRegion implements SelectionRegion {
	
	public final SpongeWorldEdit worldedit;

	public final Player player;
	public final LocalSession session;
	public final RegionSelector selector;
	public final Region region;
	
	public EWSelectionRegion(SpongeWorldEdit worldedit, Player player, LocalSession session, RegionSelector selector, Region region) {
		this.worldedit = worldedit;
		this.player = player;
		this.session = session;
		this.selector = selector;
		this.region = region;
	}
	
	@Override
	public Optional<World> getWorld() {
		return Optional.ofNullable(((SpongeWorld) this.region.getWorld()).getWorld());
	}

	@Override
	public void setWorld(World world) {
		this.region.setWorld(this.worldedit.getWorld(world));
	}

	@Override
	public Vector3i getPrimaryPosition() {
		try {
			Vector vector = this.selector.getPrimaryPosition();
			return Vector3i.from(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
		} catch (IncompleteRegionException e) {
			return this.getMinimumPoint();
		}
	}

	@Override
	public List<Vector3i> getPositions() {
		if (this.region instanceof Polygonal2DRegion) {
			int min = this.region.getMinimumPoint().getBlockY();
			int max = this.region.getMaximumPoint().getBlockY();
			
			List<Vector3i> vectors = new ArrayList<Vector3i>();
			for (BlockVector2D vector : this.region.polygonize(((Polygonal2DRegion) this.region).size())) {
				vectors.add(Vector3i.from(vector.getBlockX(), min, vector.getBlockZ()));
			}
			
			Vector3i last = vectors.get(vectors.size());
			vectors.set(vectors.size()-1, Vector3i.from(last.getX(), max, last.getZ()));
			return vectors;
		}
		
		return Arrays.asList(this.getMinimumPoint(), this.getMaximumPoint());
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

	@Override
	public Vector3i getMinimumPoint() {
		Vector min = this.region.getMinimumPoint();
		return Vector3i.from(min.getBlockX(), min.getBlockY(), min.getBlockZ());
	}

	@Override
	public Vector3i getMaximumPoint() {
		Vector max = this.region.getMaximumPoint();
		return Vector3i.from(max.getBlockX(), max.getBlockY(), max.getBlockZ());
	}

	@Override
	public Vector3i getCenter() {
		Vector center = this.region.getCenter();
		return Vector3i.from(center.getBlockX(), center.getBlockY(), center.getBlockZ());
	}

	@Override
	public int getArea() {
		return this.region.getArea();
	}

	@Override
	public int getWidth() {
		return this.region.getWidth();
	}

	@Override
	public int getHeight() {
		return this.region.getHeight();
	}

	@Override
	public int getLength() {
		return this.region.getLength();
	}

	@Override
	public boolean expand(Vector3i... changes) throws RegionOperationException {
		Vector[] vectors = new Vector[changes.length];
		for (int cpt = 0; cpt < changes.length; cpt++) {
			vectors[cpt] = Vector.toBlockPoint(changes[cpt].getX(), changes[cpt].getY(), changes[cpt].getZ());
		}
		
		try {
			this.region.expand(vectors);
			return true;
		} catch (com.sk89q.worldedit.regions.RegionOperationException e) {
			throw new RegionOperationException(e.getMessage());
		}
	}

	@Override
	public boolean contract(Vector3i... changes) throws RegionOperationException {
		Vector[] vectors = new Vector[changes.length];
		for (int cpt = 0; cpt < changes.length; cpt++) {
			vectors[cpt] = Vector.toBlockPoint(changes[cpt].getX(), changes[cpt].getY(), changes[cpt].getZ());
		}
		
		try {
			this.region.contract(vectors);
			return true;
		} catch (com.sk89q.worldedit.regions.RegionOperationException e) {
			throw new RegionOperationException(e.getMessage());
		}
	}

	@Override
	public boolean shift(Vector3i change) {
		Vector vectors = Vector.toBlockPoint(change.getX(), change.getY(), change.getZ());
		try {
			this.region.shift(vectors);
			return true;
		} catch (com.sk89q.worldedit.regions.RegionOperationException e) {
			return false;
		}
	}

	@Override
	public boolean containsPosition(Vector3i position) {
		return this.region.contains(Vector.toBlockPoint(position.getX(), position.getY(), position.getZ()));
	}
}
