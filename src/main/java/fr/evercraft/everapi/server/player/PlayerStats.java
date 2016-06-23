/*
 * EverAPI
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

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.DamageType;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.StatsSubject;

public class PlayerStats extends PlayerEssentials implements StatsSubject {
	
	private StatsSubject subject;

	public PlayerStats(EverAPI plugin, Player player) {
		super(plugin, player);
	}

	private boolean isPresent() {
		if(this.subject == null && this.plugin.getManagerService().getStats().isPresent()) {
			this.subject = this.plugin.getManagerService().getStats().get().get(this.player.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}

	@Override
	public int getDeath() {
		if(this.isPresent()) {
			return this.subject.getDeath();
		}
		return 0;
	}

	@Override
	public int getKill() {
		if(this.isPresent()) {
			return this.subject.getKill();
		}
		return 0;
	}
	
	@Override
	public int getKillStreaks() {
		if(this.isPresent()) {
			return this.subject.getKillStreaks();
		}
		return 0;
	}

	@Override
	public int getRatio() {
		if(this.isPresent()) {
			return this.subject.getRatio();
		}
		return 0;
	}

	@Override
	public int getDeathMonthly() {
		if(this.isPresent()) {
			return this.subject.getDeathMonthly();
		}
		return 0;
	}

	@Override
	public int getKillMonthly() {
		if(this.isPresent()) {
			return this.subject.getKillMonthly();
		}
		return 0;
	}

	@Override
	public int getRatioMonthly() {
		if(this.isPresent()) {
			return this.subject.getRatioMonthly();
		}
		return 0;
	}

	@Override
	public boolean addDeath(Entity killer, DamageType damage, Long time) {
		if(this.isPresent()) {
			return this.subject.addDeath(killer, damage, time);
		}
		return false;
	}
	
	public boolean addDeath(DamageType damage, Long time) {
		if(this.isPresent()) {
			return this.subject.addDeath(null, damage, time);
		}
		return false;
	}
}
