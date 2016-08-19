package fr.evercraft.everapi.services.worldguard.region;

public interface RegionGlobal extends Region {
	
	public default RegionType getType() {
		return RegionType.GLOBAL;
	}
}
