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

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class FightEvent extends AbstractEvent {	
	public static enum Type {
    	START,
    	STOP;
    }

	private final EPlayer player;
	private final Cause cause;
	
    public FightEvent(EPlayer player, Cause cause) {
		this.player = player;
		this.cause = cause;
	}

	public abstract Type getType();
    
    public EPlayer getPlayer() {
    	return this.player;
    }
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
    
    public static class Start extends FightEvent {
    	private final EPlayer other;
    	private final boolean victim;
    	
		public Start(EPlayer player, EPlayer other, boolean victim, Cause cause) {
			super(player, cause);

			this.other = other;
			this.victim = victim;
		}

		public EPlayer getOther() {
			return this.other;
		}

		public boolean isVictim() {
			return this.victim;
		}
		
		@Override
		public Type getType() {
			return Type.START;
		}
    }
    
    public static class Stop extends FightEvent {
    	public static enum Reason {
        	TIME,
        	DEAD,
        	DISCONNECTED,
        	COMMAND,
        	PLUGIN;
        }
    	
    	private final Reason reason;
    	
    	public Stop(EPlayer player, Reason reason, Cause cause) {
			super(player, cause);
			this.reason = reason;
		}

		public Reason getReason() {
    		return this.reason;
    	}
    	
    	@Override
		public Type getType() {
			return Type.START;
		}
    }
}
