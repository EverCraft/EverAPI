package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.event.Listener;

import fr.evercraft.everapi.event.StatsUserEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreDeath extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getDeath();
	}
	
	@Listener
    public void event(StatsUserEvent.Death event) {
		this.update(event.getVictim().getUniqueId(), TypeScores.DEATHS);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}