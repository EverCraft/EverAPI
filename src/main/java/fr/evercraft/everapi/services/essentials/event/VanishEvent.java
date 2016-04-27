package fr.evercraft.everapi.services.essentials.event;

import java.util.UUID;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;

public class VanishEvent implements Event {	
	public static enum Action {
    	ADD,
    	REMOVE;
    }
	
	private final EPlugin plugin;
    private final UUID uuid;
    private final Action action;

    public VanishEvent(final EPlugin plugin, final UUID uuid, final Action action) {
    	this.plugin = plugin;
    	
    	this.uuid = uuid;
        this.action = action;
    }

    public UUID getPlayer() {
        return this.uuid;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}

