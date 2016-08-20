package fr.evercraft.everapi.services.worldguard.flag;

import org.spongepowered.api.world.weather.Weather;

public class FlagWeather extends Flag {
	
	private Weather value;
	
	public FlagWeather(String name, Weather value) {
		super(name);
		
		this.value = value;
	}
	
	public Weather getValue() {
		return this.value;
	}
	
	public boolean setValue(Weather value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
