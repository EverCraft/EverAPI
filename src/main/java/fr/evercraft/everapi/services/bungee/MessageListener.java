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

	    if(subChannel.equals("EPlayer_SET_MUTE")) {
	        UUID uuid = data.readUniqueId();
	        Boolean mute = data.readBoolean();
	        if(uuid != null && mute != null){
	        	Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
	        	if(player.isPresent()){
	        		//player.get().setMute(mute);
	        	}
	        }
	    } else if(subChannel.equals("EPlayer_ADD_IGNORE")) {
	    	UUID uuid = data.readUniqueId();
	        if(uuid != null){
	        	Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
	        	if(player.isPresent()){
	        		//player.get().addIgnore(data.readUniqueId());
	        	}
	        }
	    } else if(subChannel.equals("EPlayer_REMOVE_IGNORE")) {
	    	UUID uuid = data.readUniqueId();
	        if(uuid != null){
	        	Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
	        	if(player.isPresent()){
	        		//player.get().removeIgnore(data.readUniqueId());
	        	}
	        }
	    }
	}

}
