package fr.evercraft.everapi.services.worldguard.flag;

import com.flowpowered.math.vector.Vector3i;

public class FlagLocation extends Flag {
	
	private Vector3i value;
	
	public FlagLocation(String name) {
		super(name);
		
		this.value = new Vector3i(0, 0, 0);
	}
	
	public FlagLocation(String name, Vector3i value) {
		super(name);
		
		this.value = value;
	}
	
	public Vector3i getValue() {
		return this.value;
	}
	
	public boolean setValue(Vector3i value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
