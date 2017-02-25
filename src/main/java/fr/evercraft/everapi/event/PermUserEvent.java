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

import java.util.Optional;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.service.permission.Subject;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class PermUserEvent extends AbstractEvent {

    public static enum Action {
    	USER_PERMISSION_CHANGED,
    	USER_OPTION_CHANGED,
    	USER_GROUP_CHANGED,
    	USER_SUBGROUP_CHANGED,
    	USER_ADDED,
    	USER_REMOVED;
    };
    
    private final Subject subject;
    private final Action action;
    private final Optional<EPlayer> player;
    private final Cause cause;

    public PermUserEvent(Subject subject, Action action, Optional<EPlayer> player, Cause cause) {
		super();
		this.subject = subject;
		this.action = action;
		this.player = player;
		this.cause = cause;
	}

	public Subject getSubject() {
    	return this.subject;
    }
    
    public Action getAction() {
    	return this.action;
    }
    
    public Optional<EPlayer> getPlayer() {
    	return this.player;
    }

	@Override
	public Cause getCause() {
		return this.cause;
	}
	
	public static class Permission extends PermUserEvent {
		public Permission(Subject subject, Optional<EPlayer> player, Cause cause) {
			super(subject, Action.USER_PERMISSION_CHANGED, player, cause);
		}
	}
	public static class Option extends PermUserEvent {
		public Option(Subject subject, Optional<EPlayer> player, Cause cause) {
			super(subject, Action.USER_OPTION_CHANGED, player, cause);
		}
	}
	public static class Group extends PermUserEvent {
		public Group(Subject subject, Optional<EPlayer> player, Cause cause) {
			super(subject, Action.USER_GROUP_CHANGED, player, cause);
		}
	}
	public static class SubGroup extends PermUserEvent {
		public SubGroup(Subject subject, Optional<EPlayer> player, Cause cause) {
			super(subject, Action.USER_SUBGROUP_CHANGED, player, cause);
		}
	}
	public static class Add extends PermUserEvent {
		public Add(Subject subject, Optional<EPlayer> player, Cause cause) {
			super(subject, Action.USER_ADDED, player, cause);
		}
	}
	public static class Remove extends PermUserEvent {
		public Remove(Subject subject, Optional<EPlayer> player, Cause cause) {
			super(subject, Action.USER_REMOVED, player, cause);
		}
	}
	
}
