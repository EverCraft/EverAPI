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

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.essentials.Mail;

public abstract class MailEvent extends AbstractEvent {
	
	public static enum Action {
    	SEND,
    	REMOVE,
    	READ,
    	RECEIVE;
    }
	
	private final EUser user;
	private final Optional<EPlayer> player;
	private final Action action;
	private final Cause cause;
	
	public MailEvent(EUser user, Optional<EPlayer> player, Action action, Cause cause) {
		super();
		this.user = user;
		this.player = player;
		this.action = action;
		this.cause = cause;
	}

	public EUser getUser() {
		return this.user;
	}
	
	public Optional<EPlayer> getPlayer() {
		return this.player;
	}
	
    public Action getAction() {
    	return this.action;
    }
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
	
	public static class Send extends MailEvent implements Cancellable {
		private final CommandSource to;
		private final String message;
		private boolean cancelled;
		
		public Send(EUser user, Optional<EPlayer> player, CommandSource to, String message, Cause cause) {
			super(user, player, Action.SEND, cause);
			this.to = to;
			this.message = message;
			this.cancelled = false;
		}

		public CommandSource getTo() {
			return this.to;
		}
		
		public String getMessage() {
			return this.message;
		}

		@Override
		public boolean isCancelled() {
			return this.cancelled;
		}

		@Override
		public void setCancelled(boolean cancel) {
			this.cancelled = cancel;
		}
	}
	public static class Remove extends MailEvent {
		private final Mail mail;
		
		public Remove(EUser user, Optional<EPlayer> player, Mail mail, Cause cause) {
			super(user, player, Action.REMOVE, cause);
			this.mail = mail;
		}

		public Mail getMail() {
			return this.mail;
		}
	}
	public static class Read extends MailEvent {
		private final Mail mail;
		
		public Read(EUser user, Optional<EPlayer> player, Mail mail, Cause cause) {
			super(user, player, Action.READ, cause);
			this.mail = mail;
		}
		
		public Mail getMail() {
			return this.mail;
		}
	}
	public static class Receive extends MailEvent {
		private final Mail mail;
		
		public Receive(EUser user, Optional<EPlayer> player, Mail mail, Cause cause) {
			super(user, player, Action.RECEIVE, cause);
			this.mail = mail;
		}
		
		public Mail getMail() {
			return this.mail;
		}
	}
}
