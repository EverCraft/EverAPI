package fr.evercraft.everapi.registers;

import fr.evercraft.everapi.register.ECatalogType;

public class SnowType extends ECatalogType {

	public SnowType(String name) {
		super(name);
	}
	
	public static interface SnowTypes {
		public static final SnowType FALL = new SnowType("FALL");
		public static final SnowType MELT = new SnowType("MELT");
	}
}
