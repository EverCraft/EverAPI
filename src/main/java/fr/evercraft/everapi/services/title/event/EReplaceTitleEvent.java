package fr.evercraft.everapi.services.title.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.event.TitleEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.title.TitleMessage;

public class EReplaceTitleEvent extends ETitleEvent implements TitleEvent.Replace {
		
	private final TitleMessage new_title;

    public EReplaceTitleEvent(final EPlayer player, final TitleMessage title, final TitleMessage new_title, final Cause cause) {
    	super(player, title, cause, Action.REPLACE);
    	
    	this.new_title = new_title;
    }

	@Override
	public String getNewIdentifier() {
		return this.new_title.getIdentifier();
	}

	@Override
	public Title getNewTitle() {
		return this.new_title.getTitle();
	}

	@Override
	public long getNewTime() {
		return this.new_title.getTime();
	}
}