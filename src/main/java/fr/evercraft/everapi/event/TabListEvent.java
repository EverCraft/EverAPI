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

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class TabListEvent extends AbstractEvent {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

	private final EPlayer player;
	private final String identifier;
	private final Action action;
	private final Cause cause;
	
	public TabListEvent(EPlayer player, String identifier, Action action, Cause cause) {
		super();
		this.player = player;
		this.identifier = identifier;
		this.action = action;
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
	 * Retourne l'identifiant du TabList
	 * @return
	 */
	public String getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Retourne l'action
	 * @return L'action
	 */
    public Action getAction() {
    	return this.action;
    }

    @Override
	public Cause getCause() {
    	return this.cause;
    }
    
    public static class Add extends TabListEvent {
		public Add(EPlayer player, String identifier, Cause cause) {
			super(player, identifier, Action.ADD, cause);
		}
	}
    
    public static class Remove extends TabListEvent {
		public Remove(EPlayer player, String identifier, Cause cause) {
			super(player, identifier, Action.REMOVE, cause);
		}
	}
    
    public static class Replace extends TabListEvent {
    	private final String newIdentifier;
    	
    	public Replace(EPlayer player, String identifier, String newIdentifier, Cause cause) {
			super(player, identifier, Action.REPLACE, cause);
			this.newIdentifier = newIdentifier;
		}
    	
    	/**
    	 * Retourne le nouvelle identifiant du TabList
    	 * @return Le nouvelle identifiant du TabList
    	 */
		public String getNewIdentifier() {
			return this.newIdentifier;
		}
	}
}
