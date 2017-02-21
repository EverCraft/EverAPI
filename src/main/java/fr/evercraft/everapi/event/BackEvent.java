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

public class BackEvent extends AbstractCancellableEvent {
	
	private final EPlayer player;
	private final Optional<Transform<World>> beforeLocation;
	private final Optional<Transform<World>> afterLocation;
	private final Cause cause;
	
	
	public BackEvent(EPlayer player, Optional<Transform<World>> beforeLocation,
			Optional<Transform<World>> afterLocation, Cause cause) {
		super();
		this.player = player;
		this.beforeLocation = beforeLocation;
		this.afterLocation = afterLocation;
		this.cause = cause;
	}

	public EPlayer getPlayer() {
		return this.player;
	}
    
	public Optional<Transform<World>> getBeforeLocation() {
		return this.beforeLocation;
	}
	
    public Optional<Transform<World>> getAfterLocation() {
    	return this.afterLocation;
    }
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
}

