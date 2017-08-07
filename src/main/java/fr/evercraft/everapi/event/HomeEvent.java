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
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class HomeEvent extends AbstractCancellableEvent {	
	public static enum Action {
    	ADD,
    	REMOVE,
    	MOVE;
    }
	
	private final EPlayer player;
	private final String name;
	private final Cause cause;
	
	public HomeEvent(EPlayer player, String name, Cause cause) {
		super();
		this.player = player;
		this.name = name;
		this.cause = cause;
	}

	public abstract Action getAction();
	
	public EPlayer getPlayer() {
		return this.player;
	}
	
	public String getName() {
		return this.name;
	}
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
	
	public static class Add extends HomeEvent {
		private final Transform<World> location;
		
		public Add(EPlayer player, String name, Transform<World> location, Cause cause) {
			super(player, name, cause);
			this.location = location;
		}
		
		public Transform<World> getLocation() {
			return this.location;
		}

		@Override
		public Action getAction() {
			return Action.ADD;
		}
	}
	public static class Remove extends HomeEvent {
		private final Optional<Transform<World>> location;
		
		public Remove(EPlayer player, String name, Optional<Transform<World>> location, Cause cause) {
			super(player, name, cause);
			this.location = location;
		}
		
		public Optional<Transform<World>> getLocation() {
			return this.location;
		}

		@Override
		public Action getAction() {
			return Action.REMOVE;
		}
	}
	public static class Move extends HomeEvent {
		private final Optional<Transform<World>> beforeLocation;
		private final Transform<World> afterLocation;
		
		public Move(EPlayer player, String name, Optional<Transform<World>> beforeLocation,
				Transform<World> afterLocation, Cause cause) {
			super(player, name, cause);
			this.beforeLocation = beforeLocation;
			this.afterLocation = afterLocation;
		}

		public Optional<Transform<World>> getBeforeLocation() {
			return this.beforeLocation;
		}
		
		public Transform<World> getAfterLocation() {
			return this.afterLocation;
		}
		
		@Override
		public Action getAction() {
			return Action.MOVE;
		}
	}
}
