package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Set;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class SetFlag<T> extends EFlag<Set<T>> {

	public SetFlag(String name) {
		super(name);
	}
	
	public abstract String subSerialize(T value);
	public abstract T subDeserialize(String value) throws IllegalArgumentException;

	@Override
	public String serialize(Set<T> value) {
		return "";
	}

	@Override
	public Set<T> deserialize(String value) throws IllegalArgumentException {
		return null;
	}
}
