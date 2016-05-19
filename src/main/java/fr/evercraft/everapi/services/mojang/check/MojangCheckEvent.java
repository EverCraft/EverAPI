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
package fr.evercraft.everapi.services.mojang.check;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.mojang.check.Server.Color;

public class MojangCheckEvent implements Event {

	private final EPlugin plugin;
	private final Server server;
    private final Color color_before;
	private final Color color_after;

    public MojangCheckEvent(final EPlugin plugin, final Server server, Color color_before, Color color_after) {
    	this.plugin = plugin;
    	
    	this.server = server;
        this.color_before = color_before;
        this.color_after = color_after;
        
        this.plugin.getLogger().debug("Event MojangCheckEvent : (" + this + ")");
    }

	public Server getServer() {
        return this.server;
    }
    
	public Color getBeforeColore() {
        return this.color_before;
    }
	
    public Color getAfterColore() {
        return this.color_after;
    }

    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}

	@Override
	public String toString() {
		return "server='" + server + "', color_before='" + color_before + "', color_after='" + color_after + "'";
	}
}
