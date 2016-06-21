package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.event.Listener;

import fr.evercraft.everapi.event.StatsUserEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreRatio extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getRatio();
	}
	
	@Listener
    public void event(StatsUserEvent.Death event) {
		this.update(event.getVictim().getUniqueId(), TypeScores.RATIO);
	}
	
	@Listener
    public void event(StatsUserEvent.Kill event) {
		this.update(event.getKiller().getUniqueId(), TypeScores.RATIO);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}