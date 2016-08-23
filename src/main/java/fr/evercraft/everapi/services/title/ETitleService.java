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
package fr.evercraft.everapi.services.title;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.EverAPI;
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
	private final ConcurrentMap<UUID, TitleMessage> players;
	
	public ETitleService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.players = new ConcurrentHashMap<UUID, TitleMessage>();
	}
	
	public void reload() {
		this.stop();
		
		HashMap<UUID, TitleMessage> titleMessages = new HashMap<UUID, TitleMessage>(this.players);
		this.players.clear();
		
		for (Entry<UUID, TitleMessage> titleMessage : titleMessages.entrySet()) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(titleMessage.getKey());
			if (player.isPresent()) {
				player.get().sendTitle(Title.CLEAR);
				
				// Event
				this.postRemove(player.get(), titleMessage.getValue());
			}
		}
	}
	
	@Override
	public boolean send(EPlayer player, String identifier, Title title) {
		return this.send(player, identifier, this.getPriority(identifier), title);
	}
	
	@Override
	public boolean send(EPlayer player, String identifier,  int priority, Title title) {
		TitleMessage titleMessage = this.players.get(player.getUniqueId());
		// Vérifie la priorité
		if (titleMessage == null || this.getPriority(titleMessage.getIdentifier()) <= priority) {
			TitleMessage newtitleMessage = new TitleMessage(player.getUniqueId(), identifier, title);
			// Si l'ActionBar fonctionne
			if (newtitleMessage.send(player)) {				
				// On ajoute la nouveau Title
				this.players.put(player.getUniqueId(), newtitleMessage);
				
				// Si il y a un déjà une Title on post un event de remplacement
				if (titleMessage == null) {
					this.postAdd(player, newtitleMessage);
				} else {
					this.postReplace(player, titleMessage, newtitleMessage);
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
		TitleMessage titleMessage = this.players.get(player.getUniqueId());
		if (titleMessage != null && titleMessage.getIdentifier().equalsIgnoreCase(identifier)) {
			player.sendTitle(Title.CLEAR);
			
			// Event
			this.postRemove(player, titleMessage);
			return true;
		}
		return false;
	}
	
	public void update() {
		if (this.players.isEmpty()) {
			stop();
		} else {
			final List<UUID> removes = new ArrayList<UUID>();
			for (TitleMessage titleMessage : this.players.values()) {
				if (System.currentTimeMillis() >= titleMessage.getTime()) {
					removes.add(titleMessage.getPlayer());
				}
			}
			
			if (!removes.isEmpty()) {
				this.plugin.getGame().getScheduler().createTaskBuilder()
					.execute(() -> this.updateSync(removes))
					.name("TitleService")
					.submit(this.plugin);
			}
		}
	}
	
	public void updateSync(List<UUID> players) {
		for (UUID uuid : players) {
			TitleMessage titleMessage = this.players.get(uuid);
			if (titleMessage != null && System.currentTimeMillis() >= titleMessage.getTime()) {
				this.players.remove(uuid);
				
				Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(uuid);
				if (player.isPresent()) {					
					//Event
					this.postRemove(player.get(), titleMessage);
				}
			}
		}
		
		
		if (this.players.isEmpty()) {
			stop();
		} else {
			start();
		}
	}
	
	public void start() {
		if (!isEnable()) {
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
		if (isEnable()) {
			this.task.cancel();
			this.task = null;
		}
	}

	public boolean isEnable() {
		return this.task != null;
	}
	
	@Override
	public boolean has(UUID uuid) {
		return this.players.containsKey(uuid);
	}

	@Override
	public Optional<TitleMessage> get(UUID uuid) {
		return Optional.ofNullable(this.players.get(uuid));
	}
	
	private int getPriority(String identifier) {
		if (this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getTitle(identifier);
		}
		return PriorityService.DEFAULT;
	}
	
	/*
	 * Event
	 */
	
	private void postAdd(EPlayer player, TitleMessage title) {
		this.plugin.getLogger().debug("Event TitleEvent.Add : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "title='" + title + "')");
		this.plugin.getGame().getEventManager().post(new EAddTitleEvent(player, title, Cause.source(this.plugin).build()));
	}
	
	private void postRemove(EPlayer player, TitleMessage title) {
		this.plugin.getLogger().debug("Event TitleEvent.Remove : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "title='" + title + "')");
		this.plugin.getGame().getEventManager().post(new ERemoveTitleEvent(player, title, Cause.source(this.plugin).build()));
	}
	
	private void postReplace(EPlayer player, TitleMessage title, TitleMessage new_title) {
		this.plugin.getLogger().debug("Event TitleEvent.Replace : ("
				+ "uuid='" + player.get().getUniqueId() + "';"
				+ "title='" + title + "';"
				+ "new_title='" + new_title + "')");
		this.plugin.getGame().getEventManager().post(new EReplaceTitleEvent(player, title, new_title, Cause.source(this.plugin).build()));
	}
}
