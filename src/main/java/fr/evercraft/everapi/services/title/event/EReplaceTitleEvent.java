/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
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