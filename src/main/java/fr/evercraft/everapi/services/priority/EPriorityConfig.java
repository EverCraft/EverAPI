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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;

public class EPriorityConfig extends EConfig<EverAPI> {

	public EPriorityConfig(final EverAPI plugin) {
		super(plugin, "priority");
	}
	
	@Override
	public void loadDefault() {
		addDefault("actionbar", Arrays.asList(
				"everinformations.newbie.player", 
				"everinformations.newbie.others", 
				"everinformations.connection.player", 
				"everinformations.connection.others", 
				"everinformations.join", 
				"everinformations.automessages"));
		addDefault("title", Arrays.asList(
				"everinformations.newbie.player", 
				"everinformations.newbie.others", 
				"everinformations.connection.player", 
				"everinformations.connection.others", 
				"everinformations.join", 
				"everinformations.automessages"));
		addDefault("bossbar", Arrays.asList(
				"everpvp",
				"everinformations.newbie.player", 
				"everinformations.newbie.others", 
				"everinformations.connection.player", 
				"everinformations.connection.others", 
				"everinformations.join", 
				"everinformations.automessages"));
		addDefault("nametag", Arrays.asList("everinformations"));
		addDefault("tablist", Arrays.asList("everinformations"));
		addDefault("scoreboard.below_name", Arrays.asList("everinfo.below"));
		addDefault("scoreboard.list", Arrays.asList("everinfo.list"));
		addDefault("scoreboard.sidebar", Arrays.asList("everinfo.side"));
	}
	
	public Map<String, Integer> getActionBar(){
		return this.getPriority("actionbar");
	}
	
	public Map<String, Integer> getTitle(){
		return this.getPriority("title");
	}
	
	public Map<String, Integer> getNameTag(){
		return this.getPriority("nametag");
	}
	
	public Map<String, Integer> getTabList(){
		return this.getPriority("tablist");
	}
	
	public Map<String, Integer> getBossBar(){
		return this.getPriority("bossbar");
	}
	
	public ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>> getScoreBoard() {
		ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>> scoreboards = new ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>>();
		scoreboards.put(DisplaySlots.BELOW_NAME, this.getPriority("scoreboard.below_name"));
		scoreboards.put(DisplaySlots.LIST, this.getPriority("scoreboard.list"));
		scoreboards.put(DisplaySlots.SIDEBAR, this.getPriority("scoreboard.sidebar"));
		return scoreboards;
	}

	private ConcurrentHashMap<String, Integer> getPriority(String name) {
		ConcurrentHashMap<String, Integer> priority = new ConcurrentHashMap<String, Integer>();
		List<String> config = this.getListString(name);
		int cpt = config.size();
		for (String type : config) {
			priority.put(type, cpt);
			cpt--;
		}
		return priority;
	}

}
