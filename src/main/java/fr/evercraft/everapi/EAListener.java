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
package fr.evercraft.everapi;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;

public class EAListener {
	private final EverAPI plugin;
	
	public EAListener(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	@Listener(order=Order.FIRST)
	public void onPlayerJoin(final ClientConnectionEvent.Join event) {		
		EPlayer player = this.plugin.getEServer().getEPlayer(event.getTargetEntity());
		
		// Corrige bug
		player.sendTitle(Title.CLEAR);
		player.getTabList().setHeaderAndFooter(null, null);
		
		this.plugin.getManagerService().getEScoreBoard().addPlayer(player);
	}
	
	@Listener(order=Order.PRE)
	public void onPlayerDisconnectPre(final ClientConnectionEvent.Disconnect event) {
		this.plugin.getEServer().disconnects.add(event.getTargetEntity().getUniqueId());
	}
	
	@Listener(order=Order.POST)
	public void onPlayerDisconnectPost(final ClientConnectionEvent.Disconnect event) {
		this.plugin.getEServer().disconnects.add(event.getTargetEntity().getUniqueId());
	}
}
