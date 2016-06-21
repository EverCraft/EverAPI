package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.server.player.EPlayer;

public interface NameTagEvent extends Event {
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
    
    interface Add extends NameTagEvent {};
    
    interface Remove extends NameTagEvent {};
    
    interface Replace extends NameTagEvent {
    	
    	/**
    	 * Retourne le nouvelle identifiant
    	 * @return Le nouvelle identifiant
    	 */
		public String getNewIdentifier();
	};
}