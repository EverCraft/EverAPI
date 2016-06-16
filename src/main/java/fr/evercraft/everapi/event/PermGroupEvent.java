package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.Subject;

public interface PermGroupEvent extends Event {

    public static enum Action {
    	GROUP_PERMISSION_CHANGED,
    	GROUP_INHERITANCE_CHANGED,
    	GROUP_OPTION_CHANGED,
    	GROUP_ADDED,
    	GROUP_REMOVED;
    };

    public Subject getSubject();
    
    public Action getAction();

	@Override
	public Cause getCause();
	
	public interface Permission {}
	public interface Inheritance {}
	public interface Option {}
	public interface Add {}
	public interface Remove {}
}
