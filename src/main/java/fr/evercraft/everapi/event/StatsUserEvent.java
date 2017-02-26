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

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.impl.AbstractEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class StatsUserEvent extends AbstractEvent {	
	public static enum Type {
    	DEATH,
    	KILL;
    }

	private final EPlayer victim;
	private final Long time;
	private final DamageType damageType;
	private final Type type;
	private final Cause cause;
	
    public StatsUserEvent(EPlayer victim, Long time, DamageType damageType, Type type, Cause cause) {
		super();
		this.victim = victim;
		this.time = time;
		this.damageType = damageType;
		this.type = type;
		this.cause = cause;
	}

	public EPlayer getVictim() {
    	return this.victim;
    }

    public Long getTime() {
    	return this.time;
    }
    
    public DamageType getDamageType() {
    	return this.damageType;
    }
    
    public Type getType() {
    	return this.type;
    }
    
    @Override
  	public Cause getCause() {
    	return this.cause;
    }
    
    public static class Death extends StatsUserEvent {
    	private final Optional<Entity> killer;
    	
    	public Death(EPlayer victim, Long time, DamageType damageType, Optional<Entity> killer, Cause cause) {
			super(victim, time, damageType, Type.DEATH, cause);
			
			this.killer = killer;
		}

		public Optional<Entity> getKiller() {
			return this.killer;
		}
    }
    
    public static class Kill extends StatsUserEvent{
    	private final EPlayer killer;
    	
    	public Kill(EPlayer victim, Long time, DamageType damageType, EPlayer killer, Cause cause) {
			super(victim, time, damageType, Type.DEATH, cause);
			
			this.killer = killer;
		}

		public EPlayer getKiller() {
			return this.killer;
		}
    }
}
