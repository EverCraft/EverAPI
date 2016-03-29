package fr.evercraft.everapi.services.permission.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.Subject;

import fr.evercraft.everapi.plugin.EPlugin;

public class PermOtherEvent implements Event {

    public static enum Action {
    	OTHER_PERMISSIONS_CHANGED,
    	OTHER_OPTION_CHANGED,
    	OTHER_INHERITANCE_CHANGED,
    	OTHER_ADDED,
    	OTHER_REMOVED;
    };

    private final EPlugin plugin;
    private final Subject subject;
    private final Action action;

    public PermOtherEvent(final EPlugin plugin, final Subject subject, final Action action) {
    	this.plugin = plugin;
    	
        this.subject = subject;
        this.action = action;
    }

    public Subject getSubject() {
        return this.subject;
    }
    
    public Action getAction() {
        return this.action;
    }

	@Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
