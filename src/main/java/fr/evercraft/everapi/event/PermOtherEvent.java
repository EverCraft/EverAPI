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

public abstract class PermOtherEvent extends AbstractEvent {

    public static enum Action {
    	OTHER_PERMISSION_CHANGED,
    	OTHER_OPTION_CHANGED,
    	OTHER_INHERITANCE_CHANGED,
    	OTHER_ADDED,
    	OTHER_REMOVED;
    };

    private final Subject subject;
    private final Action action;
    private final Cause cause;
    
    public PermOtherEvent(Subject subject, Action action, Cause cause) {
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
	
	public static class Permission extends PermOtherEvent {
		public Permission(Subject subject, Cause cause) {
			super(subject, Action.OTHER_PERMISSION_CHANGED, cause);
		}
	}
	
	public static class Inheritance extends PermOtherEvent {
		public Inheritance(Subject subject, Cause cause) {
			super(subject, Action.OTHER_INHERITANCE_CHANGED, cause);
		}
	}
	
	public static class Option extends PermOtherEvent {
		public Option(Subject subject, Cause cause) {
			super(subject, Action.OTHER_OPTION_CHANGED, cause);
		}
	}
	
	public static class Add extends PermOtherEvent {
		public Add(Subject subject, Cause cause) {
			super(subject, Action.OTHER_ADDED, cause);
		}
	}
	
	public static class Remove extends PermOtherEvent {
		public Remove(Subject subject, Cause cause) {
			super(subject, Action.OTHER_REMOVED, cause);
		}
	}
}
