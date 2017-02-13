package fr.evercraft.everapi.services.worldguard.flag;

import fr.evercraft.everapi.server.location.VirtualLocation;
import fr.evercraft.everapi.services.worldguard.flag.type.FakeFlag;
import fr.evercraft.everapi.services.worldguard.flag.type.StateFlag.State;

public class Flags {
	
	public static Flag<State> BUILD = FakeFlag.of("BUILD");
	public static Flag<State> PVP = FakeFlag.of("PVP");
	public static Flag<State> INVINCIBILITY = FakeFlag.of("INVINCIBILITY");
	
	public static Flag<VirtualLocation> SPAWN = FakeFlag.of("SPAWN");
	public static Flag<VirtualLocation> TELEPORT = FakeFlag.of("TELEPORT");	
}
