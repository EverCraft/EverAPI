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
package fr.evercraft.everapi.sponge;

public class UtilsTick {
	public static final int TICK_SECONDS = 20;
	
	public static int parseSeconds(final double seconds){
		return (int) (seconds * TICK_SECONDS);
	}
	
	public static long parseSeconds(final long seconds){
		return seconds * TICK_SECONDS;
	}
	
	public static long parseMinutes(final long minutes){
		return parseSeconds(minutes * 60);
	}
	
	public static int parseMillis(final long millis){
		return (int) parseSeconds(millis / 1000);
	}
}
