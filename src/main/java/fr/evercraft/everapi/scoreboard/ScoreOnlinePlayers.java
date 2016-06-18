/**
 * This file is part of EverInformations.
 *
 * EverInformations is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverInformations is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverInformations.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.essentials.event.VanishEvent;

public class ScoreOnlinePlayers extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getOnlinePlayers().size();
	}
	
	@Listener
    public void joinEvent(ClientConnectionEvent.Join event) {
		this.update(TypeScores.ONLINE_PLAYERS);
	}
	
	@Listener(order=Order.POST)
    public void quitEvent(ClientConnectionEvent.Disconnect event) {
		this.update(TypeScores.ONLINE_PLAYERS);
	}
	
	@Listener
    public void vanishEvent(VanishEvent event) {
		this.update(TypeScores.ONLINE_PLAYERS);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}