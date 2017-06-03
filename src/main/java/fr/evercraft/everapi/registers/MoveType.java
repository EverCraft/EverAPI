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
package fr.evercraft.everapi.registers;

import fr.evercraft.everapi.register.ECatalogType;

public class MoveType extends ECatalogType {
	
	private final boolean cancellable;
	private final boolean teleport;
	
	public MoveType(String name, boolean cancellable, boolean teleport) {
		super(name);
		this.cancellable = cancellable;
		this.teleport = teleport;
	}
	
	public boolean isCancellable() {
		return this.cancellable;
	}
	
	public boolean isTeleport() {
		return this.teleport;
	}
	
	public static class MoveTypes {
		public static final MoveType RESPAWN = new MoveType("RESPAWN", false, true);
		public static final MoveType TELEPORT = new MoveType("TELEPORT", true, true);
		
		public static final MoveType MOVE = new MoveType("MOVE", true, false);
		public static final MoveType RIDE = new MoveType("RIDE", true, false);
		public static final MoveType EMBARK = new MoveType("EMBARK", true, false);
		
		public static final MoveType UNKNOWN_NON_CANCELLABLE = new MoveType("UNKNOWN_NON_CANCELLABLE", true, false);
		public static final MoveType UNKNOWN_CANCELLABLE = new MoveType("UNKNOWN_CANCELLABLE", false, false);
	}
}
