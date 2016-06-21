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