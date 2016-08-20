package fr.evercraft.everapi.services.worldguard.flag;

public abstract class Flag {

	private final String name;
	
	public Flag(final String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
