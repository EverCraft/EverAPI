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
package fr.evercraft.everapi.services;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;

public interface PriorityService {
	public final static int PRIORITY_DEFAULT = 0;
	
	public final static String ACTIONBAR = "actionbar";
	public final static String TITLE = "title";
	public final static String NAMETAG = "nametag";
	public final static String TABLIST = "tablist";
	public final static String BOSSBAR = "bossbar";
	
	public final static String SPAWN = "spawn";
	
	public int get(String collection, String identifier);
	
	public int get(DisplaySlot type, String identifier);
}
