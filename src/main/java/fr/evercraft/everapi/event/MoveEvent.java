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

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public interface MoveEvent extends Event {
	
	public EPlayer getPlayer();
	
	public Location<World> getFromLocation();
	public Location<World> getToLocation();
	
	public SetProtectedRegion getFromRegions();
	public SetProtectedRegion getToRegions();
	
	public SetProtectedRegion getEnterRegions();
	public SetProtectedRegion getExitRegions();
	
	@Override
	public Cause getCause();
}

