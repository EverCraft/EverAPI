package fr.evercraft.everapi.services.scoreboard.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.event.ScoreBoardEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ERemoveScoreBoardEvent extends EScoreBoardEvent implements ScoreBoardEvent.Remove {
	
    public ERemoveScoreBoardEvent(final EPlayer player, final Objective objective, final DisplaySlot display, final Cause cause) {    	
    	super(player, objective, display, cause, Action.REMOVE);
    }
}
