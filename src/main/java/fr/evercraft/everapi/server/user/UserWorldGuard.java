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

import org.spongepowered.api.entity.living.player.User;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.worldguard.WorldGuardSubject;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public class UserWorldGuard extends UserStats {
	
	private WorldGuardSubject subject;

	public UserWorldGuard(EverAPI plugin, User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getWorldGuard().isPresent()) {
			this.subject = this.plugin.getManagerService().getWorldGuard().get().get(this.user.getUniqueId()).get();
		}
		return this.subject != null;
	}
	
	/*
	 * Bypass
	 */
	
	public boolean hasProtectionBypass() {
		if (this.isPresent()) {
			return this.subject.hasBypass();
		}
		return false;
	}
	
	public boolean setProtectionBypass(boolean bypass) {
		if (this.isPresent()) {
			this.subject.setBypass(bypass);
			return true;
		}
		return false;
	}
	
	/*
	 * Region
	 */
	
	public SetProtectedRegion getRegions() {
		if (this.isPresent()) {
			return this.subject.getRegions();
		}
		return SetProtectedRegion.empty();
	}
}
