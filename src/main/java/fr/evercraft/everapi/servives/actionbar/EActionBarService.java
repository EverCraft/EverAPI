/**
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
package fr.evercraft.everapi.servives.actionbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.priority.PriorityService;
import fr.evercraft.everapi.servives.actionbar.event.ActionBarEvent;
import fr.evercraft.everapi.servives.actionbar.event.ActionBarEvent.Action;

public class EActionBarService implements ActionBarService {
	private final static int UPDATE = 1000;
	private final static int LAST_UPDATE = 2000;
	
	private final EverAPI plugin;
	
	private Task task;
	private final ConcurrentMap<UUID, ActionBarMessage> actionBars;
	
	public EActionBarService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.actionBars = new ConcurrentHashMap<UUID, ActionBarMessage>();
	}
	
	public void reload() {
		if(this.task != null) {
			this.task.cancel();
		}
		this.actionBars.clear();
	}
	
	@Override
	public boolean send(Player player, String id, long stay, Text message) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.send(player, this.plugin.getManagerService().getPriority().get().getActionBar(id), stay, message);
		}
		return this.send(player, PriorityService.DEFAULT, stay, message);
	}
	
	@Override
	public boolean send(Player player, int priority, long stay, Text message) {
		ActionBarMessage actionBar = this.actionBars.get(player.getUniqueId());
		// Vérifie la priorité
		if(actionBar == null || actionBar.getPriority() <= priority) {
			ActionBarMessage newActionBar = new ActionBarMessage(player, priority, System.currentTimeMillis() + stay, message);
			// Si l'ActionBar fonctionne
			if(newActionBar.send()) {
				// Si il y a un déjà une ActionBar on post un event de remplacement
				if(actionBar != null) {
					this.plugin.getGame().getEventManager().post(new ActionBarEvent(this.plugin, actionBar, Action.REPLACE));
				}
				
				// On ajoute la nouveau ActionBar
				this.actionBars.put(player.getUniqueId(), newActionBar);
				this.plugin.getGame().getEventManager().post(new ActionBarEvent(this.plugin, newActionBar, Action.ADD));
				
				// On réactive le cooldown
				start();
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		if(this.actionBars.isEmpty()) {
			stop();
		} else {
			List<ActionBarEvent> events = new ArrayList<ActionBarEvent>();
			for(ActionBarMessage actionBar : this.actionBars.values()) {
				if(System.currentTimeMillis() + LAST_UPDATE > actionBar.getTime() || !actionBar.send()) {
					events.add(new ActionBarEvent(this.plugin, actionBar, Action.REMOVE));
				}
			}
			
			for(ActionBarEvent event : events) {
				this.actionBars.remove(event.getPlayer().getUniqueId());
				this.plugin.getGame().getEventManager().post(event);
			}
			
			if(this.actionBars.isEmpty()) {
				stop();
			} else {
				start();
			}
		}
	}
	
	public void start() {
		if(!isEnable()) {
			this.task = this.plugin.getGame().getScheduler().createTaskBuilder()
							.execute(() -> this.update())
							.delay(UPDATE, TimeUnit.MILLISECONDS)
							.interval(UPDATE, TimeUnit.MILLISECONDS)
							.async()
							.name("ActionBarService")
							.submit(this.plugin);
		}
	}
	
	public void stop() {
		if(isEnable()) {
			this.task.cancel();
			this.task = null;
		}
	}

	public boolean isEnable() {
		return this.task != null;
	}

	@Override
	public boolean has(UUID uuid) {
		return this.actionBars.containsKey(uuid);
	}

	@Override
	public Optional<ActionBarMessage> get(UUID uuid) {
		return Optional.ofNullable(this.actionBars.get(uuid));
	}
	
}
