package fr.evercraft.everapi.services.nametag.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.NameTagEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EReplaceNameTagEvent extends ENameTagEvent implements NameTagEvent.Replace {
	
	private final String new_identifier;
	
    public EReplaceNameTagEvent(final EPlayer player, final String identifier, final String new_identifier, final Cause cause) {
    	super(player, identifier, cause, Action.REPLACE);
    	
    	this.new_identifier = new_identifier;
    }
    
    @Override
    public String getNewIdentifier() {
    	return this.new_identifier;
    }
}
