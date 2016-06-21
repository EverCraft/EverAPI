package fr.evercraft.everapi.java;

import java.util.Optional;

public class UtilsBoolean {
	
	public static Optional<Boolean> parseBoolean(String value) {
		if(value.equalsIgnoreCase("true")) {
			return Optional.of(true);
		} else if(value.equalsIgnoreCase("false")) {
			return Optional.of(false);
		}
		return Optional.empty();
	}
}
