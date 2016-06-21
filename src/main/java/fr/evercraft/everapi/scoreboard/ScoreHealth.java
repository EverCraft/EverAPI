package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.HealEntityEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreHealth extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return (int) player.getHealth();
	}
	
	@Listener
    public void event(HealEntityEvent event) {
		if(event.getTargetEntity() instanceof Player) {
			this.update(event.getTargetEntity().getUniqueId(), TypeScores.HEALTH);
		}
	}
	
	@Override
	public boolean isUpdate() {
		return false;
	}
}