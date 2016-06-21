package fr.evercraft.everapi.services.title.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.event.TitleEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.title.TitleMessage;

public class ETitleEvent implements TitleEvent {
	
	private final EPlayer player;
	private final TitleMessage title;
	private final Cause cause;
	private final Action action;

    public ETitleEvent(final EPlayer player, final TitleMessage title, final Cause cause, final Action action) {
    	this.player = player;
    	this.title = title;
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
    public String getIdentifier() {
        return this.title.getIdentifier();
    }
    
    @Override
	public long getTime() {
		return this.title.getTime();
	}
    
    @Override
	public Title getTitle() {
		return this.title.getTitle();
	}
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
}
