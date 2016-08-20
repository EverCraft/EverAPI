package fr.evercraft.everapi.services.worldguard.flag;

public class FlagDouble extends Flag {
	
	private double value;
	
	public FlagDouble(String name, double value) {
		super(name);
		
		this.value = value;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public boolean setValue(double value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
