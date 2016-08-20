package fr.evercraft.everapi.services.worldguard.flag;

import java.util.Set;
import java.util.TreeSet;

import org.spongepowered.api.entity.EntityType;

public class FlagSetEntity extends Flag {
	
	private Set<EntityType> value;
	
	public FlagSetEntity(String name) {
		this(name, new TreeSet<EntityType>());
	}
	
	public FlagSetEntity(String name, Set<EntityType> value) {
		super(name);
		
		this.value = value;
	}
	
	public Set<EntityType> getValue() {
		return this.value;
	}
	
	public boolean setValue(Set<EntityType> value) {
		if(this.value != value) {
			this.value = value;
			return true;
		}
		return false;
	}
}
