package fr.evercraft.everapi.services.permission.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.Subject;

import fr.evercraft.everapi.plugin.EPlugin;

public class PermGroupEvent implements Event {

    public static enum Action {
    	GROUP_PERMISSIONS_CHANGED,
    	GROUP_INHERITANCE_CHANGED,
    	GROUP_OPTION_CHANGED,
    	GROUP_ADDED,
    	GROUP_REMOVED;
    };

    private final EPlugin plugin;
    private final Subject subject;
    private final Action action;

    public PermGroupEvent(final EPlugin plugin, final Subject subject, final Action action) {
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
