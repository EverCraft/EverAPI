package fr.evercraft.everapi.registers;

import fr.evercraft.everapi.register.ECatalogType;

public class IceType extends ECatalogType {

	public IceType(String name) {
		super(name);
	}
	
	public static interface IceTypes {
		static final IceType FORM = new IceType("FORM");
		static final IceType MELT = new IceType("MELT");
	}
}
