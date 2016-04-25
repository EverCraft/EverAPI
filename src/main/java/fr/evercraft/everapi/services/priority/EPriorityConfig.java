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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;

public class EPriorityConfig extends EConfig{

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
		addDefault("scoreboard", Arrays.asList("everinformations"));
	}
	
	public Map<String, Integer> getActionBar(){
		return this.getPriority("actionbar");
	}
	
	public Map<String, Integer> getTitle(){
		return this.getPriority("title");
	}
	
	public Map<String, Integer> getScoreBoard(){
		return this.getPriority("scoreboard");
	}

	private Map<String, Integer> getPriority(String name) {
		Map<String, Integer> priority = new HashMap<String, Integer>();
		List<String> config = this.getListString(name);
		int cpt = config.size();
		for(String type : config) {
			priority.put(type, cpt);
			cpt--;
		}
		return priority;
	}
}
