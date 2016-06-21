package fr.evercraft.everapi.services.actionbar.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.ActionBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;

public class EAddActionBarEvent extends EActionBarEvent implements ActionBarEvent.Add {
	
	public EAddActionBarEvent(final EPlayer player, final ActionBarMessage actionBar, final Cause cause) {
    	super(player, actionBar, cause, Action.ADD);
    }
}
