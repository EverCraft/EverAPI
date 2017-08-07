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
package fr.evercraft.everapi.services.essentials;

public class TeleportDelay {
	
	private final Long time;
	private final boolean move;
	private final Runnable function;
	
	public TeleportDelay(Long delay, Runnable function, boolean move) {
		this.time = System.currentTimeMillis() + delay;
		this.move = move;
		this.function = function;
	}
	
	public Long getTime() {
		return this.time;
	}

	public Runnable getFunction() {
		return this.function;
	}

	public void run() {
		this.function.run();
	}
	
	public boolean canMove() {
		return this.move;
	}
}
