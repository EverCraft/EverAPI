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
package fr.evercraft.everapi.services.scoreboard.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public class ScoreBoardEvent implements Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }
	
	private final EPlugin plugin;
    private final Action action;
    private final DisplaySlot display;
	private final Objective objective;
	private final EPlayer player;

    public ScoreBoardEvent(final EPlugin plugin, final EPlayer player, final Objective objective, final DisplaySlot display, final Action action) {
    	this.plugin = plugin;
    	
    	this.player = player;    	
    	this.objective = objective;
    	this.action = action;
        this.display = display;
    }

    public EPlayer getEPlayer() {
        return this.player;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public Objective getScoreBoardObjective() {
        return this.objective;
    }
    
    public DisplaySlot getDisplaySlot() {
        return this.display;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
