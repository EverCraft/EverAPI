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

import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;

public interface HomeEvent extends Event, Cancellable {	
	public static enum Action {
    	ADD,
    	REMOVE,
    	MOVE;
    }
	
	public EPlayer getPlayer();
	
	public String getName();
    
    public Action getAction();
    
    @Override
	public Cause getCause();
	
	public interface Add extends HomeEvent {
		public Transform<World> getLocation();
	}
	public interface Remove extends HomeEvent {
		public Optional<Transform<World>> getLocation();
	}
	public interface Move extends HomeEvent {
		public Optional<Transform<World>> getBeforeLocation();
		
		public Transform<World> getAfterLocation();
	}
}