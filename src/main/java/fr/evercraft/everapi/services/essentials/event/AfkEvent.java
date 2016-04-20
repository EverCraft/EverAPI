package fr.evercraft.everapi.services.essentials.event;

import java.util.UUID;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;

public class AfkEvent implements Event {	
	public static enum Action {
    	COMMAND,
    	INACTIVITY;
    }
	
	private final EPlugin plugin;
    private final UUID uuid;
    private final boolean value;
    private final Action action;

    public AfkEvent(final EPlugin plugin, final UUID uuid, final boolean value, final Action action) {
    	this.plugin = plugin;
    	
    	this.uuid = uuid;
        this.value = value;
        this.action = action;
    }

    public UUID getPlayer() {
        return this.uuid;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}

