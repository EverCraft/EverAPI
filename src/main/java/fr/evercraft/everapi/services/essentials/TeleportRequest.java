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
package fr.evercraft.everapi.services.essentials;

import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public class TeleportRequest {
	public enum Type {
		TPA,
		TPAHERE;
	}
	
	private final Type type;
	private Optional<Long> time;
	private boolean expire;
	
	private Optional<Transform<World>> location;
	
	public TeleportRequest(final Type type, final long delay) {
		this.type = type;
		this.setDelay(delay);
		this.expire = false;
		this.location = Optional.empty();
	}
	
	public TeleportRequest(final Type type, final long delay, final Transform<World> location) {
		this.type = type;
		this.setDelay(delay);
		this.expire = false;
		this.location = Optional.ofNullable(location);
	}

	public Type getType() {
		return this.type;
	}
	
	public Optional<Long> getTime() {
		return this.time;
	}

	public boolean isExpire() {
		return this.expire;
	}
	
	public void setExpire(boolean expire) {
		this.expire = expire;
	}

	public void setDelay(long delay) {
		if (delay > 0) {
			this.time = Optional.of(delay + System.currentTimeMillis());
		} else {
			this.time = Optional.empty();
		}
	}

	public Optional<Transform<World>> getLocation() {
		return location;
	}

	public void setLocation(@Nullable Transform<World> location) {
		this.location = Optional.ofNullable(location);
	}
}
