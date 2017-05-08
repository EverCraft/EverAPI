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
package fr.evercraft.everapi.services.bossbar;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scheduler.Task;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.ESpongeEventFactory;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.BossBarService;

public class EBossBarService implements BossBarService {	
	private final static int UPDATE = 1000;
	
	private final EverAPI plugin;
	
	private Task task;
	private final ConcurrentMap<UUID, EBossBar> players;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;
	
	public EBossBarService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.players = new ConcurrentHashMap<UUID, EBossBar>();
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
	}
	
	public void reload() {
		this.write_lock.lock();
		try {
			if (this.task != null) {
				this.task.cancel();
			}
			
			HashMap<UUID, EBossBar> bossbars = new HashMap<UUID, EBossBar>(this.players);
			this.players.clear();
			
			for (Entry<UUID, EBossBar> bossbar : bossbars.entrySet()) {
				Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(bossbar.getKey());
				if (player.isPresent()) {
					bossbar.getValue().removePlayer(player.get());
					this.postRemove(player.get(), bossbar.getValue());
				}
			}
		} finally {
			this.write_lock.unlock();
		}
	}
	
	@Override
	public boolean add(EPlayer player, String identifier, int priority, ServerBossBar bossbar, Optional<Long> stay) {
		boolean result = false;
		
		Optional<Long> time = Optional.empty();
		if (stay.isPresent()) {
			time = Optional.of(System.currentTimeMillis() + stay.get());
		}
		
		this.write_lock.lock();
		try {
			EBossBar bossbar_player = this.players.get(player.getUniqueId());
			// Vérifie la priorité
			if (bossbar_player == null) {
				// Ajoute
				bossbar_player = new EBossBar(identifier, bossbar, time);
				this.players.put(player.getUniqueId(), bossbar_player);
				bossbar.addPlayer(player.get());
				
				//Event
				this.postAdd(player, bossbar_player);
				
				// On réactive le cooldown
				start();
				result = true;
			} else if (this.getPriority(bossbar_player.getIdentifier()) <= priority && !bossbar_player.getServerBossBar().equals(bossbar)) {
				// Supprime
				bossbar_player.getServerBossBar().removePlayer(player.get());
				
				// Ajoute
				EBossBar new_bossbar_player = new EBossBar(identifier, bossbar, time);
				this.players.put(player.getUniqueId(), new_bossbar_player);
				bossbar.addPlayer(player.get());
				
				//Event
				this.postReplace(player, bossbar_player, new_bossbar_player);
				
				// On réactive le cooldown
				start();
				result = true;
			}
		} finally {
			this.write_lock.unlock();
		}
		return result;
	}

	@Override
	public boolean remove(EPlayer player, String identifier) {
		boolean result = false;
		
		this.write_lock.lock();
		try {
			EBossBar bossbar = this.players.get(player.getUniqueId());
			if (bossbar != null && bossbar.getIdentifier().equalsIgnoreCase(identifier)) {
				// Supprime
				bossbar.getServerBossBar().removePlayer(player.get());
				this.players.remove(player.getUniqueId());
				
				//Event
				this.postRemove(player, bossbar);
				return true;
			}
		} finally {
			this.write_lock.unlock();
		}
		return result;
	}
	
	public void update() {
		this.write_lock.lock();
		try {
			if (this.isEmpty()) {
				stop();
			} else {
				for (Entry<UUID, EBossBar> bossBar : this.players.entrySet()) {
					Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(bossBar.getKey());
					if (!player.isPresent()) {
						this.players.remove(bossBar.getKey());
					} else if (bossBar.getValue().getTime().isPresent() && System.currentTimeMillis() > bossBar.getValue().getTime().get()) {
						this.remove(player.get(), bossBar.getValue().getIdentifier());
					}
				}
				
				if (this.isEmpty()) {
					stop();
				} else {
					start();
				}
			}
		} finally {
			this.write_lock.unlock();
		}
	}

	public void start() {
		this.write_lock.lock();
		try {
			if (!this.isEnable()) {
				this.task = this.plugin.getGame().getScheduler().createTaskBuilder()
								.execute(() -> this.update())
								.delay(UPDATE, TimeUnit.MILLISECONDS)
								.interval(UPDATE, TimeUnit.MILLISECONDS)
								.name("ActionBarService")
								.submit(this.plugin);
			}
		} finally {
			this.write_lock.unlock();
		}
	}
	
	public void stop() {
		this.write_lock.lock();
		try {
			if (isEnable()) {
				this.task.cancel();
				this.task = null;
			}
		} finally {
			this.write_lock.unlock();
		}
	}

	public boolean isEnable() {
		boolean result = false;
		
		this.read_lock.lock();
		try {
			result = this.task != null;
		} finally {
			this.read_lock.unlock();
		}
		
		return result;
	}
	
	private boolean isEmpty() {
		boolean result = false;
		
		this.read_lock.lock();
		try {
			result = !this.players.values().stream().filter(bossbar -> bossbar.getTime().isPresent()).findFirst().isPresent();
		} finally {
			this.read_lock.unlock();
		}
		
		return result;
	}

	public int getPriority(String identifier) {
		return this.plugin.getManagerService().getPriority().getBossBar(identifier);
	}

	@Override
	public Optional<ServerBossBar> get(EPlayer player, String identifier) {
		Optional<ServerBossBar> result = Optional.empty();
		
		this.read_lock.lock();
		try {
			EBossBar bossbar = this.players.get(player.getUniqueId());
			if (bossbar != null && bossbar.getIdentifier().equalsIgnoreCase(identifier)) {
				return Optional.of(bossbar.getServerBossBar());
			}
		} finally {
			this.read_lock.unlock();
		}
		
		return result;
	}
	
	
	/*
	 * Event
	 */
	
	private void postAdd(EPlayer player, EBossBar bossbar) {
		this.plugin.getELogger().debug("Event BossBarEvent.Add : ("
				+ "uuid='" + player.getUniqueId() + "';"
				+ "boosbar='" + bossbar.getServerBossBar().getName().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createBossBarEventAdd(player, bossbar, Cause.source(this.plugin).build()));
	}
	
	private void postRemove(EPlayer player, EBossBar bossbar) {
		this.plugin.getELogger().debug("Event BossBarEvent.Remove : ("
				+ "uuid='" + player.getUniqueId() + "';"
				+ "boosbar='" + bossbar.getServerBossBar().getName().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createBossBarEventRemove(player, bossbar, Cause.source(this.plugin).build()));
	}
	
	private void postReplace(EPlayer player, EBossBar bossbar, EBossBar new_bossbar) {
		this.plugin.getELogger().debug("Event BossBarEvent.Replace : ("
				+ "uuid='" + player.getUniqueId() + "';"
				+ "boosbar='" + bossbar.getServerBossBar().getName().toPlain() + "';"
				+ "new_boosbar='" + new_bossbar.getServerBossBar().getName().toPlain() + "')");
		this.plugin.getGame().getEventManager().post(ESpongeEventFactory.createBossBarEventReplace(player, bossbar, new_bossbar, Cause.source(this.plugin).build()));
	}
}
