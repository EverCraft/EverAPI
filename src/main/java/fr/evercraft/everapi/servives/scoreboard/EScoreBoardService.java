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
package fr.evercraft.everapi.servives.scoreboard;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.priority.PriorityService;
import fr.evercraft.everapi.servives.scoreboard.event.ScoreBoardEvent;
import fr.evercraft.everapi.servives.scoreboard.event.ScoreBoardEvent.Action;

public class EScoreBoardService implements ScoreBoardService {	
	private final EverAPI plugin;
	
	public EScoreBoardService(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	public void reload() {
	}
	
	@Override
	public boolean addObjective(Player player, DisplaySlot display, Objective objective) {
		return addObjective(player, getPriority(display, objective), display, objective);
	}
	
	@Override
	public boolean addObjective(Player player, int priority, DisplaySlot display, Objective objective) {
		Optional<Objective> objective_player = player.getScoreboard().getObjective(display);
		if(!objective_player.isPresent() || getPriority(display, objective_player.get()) <= priority) {
			if(objective_player.isPresent()) {
				player.getScoreboard().removeObjective(objective_player.get());
				this.plugin.getGame().getEventManager().post(new ScoreBoardEvent(this.plugin, player, objective_player.get(), display, Action.REPLACE));
			}
			player.getScoreboard().addObjective(objective);
			player.getScoreboard().updateDisplaySlot(objective, display);
			this.plugin.getGame().getEventManager().post(new ScoreBoardEvent(this.plugin, player, objective, display, Action.ADD));
			return true;
		}
		return false;
	}
	
	private int getPriority(DisplaySlot type, Objective objective) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getScoreBoard(type, objective.getName());
		}
		return PriorityService.DEFAULT;
	}
	
}
