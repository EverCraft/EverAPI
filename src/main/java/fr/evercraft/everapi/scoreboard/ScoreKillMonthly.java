package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.event.Listener;

import fr.evercraft.everapi.event.StatsUserEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreKillMonthly extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getKillMonthly();
	}
	
	@Listener
    public void event(StatsUserEvent.Kill event) {
		this.update(event.getKiller().getUniqueId(), TypeScores.KILLS);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}