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
package fr.evercraft.everapi.services.bungee;

import org.spongepowered.api.Platform;
import org.spongepowered.api.network.ChannelBinding.RawDataChannel;
import fr.evercraft.everapi.EverAPI;

public class BungeeCord {
	private EverAPI plugin;
	
	private RawDataChannel channel;
	
	public BungeeCord(EverAPI plugin){
		this.plugin = plugin;
		
		this.channel = this.plugin.getGame().getChannelRegistrar().createRawChannel(this.plugin, "BungeeCord");
		this.channel.addListener(Platform.Type.SERVER, new MessageListener(this.plugin));
		
		/*Player player = null;
		channel.sendTo(player, buf -> {
		    buf.writeString("PlayerCount");
		    buf.writeString("someServer");
		});*/
	}
}
