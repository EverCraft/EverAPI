/*
 * EverAPI
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
