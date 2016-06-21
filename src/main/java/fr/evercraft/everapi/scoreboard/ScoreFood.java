package fr.evercraft.everapi.scoreboard;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreFood extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getFood();
	}
	
	@Override
	public boolean isUpdate() {
		return false;
	}
}