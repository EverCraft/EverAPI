package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityExperienceEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreXp extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getExp();
	}
	
	@Listener
    public void event(ChangeEntityExperienceEvent event) {
		if(event.getTargetEntity() instanceof Player) {
			this.update(event.getTargetEntity().getUniqueId(), TypeScores.XP);
		}
	}

	@Override
	public boolean isUpdate() {
		return false;
	}
}