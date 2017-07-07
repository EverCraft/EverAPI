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
package fr.evercraft.everapi.services.score;

import org.spongepowered.api.event.Listener;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.StatsUserEvent;
import fr.evercraft.everapi.registers.ScoreType;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreDeathMonthly extends ScoreType {

	public ScoreDeathMonthly(String name, EverAPI plugin) {
		super(name, plugin);
	}
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getDeathMonthly();
	}
	
	@Listener
    public void event(StatsUserEvent.Death event) {
		this.update(event.getVictim().getUniqueId(), ScoreTypes.DEATHS_MONTHLY);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}