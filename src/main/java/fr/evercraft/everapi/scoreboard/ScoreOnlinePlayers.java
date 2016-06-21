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