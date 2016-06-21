package fr.evercraft.everapi.services.actionbar.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.event.ActionBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;

public class EReplaceActionBarEvent extends EActionBarEvent implements ActionBarEvent.Replace {
	
	private final ActionBarMessage new_actionBar;
	
    public EReplaceActionBarEvent(final EPlayer player, final ActionBarMessage actionBar, final ActionBarMessage new_actionBar, final Cause cause) {
    	super(player, actionBar, cause, Action.REPLACE);
    	
    	this.new_actionBar = new_actionBar;
    }
    
    @Override
    public String getNewIdentifier() {
    	return this.new_actionBar.getIdentifier();
    }

	@Override
	public long getNewTime() {
		return this.new_actionBar.getTime();
	}

	@Override
	public Text getNewMessage() {
		return this.new_actionBar.getMessage();
	}
}
