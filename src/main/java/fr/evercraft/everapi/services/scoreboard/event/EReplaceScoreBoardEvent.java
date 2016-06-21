package fr.evercraft.everapi.services.scoreboard.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.event.ScoreBoardEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EReplaceScoreBoardEvent extends EScoreBoardEvent implements ScoreBoardEvent.Replace {
	
	public final Objective new_objective;
	
    public EReplaceScoreBoardEvent(final EPlayer player, final Objective objective,  final Objective new_objective, final DisplaySlot display, final Cause cause) {    	
    	super(player, objective, display, cause, Action.REPLACE);
    	
    	this.new_objective = new_objective;
    }
    
    @Override
    public Objective getNewObjective() {
    	return this.new_objective;
    }
    
    @Override
   	public String getNewIdentifier() {
   		return this.new_objective.getName();
   	}
}
