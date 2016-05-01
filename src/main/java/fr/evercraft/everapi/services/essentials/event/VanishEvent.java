package fr.evercraft.everapi.services.essentials.event;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public class VanishEvent implements Event {	
	public static enum Action {
    	ADD,
    	REMOVE;
    }
	
	private final EPlugin plugin;
    private final Action action;
    private final EPlayer player;

    public VanishEvent(final EPlugin plugin, final EPlayer player, final Action action) {
    	this.plugin = plugin;
    	
    	this.player = player;
        this.action = action;
    }

    public Player getPlayer() {
        return this.player;
    }
    
    public EPlayer getEPlayer() {
        return this.player;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}

