package fr.evercraft.everapi.services.chat.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;

public class ChatSystemEvent implements Event {

    public static enum Action {
    	RELOADED;
    };

    private final EPlugin plugin;
    private final Action action;

    public ChatSystemEvent(final EPlugin plugin, final Action action) {
    	this.plugin = plugin;
    	
        this.action = action;
    }
    
    public Action getAction() {
        return this.action;
    }

	@Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
