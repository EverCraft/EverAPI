package fr.evercraft.everapi.services.worldguard.flag;

import java.util.Set;
import java.util.TreeSet;

public class FlagSetString extends Flag {
	
	private Set<String> value;
	
	public FlagSetString(String name) {
		this(name, new TreeSet<String>());
	}
	
	public FlagSetString(String name, Set<String> value) {
		super(name);
		
		this.value = value;
	}
	
	public Set<String> getValue() {
		return this.value;
	}
	
	public boolean setValue(Set<String> value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
