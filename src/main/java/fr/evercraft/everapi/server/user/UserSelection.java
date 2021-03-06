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
package fr.evercraft.everapi.server.user;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;

import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.selection.SelectionRegion;
import fr.evercraft.everapi.services.selection.Selector;
import fr.evercraft.everapi.services.selection.SubjectSelection;
import fr.evercraft.everapi.services.selection.exception.NoSelectedRegionException;
import fr.evercraft.everapi.services.selection.exception.RegionOperationException;
import fr.evercraft.everapi.services.selection.exception.SelectorMaxPointsException;
import fr.evercraft.everapi.services.selection.exception.SelectorSecondaryException;

public class UserSelection extends UserWorldGuard {
	
	private SubjectSelection subject;

	public UserSelection(EverAPI plugin, User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getSelection().isPresent()) {
			this.subject = this.plugin.getManagerService().getSelection().get().get(this.user.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}

	public Selector getSelector() {
		if (this.isPresent()) {
			return this.subject.getSelector();
		}
		return Selector.empty();
	}
	
	public boolean setSelectorType(SelectionRegion.Type type) {
		if (this.isPresent()) {
			this.subject.setType(type);
			return true;
		}
		return false;
	}

	public SelectionRegion.Type getSelectorType() {
		if (this.isPresent()) {
			return this.subject.getType();
		}
		return SelectionRegion.Types.CUBOID;
	}
	
	public boolean setSelectorPrimary(Vector3i position) {
		return this.getSelector().selectPrimary(position);
	} 
	
	public boolean setSelectorSecondary(Vector3i position) throws SelectorSecondaryException, SelectorMaxPointsException {
		return this.getSelector().selectSecondary(position);
	}
	
	public Optional<Vector3i> getSelectorPrimary() {
		return this.getSelector().getPrimaryPosition();
	}
	
	public Optional<Vector3i> getSelectorSecondary() {
		Selector selector = this.getSelector();
		if (selector instanceof Selector.Cuboid) {
			return ((Selector.Cuboid) selector).getSecondaryPosition();
		}
		return Optional.empty();
	}
	
	public List<Vector3i> getSelectorPositions() {
		return this.getSelector().getPositions();
	}
	
	public boolean expandSelector(Vector3i... changes) throws RegionOperationException, NoSelectedRegionException {
		return this.getSelector().expand(changes);
	}
	
	public boolean contractSelector(Vector3i... changes) throws RegionOperationException, NoSelectedRegionException {
		return this.getSelector().contract(changes);
	}
	
	public boolean shiftSelector(Vector3i change) throws NoSelectedRegionException  {
		return this.getSelector().shift(change);
	}
	
	public boolean clearSelector() {
		return this.getSelector().clear();
	}
	
	public int getSelectorVolume() {
		return this.getSelector().getVolume();
	}
	
	public Optional<SelectionRegion> getSelectorRegion() {
		return this.getSelector().getRegion();
	}
	
	public <T extends SelectionRegion> Optional<T> getSelectorRegion(Class<T> type) {
		return this.getSelector().getRegion(type);
	}
	
	public boolean isCuiSupport() {
		if (this.isPresent()) {
			this.subject.isCuiSupport();
			return true;
		}
		return false;
	}	
}
