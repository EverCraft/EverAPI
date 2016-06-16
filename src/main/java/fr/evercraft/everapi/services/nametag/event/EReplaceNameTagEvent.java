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
package fr.evercraft.everapi.services.nametag.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.NameTagEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EReplaceNameTagEvent extends ENameTagEvent implements NameTagEvent.Replace {
	
	private final String new_identifier;
	
    public EReplaceNameTagEvent(final EPlayer player, final String identifier, final String new_identifier, final Cause cause) {
    	super(player, identifier, cause, Action.REPLACE);
    	
    	this.new_identifier = new_identifier;
    }
    
    @Override
    public String getNewIdentifier() {
    	return this.new_identifier;
    }
}
