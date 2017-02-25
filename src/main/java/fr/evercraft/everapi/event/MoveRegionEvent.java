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

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public abstract class MoveRegionEvent extends AbstractEvent {
	
	private final EPlayer player;
	
	private final Location<World> fromLocation;
	private final Location<World> toLocation;
	
	private final SetProtectedRegion fromRegions;
	private final SetProtectedRegion toRegions;
	
	private final SetProtectedRegion enterRegions;
	private final SetProtectedRegion exitRegions;
	
	private final Cause cause;
	
	public MoveRegionEvent(EPlayer player, Location<World> fromLocation, Location<World> toLocation,
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions, SetProtectedRegion enterRegions,
			SetProtectedRegion exitRegions, Cause cause) {
		super();
		this.player = player;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.fromRegions = fromRegions;
		this.toRegions = toRegions;
		this.enterRegions = enterRegions;
		this.exitRegions = exitRegions;
		this.cause = cause;
	}

	public EPlayer getPlayer() {
		return this.player;
	}
	
	public Location<World> getFromLocation() {
		return this.fromLocation;
	}
	
	public Location<World> getToLocation() {
		return this.toLocation;
	}
	
	public SetProtectedRegion getFromRegions() {
		return this.fromRegions;
	}
	
	public SetProtectedRegion getToRegions() {
		return this.toRegions;
	}
	
	public SetProtectedRegion getEnterRegions() {
		return this.enterRegions;
	}
	
	public SetProtectedRegion getExitRegions() {
		return this.exitRegions;
	}
	
	@Override
	public Cause getCause() {
		return this.cause;
	}
	
	public static class Pre extends MoveRegionEvent {
		
		public Pre(EPlayer player, Location<World> fromLocation, Location<World> toLocation,
				SetProtectedRegion fromRegions, SetProtectedRegion toRegions, SetProtectedRegion enterRegions,
				SetProtectedRegion exitRegions, Cause cause) {
			super(player, fromLocation, toLocation, fromRegions, toRegions, enterRegions, exitRegions, cause);
		}

		public static class Cancellable extends MoveRegionEvent implements org.spongepowered.api.event.Cancellable {

			private boolean cancelled;
			
			public Cancellable(EPlayer player, Location<World> fromLocation, Location<World> toLocation,
					SetProtectedRegion fromRegions, SetProtectedRegion toRegions, SetProtectedRegion enterRegions,
					SetProtectedRegion exitRegions, Cause cause) {
				super(player, fromLocation, toLocation, fromRegions, toRegions, enterRegions, exitRegions, cause);

				this.cancelled = false;
			}

			@Override
			public boolean isCancelled() {
				return this.cancelled;
			}

			@Override
			public void setCancelled(boolean cancel) {
				this.cancelled = cancel;
			}
		}
	}
	
	public static class Post extends MoveRegionEvent {

		public Post(EPlayer player, Location<World> fromLocation, Location<World> toLocation,
				SetProtectedRegion fromRegions, SetProtectedRegion toRegions, SetProtectedRegion enterRegions,
				SetProtectedRegion exitRegions, Cause cause) {
			super(player, fromLocation, toLocation, fromRegions, toRegions, enterRegions, exitRegions, cause);
		}
		
	}
}

