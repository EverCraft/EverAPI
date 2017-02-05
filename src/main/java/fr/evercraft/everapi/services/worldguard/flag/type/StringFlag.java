package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class StringFlag extends EFlag<String> {

	public StringFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		return Arrays.asList("1", "2", "3");
	}

	@Override
	public String serialize(String value) {
		return value.replaceAll("\\n", "\\\\\\\\n").replaceAll("\n", "\\\\n");
	}

	@Override
	public String deserialize(String value) throws IllegalArgumentException {
		return value.replaceAll("(?!\\\\)\\\\n", "\n").replaceAll("\\\\\\\\n", "\\n");
	}
}
