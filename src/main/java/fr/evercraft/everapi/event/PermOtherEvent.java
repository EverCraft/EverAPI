package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.Subject;

public interface PermOtherEvent extends Event {

    public static enum Action {
    	OTHER_PERMISSION_CHANGED,
    	OTHER_OPTION_CHANGED,
    	OTHER_INHERITANCE_CHANGED,
    	OTHER_ADDED,
    	OTHER_REMOVED;
    };

    public Subject getSubject();

    public Action getAction();

	@Override
	public Cause getCause();
	
	public interface Permission {}
	public interface Option {}
	public interface Inheritance {}
	public interface Add {}
	public interface Remove {}
}
