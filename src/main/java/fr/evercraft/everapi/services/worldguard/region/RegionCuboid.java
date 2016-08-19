package fr.evercraft.everapi.services.worldguard.region;

import com.flowpowered.math.vector.Vector3i;

public interface RegionCuboid extends Region {
	
	public Vector3i getMin();
	public Vector3i getMax();
	
	public default RegionType getType() {
		return RegionType.CUBOID;
	}
}
