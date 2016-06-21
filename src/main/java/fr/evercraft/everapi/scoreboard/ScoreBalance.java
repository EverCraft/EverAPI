package fr.evercraft.everapi.scoreboard;

import java.util.UUID;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.economy.EconomyTransactionEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreBalance extends Score {
	
	@Override
	public Integer getValue(EPlayer player) {
		return player.getBalance().intValue();
	}
	
	@Listener
    public void event(EconomyTransactionEvent event) {
		try {
			UUID uuid = UUID.fromString(event.getTransactionResult().getAccount().getIdentifier());
			this.update(uuid, TypeScores.BALANCE);
		} catch (IllegalArgumentException e) {}
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}