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
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;

public class EAListener {
	private final EverAPI plugin;
	
	public EAListener(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	@Listener(order=Order.FIRST)
	public void onPlayerJoin(final ClientConnectionEvent.Login event) {
		EUser user = this.plugin.getEServer().getEUser(event.getTargetUser());
		
		// Newbie
		if (user.getFirstDatePlayed() == user.getLastDatePlayed()) {			
			user.setSpawnNewbie(true);
			event.setToTransform(user.getSpawn());
		}
	}
	
	
	@Listener(order=Order.FIRST)
	public void onPlayerJoin(final ClientConnectionEvent.Join event) {		
		EPlayer player = this.plugin.getEServer().getEPlayer(event.getTargetEntity());
		
		// Corrige bug
		player.sendTitle(Title.CLEAR);
		player.getTabList().setHeaderAndFooter(null, null);
		
		this.plugin.getManagerService().getEScoreBoard().addPlayer(player);

		// Newbie
		if (player.getFirstDatePlayed() == player.getLastDatePlayed()) {
			player.setSpawnNewbie(true);
			player.setTransform(player.getSpawn());
		}
	}
	
	@Listener(order=Order.PRE)
	public void onPlayerDisconnectPre(final ClientConnectionEvent.Disconnect event) {
		this.plugin.getEServer().disconnects.add(event.getTargetEntity().getUniqueId());
	}
	
	@Listener(order=Order.POST)
	public void onPlayerDisconnectPost(final ClientConnectionEvent.Disconnect event) {
		this.plugin.getGame().getScheduler().createTaskBuilder().execute(() ->
			this.plugin.getEServer().disconnects.remove(event.getTargetEntity().getUniqueId())
		).submit(this.plugin);
	}
	
	@Listener(order=Order.FIRST)
	public void onRespawnPlayer(final RespawnPlayerEvent event) {
		event.setToTransform(this.plugin.getEServer().getEPlayer(event.getOriginalPlayer()).getSpawn());
	}
}
