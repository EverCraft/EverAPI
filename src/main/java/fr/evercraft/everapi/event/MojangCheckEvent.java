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

import fr.evercraft.everapi.services.mojang.check.MojangServer;
import fr.evercraft.everapi.services.mojang.check.MojangServer.Status;

public class MojangCheckEvent extends AbstractEvent {
	
	private final MojangServer server;
	private final Status beforeStatus;
	private final Status afterStatus;
	private final Cause cause;
	
    public MojangCheckEvent(MojangServer server, Status beforeStatus, Status afterStatus, Cause cause) {
		this.server = server;
		this.beforeStatus = beforeStatus;
		this.afterStatus = afterStatus;
		this.cause = cause;
	}

	public MojangServer getServer() {
    	return this.server;
    }
    
	public Status getBeforeStatus() {
		return this.beforeStatus;
	}
	
    public Status getAfterStatus() {
    	return this.afterStatus;
    }

    @Override
	public Cause getCause() {
    	return this.cause;
    }
}

