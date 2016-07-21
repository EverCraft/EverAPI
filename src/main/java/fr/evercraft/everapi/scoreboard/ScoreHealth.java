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

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.HealEntityEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreHealth extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return (int) player.getHealth();
	}
	
	@Listener
    public void event(HealEntityEvent event) {
		if(event.getTargetEntity() instanceof Player) {
			this.update(event.getTargetEntity().getUniqueId(), TypeScores.HEALTH);
		}
	}
	
	@Override
	public boolean isUpdate() {
		return false;
	}
}