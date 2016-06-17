package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public interface PermSystemEvent extends Event {

    public static enum Action {
    	RELOADED,
    	DEFAULT_GROUP_CHANGED;
    };
    
    public Action getAction();

	@Override
	public Cause getCause();
	
	public interface Reload extends PermSystemEvent {}
	public interface Default extends PermSystemEvent {}
}
