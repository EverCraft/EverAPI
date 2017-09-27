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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.services.InformationService;
import fr.evercraft.everapi.services.SpawnService;
import fr.evercraft.everapi.services.SpawnSubjectService;
import fr.evercraft.everapi.services.essentials.EssentialsService;
import fr.evercraft.everapi.services.sanction.SanctionService;
import fr.evercraft.everapi.services.worldguard.WorldGuardService;
import ninja.leaping.configurate.ConfigurationNode;

public class EPriorityConfig extends EConfig<EverAPI> {

	public EPriorityConfig(final EverAPI plugin) {
		super(plugin, "priority");
	}
	
	@Override
	public void loadDefault() {
		addDefault(Arrays.asList("actionbar", "bossbar", "title"), Arrays.asList(
				SanctionService.MESSAGE_MUTE,
				SanctionService.MESSAGE_JAIL,
				InformationService.Priorities.NEWBIE_PLAYER, 
				InformationService.Priorities.NEWBIE_OTHERS, 
				InformationService.Priorities.CONNECTION_PLAYER, 
				InformationService.Priorities.CONNECTION_OTHERS,
				"message",
				WorldGuardService.Priorities.FLAG,
				InformationService.Priorities.AUTOMESSAGE));
		
		addDefault(Arrays.asList("nametag"), Arrays.asList(
				InformationService.Priorities.NAMETAG));
		
		addDefault(Arrays.asList("tablist"), Arrays.asList(
				InformationService.Priorities.TABLIST));
		
		addDefault(Arrays.asList("spawn"), Arrays.asList(
				SpawnSubjectService.Priorities.NEWBIE,
				WorldGuardService.Priorities.FLAG,
				EssentialsService.Priorities.HOME, 
				SpawnSubjectService.Priorities.SPAWN, 
				SpawnService.Priorities.BED,
				SpawnService.Priorities.WORLD));
		
		addDefault(Arrays.asList("scoreboard:" + DisplaySlots.BELOW_NAME.getName().replaceFirst("minecraft:", "").toLowerCase()), Arrays.asList(
				InformationService.Priorities.SCOREBOARD_BELOW_NAME));
		
		addDefault(Arrays.asList("scoreboard:" + DisplaySlots.LIST.getName().replaceFirst("minecraft:", "").toLowerCase()), Arrays.asList(
				InformationService.Priorities.SCOREBOARD_LIST));
		
		addDefault(Arrays.asList("scoreboard:" + DisplaySlots.SIDEBAR.getName().replaceFirst("minecraft:", "").toLowerCase()), Arrays.asList(
				InformationService.Priorities.SCOREBOARD_SIDEBAR));
	}
	
	public ConfigurationNode getContains(final String name) {
		for (Entry<Object, ? extends ConfigurationNode> config : this.getNode().getChildrenMap().entrySet()) {
			if (config.getKey().toString().contains(name)) {
				return config.getValue();
			}
		}
		return this.get(name);
	}
	
	public ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>> getScoreBoard() {
		ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>> scoreboards = new ConcurrentHashMap<DisplaySlot, ConcurrentHashMap<String, Integer>>();
		scoreboards.put(DisplaySlots.BELOW_NAME, this.getPriority("scoreboard." + DisplaySlots.BELOW_NAME.getName().toLowerCase()));
		scoreboards.put(DisplaySlots.LIST, this.getPriority("scoreboard." + DisplaySlots.LIST.getName().toLowerCase()));
		scoreboards.put(DisplaySlots.SIDEBAR, this.getPriority("scoreboard." + DisplaySlots.SIDEBAR.getName().toLowerCase()));
		return scoreboards;
	}

	public ConcurrentHashMap<String, Integer> getPriority(String name) {
		ConcurrentHashMap<String, Integer> priority = new ConcurrentHashMap<String, Integer>();
		List<String> config = this.getListString(name);
		int cpt = config.size();
		for (String type : config) {
			priority.put(type, cpt);
			cpt--;
		}
		return priority;
	}

	public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getPriority() {
		ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> priority = new ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>();
		this.getNode().getChildrenMap().forEach((keys, value) -> {
			if (keys.toString().equalsIgnoreCase("scoreboard")) return;
			
			ConcurrentHashMap<String, Integer> priorities = this.getPriority(keys.toString());
			for (String key : keys.toString().split(",")) {
				priority.put(key.replace(" ", ""), priorities);
			}
		});
		return priority;
	}

	public void register(String collection, String identifier) {
		List<String> values = new ArrayList<String>(this.getListString(collection));
		values.add(identifier);
		this.get(collection).setValue(values);
		this.save(true);
	}

	public void register(DisplaySlot type, String identifier) {
		List<String> values = new ArrayList<String>(this.getListString("scoreboard." + type.getName().toLowerCase()));
		values.add(identifier);
		this.get("scoreboard." + type.getName().toLowerCase()).setValue(values);
		this.save(true);
	}
}
