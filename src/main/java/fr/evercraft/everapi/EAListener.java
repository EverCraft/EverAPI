package fr.evercraft.everapi;

import java.util.Optional;

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
		Optional<EPlayer> optPlayer = this.plugin.getEServer().getEPlayer(event.getTargetEntity());
		if(optPlayer.isPresent()) {
			EPlayer player = optPlayer.get();
			
			// Corrige bug
			player.sendTitle(Title.CLEAR);
			player.getTabList().setHeaderAndFooter(null, null);
			
			this.plugin.getManagerService().getEScoreBoard().addPlayer(player);
		}
	}
	
	@Listener(order=Order.POST)
	public void onPlayerDisconnect(final ClientConnectionEvent.Disconnect event) {
		this.plugin.getEServer().removeEPlayer(event.getTargetEntity());
	}
}
