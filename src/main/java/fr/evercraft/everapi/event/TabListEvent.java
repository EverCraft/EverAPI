package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.server.player.EPlayer;

public interface TabListEvent extends Event {
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
	 * Retourne l'identifiant du TabList
	 * @return
	 */
	public String getIdentifier();
	
	/**
	 * Retourne l'action
	 * @return L'action
	 */
    public Action getAction();

    @Override
	public Cause getCause();
    
    interface Add extends TabListEvent {}
    
    interface Remove extends TabListEvent {}
    
    interface Replace extends TabListEvent {
    	
    	/**
    	 * Retourne le nouvelle identifiant du TabList
    	 * @return Le nouvelle identifiant du TabList
    	 */
		public String getNewIdentifier();
	}
}