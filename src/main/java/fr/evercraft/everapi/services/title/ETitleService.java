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
package fr.evercraft.everapi.services.title;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.TitleEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.TitleService;
import fr.evercraft.everapi.services.title.event.EAddTitleEvent;
import fr.evercraft.everapi.services.title.event.ERemoveTitleEvent;
import fr.evercraft.everapi.services.title.event.EReplaceTitleEvent;

public class ETitleService implements TitleService {
	private final static int UPDATE = 1000;
	
	private final EverAPI plugin;
	
	private Task task;
	private final ConcurrentMap<UUID, TitleMessage> titles;
	
	public ETitleService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.titles = new ConcurrentHashMap<UUID, TitleMessage>();
	}
	
	public void reload() {
		if(this.task != null) {
			this.task.cancel();
		}
		this.titles.clear();
	}
	
	@Override
	public boolean send(EPlayer player, String identifier, Title title) {
		return this.send(player, identifier, this.getPriority(identifier), title);
	}
	
	@Override
	public boolean send(EPlayer player, String identifier,  int priority, Title title) {
		TitleMessage titleMessage = this.titles.get(player.getUniqueId());
		// Vérifie la priorité
		if(titleMessage == null || this.getPriority(titleMessage.getIdentifier()) <= priority) {
			TitleMessage newtitleMessage = new TitleMessage(player.getUniqueId(), identifier, title);
			// Si l'ActionBar fonctionne
			if(newtitleMessage.send(player)) {				
				// On ajoute la nouveau Title
				this.titles.put(player.getUniqueId(), newtitleMessage);
				
				// Si il y a un déjà une Title on post un event de remplacement
				if(titleMessage != null) {
					this.plugin.getGame().getEventManager().post(new EAddTitleEvent(player, newtitleMessage, Cause.source(this.plugin).build()));
				} else {
					this.plugin.getGame().getEventManager().post(new EReplaceTitleEvent(player, titleMessage, newtitleMessage, Cause.source(this.plugin).build()));
				}
				
				// On réactive le cooldown
				start();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(EPlayer player, String identifier) {
		TitleMessage titleMessage = this.titles.get(player.getUniqueId());
		if(titleMessage != null && titleMessage.getIdentifier().equalsIgnoreCase(identifier)) {
			this.plugin.getGame().getEventManager().post(new ERemoveTitleEvent(player, titleMessage, Cause.source(this.plugin).build()));
			player.sendTitle(Title.CLEAR);
			return true;
		}
		return false;
	}
	
	public void update() {
		if(this.titles.isEmpty()) {
			stop();
		} else {
			List<TitleEvent> events = new ArrayList<TitleEvent>();
			List<UUID> removes = new ArrayList<UUID>();
			for(TitleMessage titleMessage : this.titles.values()) {
				if(System.currentTimeMillis() >= titleMessage.getTime()) {
					Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(titleMessage.getPlayer());
					if(player.isPresent()) {
						events.add(new ERemoveTitleEvent(player.get(), titleMessage, Cause.source(this.plugin).build()));
					} else {
						removes.add(titleMessage.getPlayer());
					}
				}
			}
			
			for(TitleEvent event : events) {
				this.titles.remove(event.getPlayer().getUniqueId());
				this.plugin.getGame().getEventManager().post(event);
			}
			
			for(UUID remove : removes) {
				this.titles.remove(remove);
			}
			
			if(this.titles.isEmpty()) {
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
		return this.titles.containsKey(uuid);
	}

	@Override
	public Optional<TitleMessage> get(UUID uuid) {
		return Optional.ofNullable(this.titles.get(uuid));
	}
	
	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getTitle(identifier);
		}
		return PriorityService.DEFAULT;
	}
}
