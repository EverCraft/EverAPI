/**
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
