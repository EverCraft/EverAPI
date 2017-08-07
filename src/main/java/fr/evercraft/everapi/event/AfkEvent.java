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
import fr.evercraft.everapi.server.player.EPlayer;

public abstract class AfkEvent extends AbstractCancellableEvent {	
	public static enum Action {
    	AUTO,
    	PLAYER,
    	PLUGIN;
    }
	
	private final EPlayer player;
	private final Action action;
	private final Cause cause;
	
	public AfkEvent(EPlayer player, Action action, Cause cause) {
		super();
		this.player = player;
		this.action = action;
		this.cause = cause;
	}

	public EPlayer getPlayer() {
		return this.player;
	}
    
    public Action getAction() {
    	return this.action;
    }
    
    public abstract boolean getValue();
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
	
	public static class Enable extends AfkEvent {
		public Enable(EPlayer player, Action action, Cause cause) {
			super(player, action, cause);
		}
		
		@Override
		public boolean getValue() {
			return true;
		}
	}
	
	public static class Disable extends AfkEvent {
		public Disable(EPlayer player, Action action, Cause cause) {
			super(player, action, cause);
		}
		
		@Override
		public boolean getValue() {
			return false;
		}
	}
}
