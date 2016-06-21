package fr.evercraft.everapi.scoreboard;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScorePing extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getConnection().getLatency();
	}
	
	@Override
	public boolean isUpdate() {
		return false;
	}
}