package fr.evercraft.everapi.services.worldguard.region;

import java.util.List;

import com.flowpowered.math.vector.Vector3i;

public interface RegionPolygonal extends Region {
	
	public List<Vector3i> getPoints();
	
	public default RegionType getType() {
		return RegionType.POLYGON;
	}
}
