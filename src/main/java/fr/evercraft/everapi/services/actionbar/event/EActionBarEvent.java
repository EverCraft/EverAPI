package fr.evercraft.everapi.services.actionbar.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.event.ActionBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;

public class EActionBarEvent implements ActionBarEvent {
	private final EPlayer player;
	private final ActionBarMessage actionBar;
	private final Action action;
	private final Cause cause;
	
    public EActionBarEvent(final EPlayer player, final ActionBarMessage actionBar, final Cause cause, final Action action) {
    	this.player = player;
    	this.actionBar = actionBar;
        this.action = action;
        this.cause = cause;
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
        return this.actionBar.getIdentifier();
    }
    
    @Override
    public long getTime() {
        return this.actionBar.getTime();
    }
    
    @Override
    public Text getMessage() {
        return this.actionBar.getMessage();
    }
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
}
