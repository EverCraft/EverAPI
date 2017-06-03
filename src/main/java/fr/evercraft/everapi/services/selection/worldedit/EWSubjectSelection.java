package fr.evercraft.everapi.services.selection.worldedit;

import org.spongepowered.api.entity.living.player.Player;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import com.sk89q.worldedit.regions.selector.CylinderRegionSelector;
import com.sk89q.worldedit.regions.selector.EllipsoidRegionSelector;
import com.sk89q.worldedit.regions.selector.ExtendingCuboidRegionSelector;
import com.sk89q.worldedit.regions.selector.Polygonal2DRegionSelector;
import com.sk89q.worldedit.regions.selector.SphereRegionSelector;
import com.sk89q.worldedit.sponge.SpongeWorld;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;
import fr.evercraft.everapi.services.selection.SelectionType;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.SubjectSelection;
import fr.evercraft.everapi.services.selection.worldedit.selector.EWCuboidSelector;
import fr.evercraft.everapi.services.selection.worldedit.selector.EWCylinderSelector;
import fr.evercraft.everapi.services.selection.worldedit.selector.EWEllipsoidSelector;
import fr.evercraft.everapi.services.selection.worldedit.selector.EWExtendSelector;
import fr.evercraft.everapi.services.selection.worldedit.selector.EWPolygonalSelector;
import fr.evercraft.everapi.services.selection.worldedit.selector.EWSphereSelector;

public class EWSubjectSelection implements SubjectSelection {

	public final SpongeWorldEdit worldedit;
	
	public final Player player;
	public final LocalSession session;
	
	public EWSubjectSelection(SpongeWorldEdit worldedit, Player player, LocalSession session) {
		this.worldedit = worldedit;
		this.player = player;
		this.session = session;
	}

	@Override
	public Selector getSelector() {
		SpongeWorld world = this.worldedit.getWorld(player.getWorld());
		RegionSelector selector = this.session.getRegionSelector(world);
		
		if (selector instanceof CuboidRegionSelector) {
			return new EWCuboidSelector(this.worldedit, this.player, this.session, (CuboidRegionSelector) this.session.getRegionSelector(world));
        } else if (selector instanceof ExtendingCuboidRegionSelector) {
        	return new EWExtendSelector(this.worldedit, this.player, this.session, (ExtendingCuboidRegionSelector) this.session.getRegionSelector(world));
        } else if (selector instanceof Polygonal2DRegionSelector) {
        	return new EWPolygonalSelector(this.worldedit, this.player, this.session, (Polygonal2DRegionSelector) this.session.getRegionSelector(world));
        } else if (selector instanceof EllipsoidRegionSelector) {
        	return new EWEllipsoidSelector(this.worldedit, this.player, this.session, (EllipsoidRegionSelector) this.session.getRegionSelector(world));
        } else if (selector instanceof SphereRegionSelector) {
        	return new EWSphereSelector(this.worldedit, this.player, this.session, (SphereRegionSelector) this.session.getRegionSelector(world));
        } else if (selector instanceof CylinderRegionSelector) {
        	return new EWCylinderSelector(this.worldedit, this.player, this.session, (CylinderRegionSelector) this.session.getRegionSelector(world));
        }
		return null;
	}

	@Override
	public void setType(SelectionType type) {
		SpongeWorld world = this.worldedit.getWorld(player.getWorld());
		if (world == null) return;
		
        RegionSelector oldSelector = this.session.getRegionSelector(world);
        RegionSelector selector = null;
        
        if (type.equals(SelectionType.CUBOID)) {
            selector = new CuboidRegionSelector(oldSelector);
        } else if (type.equals(SelectionType.EXTEND)) {
            selector = new ExtendingCuboidRegionSelector(oldSelector);
        } else if (type.equals(SelectionType.POLYGONAL)) {
            selector = new Polygonal2DRegionSelector(oldSelector);
        } else if (type.equals(SelectionType.ELLIPSOID)) {
            selector = new EllipsoidRegionSelector(oldSelector);
        } else if (type.equals(SelectionType.SPHERE)) {
            selector = new SphereRegionSelector(oldSelector);
        } else if (type.equals(SelectionType.CYLINDER)) {
            selector = new CylinderRegionSelector(oldSelector);
        }

        this.session.setRegionSelector(world, selector);
        this.session.dispatchCUISelection(this.worldedit.wrapPlayer(this.player));
	}

	@Override
	public SelectionType getType() {
		SpongeWorld world = this.worldedit.getWorld(player.getWorld());
		if (world == null) return SelectionType.CUBOID;
		
        RegionSelector selector = this.session.getRegionSelector(world);
		if (selector instanceof ExtendingCuboidRegionSelector) {
			return SelectionType.EXTEND;
		} else if (selector instanceof Polygonal2DRegionSelector) {
			return SelectionType.POLYGONAL;
		} else if (selector instanceof EllipsoidRegionSelector) {
			return SelectionType.ELLIPSOID;
		} else if (selector instanceof SphereRegionSelector) {
			return SelectionType.SPHERE;
		} else if (selector instanceof CylinderRegionSelector) {
			return SelectionType.CYLINDER;
		}
		return SelectionType.CUBOID;
	}

	@Override
	public boolean isCuiSupport() {
		return this.session.hasCUISupport();
	}

	@Override
	public int getCUIVersion() {
		return this.session.getCUIVersion();
	}

}
