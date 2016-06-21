package fr.evercraft.everapi.event;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.server.player.EPlayer;

public interface BossBarEvent extends Event {
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
	 * Retourne le ServerBossBar
	 * @return ServerBossBar
	 */
	public ServerBossBar getServerBossBar();

	/**
	 * Retourne l'action
	 * @return L'action
	 */
    public Action getAction();

    @Override
	public Cause getCause();
    
    interface Add extends BossBarEvent {}
    
    interface Remove extends BossBarEvent {}
    
    interface Replace extends BossBarEvent {
    	
    	/**
    	 * Retourne le nouvelle identifiant
    	 * @return Le nouvelle identifiant
    	 */
		public String getNewIdentifier();
		public ServerBossBar getNewServerBossBar();
	}
}
