package fr.evercraft.everapi.services.scoreboard.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.event.ScoreBoardEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EScoreBoardEvent implements ScoreBoardEvent {
	
	private final EPlayer player;
    private final DisplaySlot display;
	private final Objective objective;
	private final Cause cause;
	private final Action action;

    public EScoreBoardEvent(final EPlayer player, final Objective objective, final DisplaySlot display, final Cause cause, final Action action) {    	
    	this.player = player;    	
    	this.objective = objective;
        this.display = display;
        this.cause = cause;
        this.action = action;
    }

    @Override
    public EPlayer getPlayer() {
        return this.player;
    }
    
    @Override
    public Action getAction() {
        return this.action;
    }
    
    @Override
    public Objective getObjective() {
        return this.objective;
    }
    
    @Override
	public String getIdentifier() {
		return this.objective.getName();
	}
    
    @Override
    public DisplaySlot getDisplaySlot() {
        return this.display;
    }
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
}
