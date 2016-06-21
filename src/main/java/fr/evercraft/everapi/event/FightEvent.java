package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.server.player.EPlayer;

public interface FightEvent extends Event {	
	public static enum Type {
    	START,
    	STOP;
    }

    public Type getType();
    
    public EPlayer getPlayer();
    
    @Override
	public Cause getCause();
    
    interface Start extends FightEvent {
    	
		public EPlayer getOther();

		public boolean isVictim();
    }
    
    interface Stop extends FightEvent{};
}
