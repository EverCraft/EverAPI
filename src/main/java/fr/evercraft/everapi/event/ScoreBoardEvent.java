package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.server.player.EPlayer;

public interface ScoreBoardEvent extends Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

    public EPlayer getPlayer();
    
    public Action getAction();
    
    public Objective getObjective();
    
    public String getIdentifier();
    
    public DisplaySlot getDisplaySlot();
    
    @Override
	public Cause getCause();
    
    public interface Add extends ScoreBoardEvent {}
    public interface Remove extends ScoreBoardEvent {}
    public interface Replace extends ScoreBoardEvent {
    	public Objective getNewObjective();
    	public String getNewIdentifier();
    }
}
