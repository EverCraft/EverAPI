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
package fr.evercraft.everapi.services.priority;

import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.PriorityService;

public class EPriorityService implements PriorityService {

	public final EverAPI plugin;
	
	private EPriorityConfig config;
	
	private final ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> priority;
	private final ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>> scoreboard;

	public EPriorityService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.priority = new ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>();
		this.scoreboard = new ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>>();
		
		this.config = new EPriorityConfig(plugin);
		
		this.reload();
	}

	public void reload() {
		this.priority.clear();
		this.scoreboard.clear();
		
		this.priority.putAll(this.config.getPriority());
		this.scoreboard.putAll(this.config.getScoreBoard());
	}

	@Override
	public int get(final String collection, final String identifier) {
		Preconditions.checkNotNull(collection, "collection");
		Preconditions.checkNotNull(identifier, "identifier");
		
		ConcurrentHashMap<String, Integer> values = this.priority.get(collection);
		if (values != null) {
			Integer value = values.get(identifier);
			if (value != null) {
				return value;
			}
		}
		
		this.plugin.getELogger().warn("Unknown priority (collection='" + collection + "';identifier='" + identifier + "')");
		this.config.register(collection, identifier);
		
		if (values == null) {
			values = new ConcurrentHashMap<String, Integer>();
			this.priority.put(collection, values);
		}
		values.put(identifier, PriorityService.PRIORITY_DEFAULT);
		
		return PriorityService.PRIORITY_DEFAULT;
	}
	
	@Override
	public int get(final DisplaySlot type, final String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		ConcurrentHashMap<String, Integer> values = this.scoreboard.get(type);
		if (values != null) {
			Integer value = values.get(identifier);
			if (value != null) {
				return value;
			}
		}
		
		this.plugin.getELogger().warn("Unknown priority (Type='" + type.getName() + "';ScoreBoard='" + identifier + "')");
		this.config.register(type, identifier);
		
		if (values == null) {
			values = new ConcurrentHashMap<String, Integer>();
			this.scoreboard.put(type, values);
		}
		values.put(identifier, PriorityService.PRIORITY_DEFAULT);
		return PriorityService.PRIORITY_DEFAULT;
	}
}
