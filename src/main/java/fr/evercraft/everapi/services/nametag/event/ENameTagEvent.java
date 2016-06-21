package fr.evercraft.everapi.services.nametag.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.NameTagEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ENameTagEvent implements NameTagEvent {
	
	private final EPlayer player;
    private final Action action;
	private final String identifier;
	private final Cause cause;

    public ENameTagEvent(final EPlayer player, String identifier, final Cause cause, final Action action) {
    	this.player = player;
        this.identifier = identifier;
        this.action = action;
        this.cause = cause;
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
