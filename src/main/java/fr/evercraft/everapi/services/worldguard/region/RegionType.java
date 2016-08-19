package fr.evercraft.everapi.services.worldguard.region;

public enum  RegionType {
	CUBOID("cuboid"),
	POLYGON("poly2d"),
	GLOBAL("global");

	private final String name;

	RegionType(final String name) {
        this.name = name;
    }
	
	public String getName() {
        return this.name;
    }
}
