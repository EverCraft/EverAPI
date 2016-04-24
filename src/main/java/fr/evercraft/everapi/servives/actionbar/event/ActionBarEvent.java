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
package fr.evercraft.everapi.servives.actionbar.event;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.servives.actionbar.ActionBarMessage;

public class ActionBarEvent implements Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }
	
	private final EPlugin plugin;
    private final Action action;
	private final ActionBarMessage actionBar;

    public ActionBarEvent(final EPlugin plugin, final ActionBarMessage actionBar, final Action action) {
    	this.plugin = plugin;
    	
    	this.actionBar = actionBar;
        this.action = action;
    }

    public Player getPlayer() {
        return this.actionBar.getPlayer();
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public ActionBarMessage getActionBar() {
        return this.actionBar;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
