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


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.cooldown.CooldownsSubject;

public class UserCooldown extends UserSanction {
	
	private CooldownsSubject subject;

	public UserCooldown(EverAPI plugin, User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getCooldown().isPresent()) {
			this.subject = this.plugin.getManagerService().getCooldown().get().get(this.user.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}

	public Map<String, Long> getCooldowns() {
		if (this.isPresent()) {
			return this.subject.getAll();
		}
		return new HashMap<String, Long>();
	}

	public boolean addCooldown(final String command) {
		if (this.isPresent()) {
			return this.subject.add(this.user, command);
		}
		return false;
	}

	public boolean removeCooldown(final String command) {
		if (this.isPresent()) {
			return this.subject.remove(command);
		}
		return false;
	}
	
	public boolean clearCooldown() {
		if (this.isPresent()) {
			return this.subject.clear();
		}
		return false;
	}

	public Optional<Long> getCooldown(final String command) {
		if (this.isPresent()) {
			return this.subject.get(command);
		}
		return Optional.empty();
	}
}
