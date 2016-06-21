package fr.evercraft.everapi.scoreboard;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.sponge.UtilsItemStack;

public class ScoreLeggingsMax extends Score {
	private final int DEFAULT = 0;
	
	@Override
	public Integer getValue(EPlayer player) {
		if(player.getLeggings().isPresent()) {
			return UtilsItemStack.getMaxDurability(player.getLeggings().get());
		}
		return DEFAULT;
	}
	
	@Listener
    public void event(ChangeEntityEquipmentEvent.TargetPlayer event) {
		this.update(event.getTargetEntity().getUniqueId(), TypeScores.LEGGINGS_MAX);
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}