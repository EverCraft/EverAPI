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
package fr.evercraft.everapi.services.planning;

import java.util.Calendar;

import fr.evercraft.everapi.plugin.EPlugin;

public class Schedules implements Runnable {

	private static int NEXT_ID = 0;
	
	private final int id;
	
	private final Runnable task;
	private final Calendar calendar;
	private final EPlugin plugin;
	
	public Schedules(final EPlugin plugin, final Runnable task, final Calendar calendar){
		this.id = NEXT_ID++;
		
		this.plugin = plugin;
		this.task = task;
		this.calendar = calendar;
	}

	public int getId() {
		return id;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public EPlugin getPlugin() {
		return plugin;
	}

	@Override
	public void run() {
		this.task.run();
	}
	
	@Override
	public String toString() {
		return "Plannings [id=" + this.id + ", calendar=" + this.calendar.getTime() + ", plugin="
				+ this.plugin.getGame().getPluginManager().fromInstance(plugin).get().getName() + "]";
	}
}
