package fr.evercraft.everapi.services.worldguard.flag;

import org.spongepowered.api.entity.living.player.gamemode.GameMode;

public class FlagGameMode extends Flag {
	
	private GameMode value;
	
	public FlagGameMode(String name, GameMode value) {
		super(name);
		
		this.value = value;
	}
	
	public GameMode getValue() {
		return this.value;
	}
	
	public boolean setValue(GameMode value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
