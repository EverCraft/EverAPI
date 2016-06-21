package fr.evercraft.everapi.services.nametag.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.NameTagEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EAddNameTagEvent extends ENameTagEvent implements NameTagEvent.Add {
	
	public EAddNameTagEvent(final EPlayer player, final String identifier, final Cause cause) {
    	super(player, identifier, cause, Action.ADD);
    }
}
