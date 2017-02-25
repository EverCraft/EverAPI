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

public abstract class PermSystemEvent extends AbstractEvent {

    public static enum Action {
    	RELOADED,
    	DEFAULT_GROUP_CHANGED;
    };
    
    private final Action action;
    private final Cause cause;
    
    public PermSystemEvent(Action action, Cause cause) {
		super();
		this.action = action;
		this.cause = cause;
	}

	public Action getAction() {
		return this.action;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
	
	public static class Reload extends PermSystemEvent {
		public Reload(Cause cause) {
			super(Action.RELOADED, cause);
		}
	}
	public static class Default extends PermSystemEvent {
		public Default(Cause cause) {
			super(Action.DEFAULT_GROUP_CHANGED, cause);
		}
	}
}
