package fr.evercraft.everapi.services.title.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.TitleEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.title.TitleMessage;

public class ERemoveTitleEvent extends ETitleEvent implements TitleEvent.Remove {

    public ERemoveTitleEvent(final EPlayer player, final TitleMessage title, final Cause cause) {
    	super(player, title, cause, Action.REMOVE);
    }
}
