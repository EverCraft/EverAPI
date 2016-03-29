/**
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
package fr.evercraft.everapi.server.player;


import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.entity.living.player.Player;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.cooldown.CooldownSubject;

public class PlayerCooldown extends PlayerPermission implements CooldownSubject {
	
	private CooldownSubject subject;

	public PlayerCooldown(EverAPI plugin, Player player) {
		super(plugin, player);
	}

	private boolean isPresent() {
		if(this.subject == null && this.plugin.getManagerService().getCooldown().isPresent()) {
			this.subject = this.plugin.getManagerService().getCooldown().get().get(this.player.getIdentifier());
		}
		return this.subject != null;
	}

	@Override
	public Map<String, Long> getCooldown() {
		if(this.isPresent()) {
			return this.subject.getCooldown();
		}
		return new HashMap<String, Long>();
	}

	@Override
	public boolean addCooldown(String identifier) {
		if(this.isPresent()) {
			return this.subject.addCooldown(identifier);
		}
		return false;
	}

	@Override
	public boolean addCooldownScheduler(String identifier) {
		if(this.isPresent()) {
			return this.subject.addCooldownScheduler(identifier);
		}
		return false;
	}

	@Override
	public boolean removeCooldown(String identifier) {
		if(this.isPresent()) {
			return this.subject.removeCooldown(identifier);
		}
		return false;
	}

	@Override
	public long getCooldownTime(String identifier) {
		if(this.isPresent()) {
			return this.subject.getCooldownTime(identifier);
		}
		return 0;
	}
	
	
}
