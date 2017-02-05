package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class DoubleFlag extends EFlag<Double> {

	public DoubleFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		return Arrays.asList("1.01", "2.02", "3.03");
	}

	@Override
	public String serialize(Double value) {
		return value.toString();
	}

	@Override
	public Double deserialize(String value) throws IllegalArgumentException {
		try {
			return Double.valueOf(value);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
