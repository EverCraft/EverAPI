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
package fr.evercraft.everapi.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class ScoreBoardEvent extends AbstractEvent {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

	private final EPlayer player;
	private final Action action;
	private final Objective objective;
	private final String identifier;
	private final DisplaySlot displaySlot;
	private final Cause cause;
	
    public ScoreBoardEvent(EPlayer player, Action action, Objective objective, String identifier,
			DisplaySlot displaySlot, Cause cause) {
		super();
		this.player = player;
		this.action = action;
		this.objective = objective;
		this.identifier = identifier;
		this.displaySlot = displaySlot;
		this.cause = cause;
	}

	public EPlayer getPlayer() {
		return this.player;
	}
    
    public Action getAction() {
    	return this.action;
    }
    
    public Objective getObjective() {
    	return this.objective;
    }
    
    public String getIdentifier() {
    	return this.identifier;
    }
    
    public DisplaySlot getDisplaySlot() {
    	return this.displaySlot;
    }
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
    
    public final class Add extends ScoreBoardEvent {
		public Add(EPlayer player, Objective objective, String identifier, DisplaySlot displaySlot,
				Cause cause) {
			super(player, Action.ADD, objective, identifier, displaySlot, cause);
		}
	}
    public final class Remove extends ScoreBoardEvent {
		public Remove(EPlayer player, Objective objective, String identifier, DisplaySlot displaySlot,
				Cause cause) {
			super(player, Action.REMOVE, objective, identifier, displaySlot, cause);
		}
	}
    public final class Replace extends ScoreBoardEvent {
    	private final Objective newObjective;
    	private final String newIdentifier;
		
    	public Replace(EPlayer player, Objective objective, String identifier, DisplaySlot displaySlot,
				Cause cause, Objective newObjective, String newIdentifier) {
			super(player, Action.REPLACE, objective, identifier, displaySlot, cause);
			this.newObjective = newObjective;
			this.newIdentifier = newIdentifier;
		}
    	
		public Objective getNewObjective() {
			return this.newObjective;
		}
		
    	public String getNewIdentifier() {
    		return this.newIdentifier;
    	}
    }
}
