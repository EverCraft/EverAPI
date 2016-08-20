package fr.evercraft.everapi.services.worldguard.flag;

public class FlagBoolean extends Flag {
	
	private boolean value;
	
	public FlagBoolean(String name, boolean value) {
		super(name);
		
		this.value = value;
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	public boolean setValue(boolean value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
