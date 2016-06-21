package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.ChangeLevelEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreLevel extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getLevel();
	}
	
	@Listener
	public void event(ChangeLevelEvent event) {
		if(event.getTargetEntity() instanceof Player) {
			this.update(event.getTargetEntity().getUniqueId(), TypeScores.LEVEL);
		}
	}
	
	@Override
	public boolean isUpdate() {
		return false;
	}
}