package fr.evercraft.everapi.services.mojang.check;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.mojang.check.MojangServer.Color;

public class MojangCheckEvent implements Event {

	private final EPlugin plugin;
	private final MojangServer server;
    private final Color color_before;
	private final Color color_after;

    public MojangCheckEvent(final EPlugin plugin, final MojangServer server, Color color_before, Color color_after) {
    	this.plugin = plugin;
    	
    	this.server = server;
        this.color_before = color_before;
        this.color_after = color_after;
        
        this.plugin.getLogger().debug("Event MojangCheckEvent : (" + this + ")");
    }

	public MojangServer getServer() {
        return this.server;
    }
    
	public Color getBeforeColore() {
        return this.color_before;
    }
	
    public Color getAfterColore() {
        return this.color_after;
    }

    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}

	@Override
	public String toString() {
		return "server='" + server + "', color_before='" + color_before + "', color_after='" + color_after + "'";
	}
}
