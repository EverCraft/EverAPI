package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;


public interface TitleEvent extends Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

    public EPlayer getPlayer();
    
    public Action getAction();
    
    public String getIdentifier();
    
    public Title getTitle();
    
    public long getTime();
    
    @Override
	public Cause getCause();
    
    public interface Add extends TitleEvent {}
    public interface Remove extends TitleEvent {}
    public interface Replace extends TitleEvent {
    	public String getNewIdentifier();
        
        public Title getNewTitle();
        
        public long getNewTime();
    }
}
