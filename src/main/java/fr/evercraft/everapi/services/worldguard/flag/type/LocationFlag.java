package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import fr.evercraft.everapi.server.location.VirtualLocation;
import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class LocationFlag extends EFlag<VirtualLocation> {

	public LocationFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		return Arrays.asList("here", "x,y,z", "x,y,z,yaw,pitch", "x,y,z,yaw,pitch,world");
	}

	@Override
	public String serialize(VirtualLocation value) {
		return value.getFloorX() + "," + value.getFloorY() + "," + value.getFloorZ() ;
	}
	
	@Override
	public VirtualLocation deserialize(String value) throws IllegalArgumentException {
		return null;
	}
}
