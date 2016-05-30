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
package fr.evercraft.everapi.services.scoreboard;

import java.util.Optional;

import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.priority.PriorityService;
import fr.evercraft.everapi.services.scoreboard.event.ScoreBoardEvent;
import fr.evercraft.everapi.services.scoreboard.event.ScoreBoardEvent.Action;

public class EScoreBoardService implements ScoreBoardService {	
	private final EverAPI plugin;
		
	public EScoreBoardService(final EverAPI plugin){
		this.plugin = plugin;
	}
	
	public void reload() {
	}
	
	public boolean addPlayer(EPlayer player) {
		player.setScoreboard(Scoreboard.builder().build());
		return true;
	}
	
	/*
	 * Objective
	 */
	
	@Override
	public boolean addObjective(EPlayer player, DisplaySlot display, Objective objective) {
		return addObjective(player, getPriority(display, objective), display, objective);
	}
	
	@Override
	public boolean addObjective(EPlayer player, int priority, DisplaySlot display, Objective objective) {
		Optional<Objective> objective_player = player.getScoreboard().getObjective(display);
		if(!objective_player.isPresent() || getPriority(display, objective_player.get()) <= priority) {
			if(objective_player.isPresent()) {
				player.getScoreboard().removeObjective(objective_player.get());
				this.plugin.getGame().getEventManager().post(new ScoreBoardEvent(this.plugin, player, objective_player.get(), display, Action.REPLACE));
			}
			if(!player.getScoreboard().getObjective(objective.getName()).isPresent()) {
				player.getScoreboard().addObjective(objective);
				player.getScoreboard().updateDisplaySlot(objective, display);
				this.plugin.getGame().getEventManager().post(new ScoreBoardEvent(this.plugin, player, objective, display, Action.ADD));
				return true;
			} else {
				this.plugin.getLogger().warn("Multi-Objective (player='" + player.getIdentifier() + "';objective='" + objective.getName() + "')");
			}
		}
		return false;
	}
	
	private int getPriority(DisplaySlot display, Objective objective) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getScoreBoard(display, objective.getName());
		}
		return PriorityService.DEFAULT;
	}
	
	@Override
	public boolean removeObjective(EPlayer player, DisplaySlot display, Objective objective) {
		return removeObjective(player, display, objective.getName());
	}
	
	@Override
	public boolean removeObjective(EPlayer player, DisplaySlot display, String identifier) {
		Optional<Objective> objective = player.getScoreboard().getObjective(display);
		if(objective.isPresent() && objective.get().getName().equals(identifier)) {
			player.getScoreboard().removeObjective(objective.get());
			this.plugin.getGame().getEventManager().post(new ScoreBoardEvent(this.plugin, player, objective.get(), display, Action.REMOVE));
			return true;
		}
		return false;
	}
	
}
