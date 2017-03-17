/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.services.worldguard.flag;

import org.spongepowered.api.block.BlockType;

import fr.evercraft.everapi.server.location.VirtualLocation;
import fr.evercraft.everapi.services.worldguard.flag.type.FakeFlag;
import fr.evercraft.everapi.services.worldguard.flag.type.StateFlag.State;
import fr.evercraft.everapi.services.worldguard.flag.value.EntryFlagValue;

public class Flags {
	
	public static final Flag<State> BUILD = FakeFlag.of("BUILD");
	public static final Flag<EntryFlagValue<String, BlockType>> INTERACT_BLOCK = FakeFlag.of("INTERACT");
	public static final Flag<State> ENTRY = FakeFlag.of("ENTRY");
	public static final Flag<State> EXIT = FakeFlag.of("EXIT");
	public static final Flag<State> PVP = FakeFlag.of("PVP");
	public static final Flag<State> INVINCIBILITY = FakeFlag.of("INVINCIBILITY");
	
	public static final Flag<VirtualLocation> SPAWN = FakeFlag.of("SPAWN");
	public static final Flag<VirtualLocation> TELEPORT = FakeFlag.of("TELEPORT");	
}
