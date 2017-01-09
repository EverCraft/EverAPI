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

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.essentials.Mail;

public interface MailEvent extends Event {
	
	public static enum Action {
    	ADD,
    	REMOVE,
    	READ,
    	RECEIVE;
    }
	
	public EPlayer getPlayer();
	
    public Action getAction();
    
    @Override
	public Cause getCause();
	
	public interface Send extends MailEvent, Cancellable {
		public CommandSource getTo();
		
		public String getMessage();
	}
	public interface Remove extends MailEvent {
		public Mail getMail();
	}
	public interface Read extends MailEvent {
		public Mail getMail();
	}
	public interface Receive extends MailEvent {
		public Mail getMail();
	}
}
