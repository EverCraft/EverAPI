/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
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

import fr.evercraft.everapi.services.selection.SelectionRegion;
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
	public void setType(SelectionRegion.Type type) {
		SpongeWorld world = this.worldedit.getWorld(player.getWorld());
		if (world == null) return;
		
        RegionSelector oldSelector = this.session.getRegionSelector(world);
        RegionSelector selector = null;
        
        if (type.equals(SelectionRegion.Types.CUBOID)) {
            selector = new CuboidRegionSelector(oldSelector);
        } else if (type.equals(SelectionRegion.Types.EXTEND)) {
            selector = new ExtendingCuboidRegionSelector(oldSelector);
        } else if (type.equals(SelectionRegion.Types.POLYGONAL)) {
            selector = new Polygonal2DRegionSelector(oldSelector);
        } else if (type.equals(SelectionRegion.Types.ELLIPSOID)) {
            selector = new EllipsoidRegionSelector(oldSelector);
        } else if (type.equals(SelectionRegion.Types.SPHERE)) {
            selector = new SphereRegionSelector(oldSelector);
        } else if (type.equals(SelectionRegion.Types.CYLINDER)) {
            selector = new CylinderRegionSelector(oldSelector);
        }

        this.session.setRegionSelector(world, selector);
        this.session.dispatchCUISelection(this.worldedit.wrapPlayer(this.player));
	}

	@Override
	public SelectionRegion.Type getType() {
		SpongeWorld world = this.worldedit.getWorld(player.getWorld());
		if (world == null) return SelectionRegion.Types.CUBOID;
		
        RegionSelector selector = this.session.getRegionSelector(world);
		if (selector instanceof ExtendingCuboidRegionSelector) {
			return SelectionRegion.Types.EXTEND;
		} else if (selector instanceof Polygonal2DRegionSelector) {
			return SelectionRegion.Types.POLYGONAL;
		} else if (selector instanceof EllipsoidRegionSelector) {
			return SelectionRegion.Types.ELLIPSOID;
		} else if (selector instanceof SphereRegionSelector) {
			return SelectionRegion.Types.SPHERE;
		} else if (selector instanceof CylinderRegionSelector) {
			return SelectionRegion.Types.CYLINDER;
		}
		return SelectionRegion.Types.CUBOID;
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
