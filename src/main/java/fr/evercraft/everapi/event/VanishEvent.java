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

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class VanishEvent extends AbstractCancellableEvent {
	
	private final EPlayer player;
	private final Cause cause;
	
	public VanishEvent(EPlayer player, Cause cause) {
		super();
		this.player = player;
		this.cause = cause;
	}

	public EPlayer getPlayer() {
		return this.player;
	}
	
    public abstract boolean getValue();
    
    @Override
	public Cause getCause() {
    	return this.cause;
    }
	
	public static class Enable extends VanishEvent {
		public Enable(EPlayer player, Cause cause) {
			super(player, cause);
		}
		
		@Override
		public boolean getValue() {
			return true;
		}
	}
	
	public static class Disable extends VanishEvent {
		public Disable(EPlayer player, Cause cause) {
			super(player, cause);
		}
		
		@Override
		public boolean getValue() {
			return false;
		}
	}
}

