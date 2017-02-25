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
import org.spongepowered.api.service.permission.Subject;

public abstract class PermGroupEvent extends AbstractEvent {

    public static enum Action {
    	GROUP_PERMISSION_CHANGED,
    	GROUP_INHERITANCE_CHANGED,
    	GROUP_OPTION_CHANGED,
    	GROUP_ADDED,
    	GROUP_REMOVED;
    };

    private final Subject subject;
    private final Action action;
    private final Cause cause;
    
    public PermGroupEvent(Subject subject, Action action, Cause cause) {
		super();
		this.subject = subject;
		this.action = action;
		this.cause = cause;
	}

	public Subject getSubject() {
    	return this.subject;
    }
    
    public Action getAction() {
    	return this.action;
    }

	@Override
	public Cause getCause() {
		return this.cause;
	}
	
	public static class Permission extends PermGroupEvent {
		public Permission(Subject subject, Cause cause) {
			super(subject, Action.GROUP_PERMISSION_CHANGED, cause);
		}
	}
	
	public static class Inheritance extends PermGroupEvent {
		public Inheritance(Subject subject, Cause cause) {
			super(subject, Action.GROUP_INHERITANCE_CHANGED, cause);
		}
	}
	
	public static class Option extends PermGroupEvent {
		public Option(Subject subject, Cause cause) {
			super(subject, Action.GROUP_OPTION_CHANGED, cause);
		}
	}
	
	public static class Add extends PermGroupEvent {
		public Add(Subject subject, Cause cause) {
			super(subject, Action.GROUP_ADDED, cause);
		}
	}
	
	public static class Remove extends PermGroupEvent {
		public Remove(Subject subject, Cause cause) {
			super(subject, Action.GROUP_REMOVED, cause);
		}
	}
}
