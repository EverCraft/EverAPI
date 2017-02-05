package fr.evercraft.everapi.services.worldguard.exception;

import fr.evercraft.everapi.services.worldguard.flag.Flag;

public class FlagRegisterException extends RuntimeException {

	private static final long serialVersionUID = 8386730760561544393L;
	
	public enum Type {
		CONFLICT,
		INITIALIZED
	}
	
	private final Type type;
	private final Flag<?> flag;

	public FlagRegisterException(Type type, Flag<?> flag) {
        this.type = type;
        this.flag = flag;
    }
	
	@Override
	public String getMessage() {
		if (this.type.equals(Type.INITIALIZED)) {
			return "New flags cannot be registered at this time";
		} else if (this.type.equals(Type.CONFLICT)) {
			return "A flag already exists by the name " + this.flag.getIdentifier();
		}
		return "";
	}

	
}
