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
package fr.evercraft.everapi.services.nametag.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.NameTagEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class ENameTagEvent implements NameTagEvent {
	
	private final EPlayer player;
    private final Action action;
	private final String identifier;
	private final Cause cause;

    public ENameTagEvent(final EPlayer player, String identifier, final Cause cause, final Action action) {
    	this.player = player;
        this.identifier = identifier;
        this.action = action;
        this.cause = cause;
    }

    @Override
	public EPlayer getPlayer() {
        return this.player;
    }
    
	@Override
	public String getIdentifier() {
        return this.identifier;
    }
	
	@Override
    public Action getAction() {
        return this.action;
    }

    @Override
	public Cause getCause() {
		return this.cause;
	}
}
