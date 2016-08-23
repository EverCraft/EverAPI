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

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Platform.Type;
import org.spongepowered.api.network.ChannelBuf;
import org.spongepowered.api.network.RawDataListener;
import org.spongepowered.api.network.RemoteConnection;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;

public class MessageListener implements RawDataListener {

	private EverAPI plugin;

	public MessageListener(EverAPI plugin) {
		this.plugin = plugin;
	}

	@Override
	public void handlePayload(ChannelBuf data, RemoteConnection connection, Type side) {
		String subChannel = data.readString();

	    if (subChannel.equals("EPlayer_SET_MUTE")) {
	        UUID uuid = data.readUniqueId();
	        Boolean mute = data.readBoolean();
	        if (uuid != null && mute != null){
	        	Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
	        	if (player.isPresent()){
	        		//player.get().setMute(mute);
	        	}
	        }
	    } else if (subChannel.equals("EPlayer_ADD_IGNORE")) {
	    	UUID uuid = data.readUniqueId();
	        if (uuid != null){
	        	Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
	        	if (player.isPresent()){
	        		//player.get().addIgnore(data.readUniqueId());
	        	}
	        }
	    } else if (subChannel.equals("EPlayer_REMOVE_IGNORE")) {
	    	UUID uuid = data.readUniqueId();
	        if (uuid != null){
	        	Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
	        	if (player.isPresent()){
	        		//player.get().removeIgnore(data.readUniqueId());
	        	}
	        }
	    }
	}

}
