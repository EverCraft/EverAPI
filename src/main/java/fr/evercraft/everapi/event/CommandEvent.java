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
package fr.evercraft.everapi.event;

import java.util.List;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;

import fr.evercraft.everapi.server.player.EPlayer;

public interface CommandEvent extends Event {
	public static enum Action {
    	SEND,
    	RESULT;
    }

    public EPlayer getPlayer();
    
    public Action getAction();
    
    public String getCommand();
    
    public String getArg();
    
    public List<String> getArgs();
    
    public interface Send extends CommandEvent, Cancellable {}
    public interface Result extends CommandEvent {
    	public boolean getResult();
    }
}
