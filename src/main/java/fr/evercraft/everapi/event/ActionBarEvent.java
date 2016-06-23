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

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.player.EPlayer;

public interface ActionBarEvent extends Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

	/**
	 * Retourne le joueur
	 * @return Le joueur
	 */
	public EPlayer getPlayer();
    
	/**
	 * Retourne l'identifiant
	 * @return L'identifiant
	 */
	public String getIdentifier();

	/**
	 * Retourne le temps du message
	 * @return En Milliseconde
	 */
	public long getTime();

	/**
	 * Retourne le message
	 * @return Le message
	 */
	public Text getMessage();
	
	/**
	 * Retourne l'action
	 * @return L'action
	 */
    public Action getAction();

    @Override
	public Cause getCause();
    
    interface Add extends ActionBarEvent {}
    
    interface Remove extends ActionBarEvent {}
    
    interface Replace extends ActionBarEvent {
    	
    	/**
    	 * Retourne le nouvelle identifiant
    	 * @return Le nouvelle identifiant
    	 */
		public String getNewIdentifier();
		public long getNewTime();
		public Text getNewMessage();
	}
}