package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public interface ChatSystemEvent extends Event {

    public static enum Action {
    	RELOADED;
    };
    
    public Action getAction();

	@Override
	public Cause getCause();
	
	public interface Reload {}
}
