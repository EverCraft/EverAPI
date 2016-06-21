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
