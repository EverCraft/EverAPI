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
package fr.evercraft.everapi.services.essentials.event;

import java.util.UUID;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;

public class MuteEvent implements Event {
	public static enum Action {
    	ADD,
    	REMOVE;
    }
	
	private final EPlugin plugin;
    private final UUID uuid;
    private final boolean value;

    public MuteEvent(final EPlugin plugin, final UUID uuid, final boolean value) {
    	this.plugin = plugin;
    	
    	this.uuid = uuid;
        this.value = value;
    }

    public UUID getPlayer() {
        return this.uuid;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}

