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
package fr.evercraft.everapi.services.pvp.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class FightEvent implements Event {	
	public static enum Type {
    	START,
    	STOP;
    }
	
	private final Type type;
	private final EPlayer player;
	private final Cause cause;

    public FightEvent(final Type type, final EPlayer player, final Cause cause) {
    	this.type = type;
    	this.player = player;
    	this.cause = cause;
    }

    public Type getType() {
        return this.type;
    }
    
    public EPlayer getPlayer() {
		return player;
	}
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
    
    public class Start extends FightEvent {
    	private final EPlayer other;
    	private final boolean victim;
    	
    	public Start(final EPlayer player,  final EPlayer other, boolean victim, final Cause cause) {
        	super(Type.START, player, cause);

        	this.other = other;
        	this.victim = victim;
        }

		public EPlayer getOther() {
			return other;
		}

		public boolean isVictim() {
			return victim;
		}
    }
    
    public class Stop extends FightEvent {    	
    	public Stop(final EPlayer player, final Cause cause) {
        	super(Type.STOP, player, cause);
        }
    }
}
