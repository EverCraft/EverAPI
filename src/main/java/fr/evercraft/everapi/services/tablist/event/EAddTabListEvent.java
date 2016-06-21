package fr.evercraft.everapi.services.tablist.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.TabListEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EAddTabListEvent extends ETabListEvent implements TabListEvent.Add {
	
    public EAddTabListEvent(final EPlayer player, String identifier, Cause cause) {
    	super(player, identifier, cause, Action.ADD);
    }
}
