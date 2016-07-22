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
package fr.evercraft.everapi.services.cooldown.event;

import java.util.List;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.CommandEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public class EResultCommandEvent extends ECommandEvent implements CommandEvent.Result {
	
	private final boolean result;

    public EResultCommandEvent(final EPlayer player, final String command, final List<String> args, final boolean result, final Cause cause) {
    	super(Action.RESULT, player, command, args, cause);
    	this.result = result;
    }

	@Override
	public boolean getResult() {
		return this.result;
	}
}