package fr.evercraft.everapi.services.tablist.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.TabListEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EReplaceTabListEvent extends ETabListEvent implements TabListEvent.Replace {
	
	private final String new_identifier;
	
    public EReplaceTabListEvent(final EPlayer player, String identifier, String new_identifier, Cause cause) {
    	super(player, identifier, cause, Action.REPLACE);
    	
    	this.new_identifier = new_identifier;
    }
    
    @Override
    public String getNewIdentifier() {
    	return this.new_identifier;
    }
}
