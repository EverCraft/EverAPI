package fr.evercraft.everapi.services.tablist.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.TabListEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ETabListEvent implements TabListEvent {	
	private final EPlayer player;
    private final Action action;
	private final String identifier;
	private final Cause cause;
	
    public ETabListEvent(final EPlayer player, final String identifier, final Cause cause, final Action action) {
    	this.player = player;
    	this.identifier = identifier;
    	this.cause = cause;
        this.action = action;
	}

	@Override
	public EPlayer getPlayer() {
        return this.player;
    }
    
	@Override
	public String getIdentifier() {
        return this.identifier;
    }
	
	@Override
    public Action getAction() {
        return this.action;
    }

    @Override
	public Cause getCause() {
		return this.cause;
	}
}
