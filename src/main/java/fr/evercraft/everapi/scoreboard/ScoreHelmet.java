package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreHelmet extends Score {
	private final int DEFAULT = 0;
	
	@Override
	public Integer getValue(EPlayer player) {
		if(player.getHelmet().isPresent()) {
			return player.getHelmet().get().get(Keys.ITEM_DURABILITY).orElse(DEFAULT);
		}
		return DEFAULT;
	}
	
	@Listener
    public void event(ChangeEntityEquipmentEvent.TargetPlayer event) {
		this.update(event.getTargetEntity().getUniqueId(), TypeScores.HELMET);
	}
	
	@Listener
    public void event(DamageEntityEvent event) {
		if(event.getTargetEntity() instanceof Player) {
			this.update(event.getTargetEntity().getUniqueId(), TypeScores.HELMET);
		}
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}