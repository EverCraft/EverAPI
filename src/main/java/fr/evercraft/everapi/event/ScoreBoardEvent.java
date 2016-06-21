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
package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.server.player.EPlayer;

public interface ScoreBoardEvent extends Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

    public EPlayer getPlayer();
    
    public Action getAction();
    
    public Objective getObjective();
    
    public String getIdentifier();
    
    public DisplaySlot getDisplaySlot();
    
    @Override
	public Cause getCause();
    
    public interface Add extends ScoreBoardEvent {}
    public interface Remove extends ScoreBoardEvent {}
    public interface Replace extends ScoreBoardEvent {
    	public Objective getNewObjective();
    	public String getNewIdentifier();
    }
}
