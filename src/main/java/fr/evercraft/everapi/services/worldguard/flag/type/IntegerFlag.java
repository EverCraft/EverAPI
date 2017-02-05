package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class IntegerFlag extends EFlag<Integer> {

	public IntegerFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		return Arrays.asList("1", "2", "3");
	}

	@Override
	public String serialize(Integer value) {
		return value.toString();
	}

	@Override
	public Integer deserialize(String value) throws IllegalArgumentException {
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
