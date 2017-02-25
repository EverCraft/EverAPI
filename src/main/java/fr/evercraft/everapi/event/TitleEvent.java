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
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class TitleEvent extends AbstractEvent {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }
	
	private final EPlayer player;
	private final Action action;
	private final String identifier;
	private final Title title;
	private final long time;
	private final Cause cause;

    public TitleEvent(EPlayer player, Action action, String identifier, Title title, long time, Cause cause) {
		super();
		this.player = player;
		this.action = action;
		this.identifier = identifier;
		this.title = title;
		this.time = time;
		this.cause = cause;
	}

	public EPlayer getPlayer() {
		return this.player;
	}
    
    public Action getAction() {
    	return this.action;
    }
    
    public String getIdentifier() {
    	return this.identifier;
    }
    
    public Title getTitle() {
    	return this.title;
    }
    
    public long getTime() {
    	return this.time;
    }
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
    
    public static class Add extends TitleEvent {
		public Add(EPlayer player, String identifier, Title title, long time, Cause cause) {
			super(player, Action.ADD, identifier, title, time, cause);
		}
	}
    public static class Remove extends TitleEvent {
		public Remove(EPlayer player, String identifier, Title title, long time, Cause cause) {
			super(player, Action.REMOVE, identifier, title, time, cause);
		}
	}
    public static class Replace extends TitleEvent {
    	private final String newIdentifier;
    	private final Title newTitle;
    	private final long newTime;
    	
		public Replace(EPlayer player, String identifier, Title title, long time,
				String newIdentifier, Title newTitle, long newTime, Cause cause) {
			super(player, Action.REPLACE, identifier, title, time, cause);
			
			this.newIdentifier = newIdentifier;
			this.newTitle = newTitle;
			this.newTime = newTime;
		}
		
    	public String getNewIdentifier() {
    		return this.newIdentifier;
    	}
        
        public Title getNewTitle() {
        	return this.newTitle;
        }
        
        public long getNewTime() {
        	return this.newTime;
        }
    }
}
