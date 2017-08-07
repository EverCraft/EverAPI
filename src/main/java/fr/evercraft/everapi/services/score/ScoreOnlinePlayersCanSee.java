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
import org.spongepowered.api.event.network.ClientConnectionEvent;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.VanishEvent;
import fr.evercraft.everapi.registers.ScoreType;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreOnlinePlayersCanSee extends ScoreType {

	public ScoreOnlinePlayersCanSee(String name, EverAPI plugin) {
		super(name, plugin);
	}
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getOnlinePlayers().size();
	}
	
	@Listener
    public void joinEvent(ClientConnectionEvent.Join event) {
		this.update(ScoreTypes.ONLINE_PLAYERS_CANSEE);
	}
	
	@Listener
    public void quitEvent(ClientConnectionEvent.Disconnect event) {
		this.update(ScoreTypes.ONLINE_PLAYERS_CANSEE);
	}
	
	@Listener
    public void vanishEvent(VanishEvent event) {
		this.update(ScoreTypes.ONLINE_PLAYERS_CANSEE);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}
