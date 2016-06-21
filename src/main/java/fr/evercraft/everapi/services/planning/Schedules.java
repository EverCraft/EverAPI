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
