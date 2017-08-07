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
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class ActionBarEvent extends AbstractEvent {	
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }
	
	private final EPlayer player;
	private final String identifier;
	private final long time;
	private final Text message;
	private final Cause cause;
	
	public ActionBarEvent(EPlayer player, String identifier, long time, Text message, Cause cause) {
		this.player = player;
		this.identifier = identifier;
		this.time = time;
		this.message = message;
		this.cause = cause;
	}

	/**
	 * Retourne le joueur
	 * @return Le joueur
	 */
	public EPlayer getPlayer() {
		return this.player;
	}
    
	/**
	 * Retourne l'identifiant
	 * @return L'identifiant
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * Retourne le temps du message
	 * @return En Milliseconde
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * Retourne le message
	 * @return Le message
	 */
	public Text getMessage() {
		return this.message;
	}
	
	/**
	 * Retourne l'action
	 * @return L'action
	 */
    public abstract Action getAction();

    @Override
	public Cause getCause() {
    	return this.cause;
    }
    
    public static class Add extends ActionBarEvent {
    	
		public Add(EPlayer player, String identifier, long time, Text message, Cause cause) {
			super(player, identifier, time, message, cause);
		}

		@Override
		public Action getAction() {
			return Action.ADD;
		}
	}
    
    public static class Remove extends ActionBarEvent {
		public Remove(EPlayer player, String identifier, long time, Text message, Cause cause) {
			super(player, identifier, time, message, cause);
		}

		@Override
		public Action getAction() {
			return Action.REMOVE;
		}
	}
    
    public static class Replace extends ActionBarEvent {
    	
		private final String newIdentifier;
    	private final long newTime;
    	private final Text newMessage;
    	
    	public Replace(EPlayer player, String identifier, long time, Text message, String newIdentifier, long newTime, Text newMessage, Cause cause) {
			super(player, identifier, time, message, cause);

			this.newIdentifier = newIdentifier;
			this.newTime = newTime;
			this.newMessage = newMessage;
		}
    	
    	/**
    	 * Retourne le nouvelle identifiant
    	 * @return Le nouvelle identifiant
    	 */
		public String getNewIdentifier() {
			return this.newIdentifier;
		}
		public long getNewTime() {
			return this.newTime;
		}
		public Text getNewMessage() {
			return this.newMessage;
		}
		
		@Override
		public Action getAction() {
			return Action.REPLACE;
		}
	}
}
