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
package fr.evercraft.everapi.services.worldguard;

public enum  MoveType {
	RESPAWN(false, true),
	TELEPORT(true, true),
	
	MOVE(true, false),
	RIDE(true, false),
	EMBARK(true, false),
	
	OTHER_NON_CANCELLABLE(true, false),
	OTHER_CANCELLABLE(true, false);
	
	private final boolean cancellable;
	private final boolean teleport;
	
	MoveType(boolean cancellable, boolean teleport) {
		this.cancellable = cancellable;
		this.teleport = teleport;
	}
	
	public boolean isCancellable() {
		return this.cancellable;
	}
	
	public boolean isTeleport() {
		return this.teleport;
	}
}
