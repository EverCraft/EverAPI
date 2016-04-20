package fr.evercraft.everapi.services.essentials.event;

import java.util.UUID;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;

public class FlyEvent implements Event {	
	private final EPlugin plugin;
    private final UUID uuid;
    private final boolean value;

    public FlyEvent(final EPlugin plugin, final UUID uuid, final boolean value) {
    	this.plugin = plugin;
    	
    	this.uuid = uuid;
        this.value = value;
    }

    public UUID getPlayer() {
        return this.uuid;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}

