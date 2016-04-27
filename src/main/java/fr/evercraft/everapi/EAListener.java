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
package fr.evercraft.everapi;

import java.util.Optional;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class EAListener {
	private final EverAPI plugin;
	
	public EAListener(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	@Listener(order=Order.FIRST)
	public void onPlayerJoin(final ClientConnectionEvent.Join event) {
		Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(event.getTargetEntity());
		if(player.isPresent()) {
			this.plugin.getManagerService().getEScoreBoard().addPlayer(player.get());
		}
	}
	
	@Listener(order=Order.POST)
	public void onPlayerDisconnect(final ClientConnectionEvent.Disconnect event) {
		this.plugin.getEServer().removeEPlayer(event.getTargetEntity());
	}
}
