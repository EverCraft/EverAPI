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
package fr.evercraft.everapi.services.scoreboard.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.event.ScoreBoardEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EScoreBoardEvent implements ScoreBoardEvent {
	
	private final EPlayer player;
    private final DisplaySlot display;
	private final Objective objective;
	private final Cause cause;
	private final Action action;

    public EScoreBoardEvent(final EPlayer player, final Objective objective, final DisplaySlot display, final Cause cause, final Action action) {    	
    	this.player = player;    	
    	this.objective = objective;
        this.display = display;
        this.cause = cause;
        this.action = action;
    }

    @Override
    public EPlayer getPlayer() {
        return this.player;
    }
    
    @Override
    public Action getAction() {
        return this.action;
    }
    
    @Override
    public Objective getObjective() {
        return this.objective;
    }
    
    @Override
	public String getIdentifier() {
		return this.objective.getName();
	}
    
    @Override
    public DisplaySlot getDisplaySlot() {
        return this.display;
    }
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
}
