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
package fr.evercraft.everapi.services.priority;

import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;

public class EPriorityService implements PriorityService {

	public final EverAPI plugin;
	
	private EPriorityConfig config;
	
	private final ConcurrentHashMap<String, Integer> actionbar;
	private final ConcurrentHashMap<String, Integer> title;
	private final ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>> scoreboard;

	public EPriorityService(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.actionbar = new ConcurrentHashMap<String, Integer>();
		this.title = new ConcurrentHashMap<String, Integer>();
		this.scoreboard = new ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>>();
		
		this.config = new EPriorityConfig(plugin);
		
		this.reload();
	}

	public void reload() {
		this.actionbar.clear();
		this.title.clear();
		this.scoreboard.clear();
		
		this.actionbar.putAll(this.config.getActionBar());
		this.title.putAll(this.config.getTitle());
		this.scoreboard.putAll(this.config.getScoreBoard());
	}

	@Override
	public int getActionBar(final String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		if(this.actionbar.containsKey(identifier)) {
			return this.actionbar.get(identifier);
		}
		this.plugin.getLogger().warn("Priority (ActionBar='" + identifier + "')");
		return PriorityService.DEFAULT;
	}

	@Override
	public int getTitle(final String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		if(this.title.containsKey(identifier)) {
			return this.title.get(identifier);
		}
		this.plugin.getLogger().warn("Priority (Title='" + identifier + "')");
		return PriorityService.DEFAULT;
	}

	@Override
	public int getScoreBoard(final DisplaySlot type, final String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		if(this.scoreboard.containsKey(type)) {
			ConcurrentHashMap<String, Integer> map_type = this.scoreboard.get(type);
			if(map_type.containsKey(identifier)) {
				return map_type.get(identifier);
			}
		}
		this.plugin.getLogger().warn("Priority (Type='" + type.getName() + "';ScoreBoard='" + identifier + "')");
		return PriorityService.DEFAULT;
	}
}
