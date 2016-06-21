/*
 * EverAPI
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EPlugin;

public class ManagerPlannings{
	private final EverAPI plugin;
	
	private final Timer timer;
	private final List<Schedules> schedules;
	
	public ManagerPlannings(final EverAPI plugin){
		this.plugin = plugin;
		
		this.timer = new Timer();
		this.schedules = new ArrayList<Schedules>();
	}
	
	/**
	 * Actualisation du planning
	 */
	private void actualisation(){
		this.timer.purge();
		if(!this.schedules.isEmpty()){
			this.timer.schedule(new TimerTask() {
		        public void run() {
		        	plugin.getGame().getScheduler().createTaskBuilder().execute(new Runnable() {
		        		@Override
		        		public void run() {
		        			if(!schedules.isEmpty()){
		        				Schedules schedule = schedules.remove(0);
		        				actualisation();
		        				schedule.run();
		        			}
		        		}
		        	}).submit(plugin);
		        }
		    }, this.schedules.get(0).getCalendar().getTime());
		}
	}
	
	/**
	 * Ajouter d'un horaire au planning
	 * @param schedule L'horaire
	 * @return L'id du schedules
	 */
	public int add(final Schedules schedule){
		if(this.schedules.isEmpty()){
			this.schedules.add(schedule);
			actualisation();
		} else {
			int cpt = 0;
			boolean trouver = false;
			
			while(cpt < this.schedules.size() && !trouver){
				if(schedule.getCalendar().before(this.schedules.get(cpt).getCalendar())){
					this.schedules.add(cpt, schedule);
					trouver = true;
					if(cpt == 0){
						actualisation();
					}
				}
				cpt++;
			}
			
			if(!trouver){
				this.schedules.add(schedule);
			}
		}
		return schedule.getId();
	}
	
	/**
	 * Supprimer un horaire du planning
	 * @param id L'id de l'horaire
	 * @return True si l'horaire a bien �t� supprim�
	 */
	public boolean remove(final int id){
		int cpt = 0;
		boolean trouver = false;
		while(cpt < this.schedules.size() && !trouver){
			if(this.schedules.get(cpt).getId() == id){
				this.schedules.remove(cpt);
				trouver = true;
			}
			cpt++;
		}
		
		if(trouver && cpt-- == 0){
			actualisation();
		}
		return trouver;
	}
	
	/**
	 * Supprimé tous les horaires du plugin
	 * @param plugin Le plugin
	 * @return True si les horaire ont bien été supprimé
	 */
	public boolean remove(final EPlugin plugin){
		Iterator<Schedules> iterator = this.schedules.iterator();
		boolean trouver = false;
		while(iterator.hasNext()){
			if(iterator.next().getPlugin().equals(plugin)){
				iterator.remove();
				trouver = true;
			}
		}
		
		if(trouver){
			actualisation();
		}
		return trouver;
	}
	
	/**
	 * Vérifié que l'horaire est bien dans le planning
	 * @param id L'id de l'horaire
	 * @return True si l'horaire a bien dans le planning
	 */
	public boolean contains(final int id){
		int cpt = 0;
		boolean trouver = false;
		while(cpt < this.schedules.size() && !trouver){
			trouver = this.schedules.get(cpt).getId() == id;
			cpt++;
		}
		return trouver;
	}
}
