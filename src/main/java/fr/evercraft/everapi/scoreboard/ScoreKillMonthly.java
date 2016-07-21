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
package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.event.Listener;

import fr.evercraft.everapi.event.StatsUserEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreKillMonthly extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getKillMonthly();
	}
	
	@Listener
    public void event(StatsUserEvent.Kill event) {
		this.update(event.getKiller().getUniqueId(), TypeScores.KILLS);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}