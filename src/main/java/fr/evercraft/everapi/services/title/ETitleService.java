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
package fr.evercraft.everapi.services.title;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.TitleService;
import fr.evercraft.everapi.services.title.event.TitleEvent;
import fr.evercraft.everapi.services.title.event.TitleEvent.Action;

public class ETitleService implements TitleService {
	private final static int UPDATE = 1000;
	
	private final EverAPI plugin;
	
	private Task task;
	private final ConcurrentMap<UUID, TitleMessage> actionBars;
	
	public ETitleService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.actionBars = new ConcurrentHashMap<UUID, TitleMessage>();
	}
	
	public void reload() {
		if(this.task != null) {
			this.task.cancel();
		}
		this.actionBars.clear();
	}
	
	public boolean send(Player player, String id, Title title) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.send(player, this.plugin.getManagerService().getPriority().get().getTitle(id), title);
		}
		return this.send(player, PriorityService.DEFAULT, title);
	}
	
	public boolean send(Player player, int priority, Title title) {
		TitleMessage titleMessage = this.actionBars.get(player.getUniqueId());
		// Vérifie la priorité
		if(titleMessage == null || titleMessage.getPriority() <= priority) {
			TitleMessage newtitleMessage = new TitleMessage(player, priority, title);
			// Si l'ActionBar fonctionne
			if(newtitleMessage.send()) {
				// Si il y a un déjà une ActionBar on post un event de remplacement
				if(titleMessage != null) {
					this.plugin.getGame().getEventManager().post(new TitleEvent(this.plugin, titleMessage, Action.REPLACE));
				}
				
				// On ajoute la nouveau ActionBar
				this.actionBars.put(player.getUniqueId(), newtitleMessage);
				this.plugin.getGame().getEventManager().post(new TitleEvent(this.plugin, newtitleMessage, Action.ADD));
				
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
			List<TitleEvent> events = new ArrayList<TitleEvent>();
			for(TitleMessage titleMessage : this.actionBars.values()) {
				if(System.currentTimeMillis() >= titleMessage.getTime()) {
					events.add(new TitleEvent(this.plugin, titleMessage, Action.REMOVE));
				}
			}
			
			for(TitleEvent event : events) {
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
							.name("TitleService")
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
	public Optional<TitleMessage> get(UUID uuid) {
		return Optional.ofNullable(this.actionBars.get(uuid));
	}
	
}
