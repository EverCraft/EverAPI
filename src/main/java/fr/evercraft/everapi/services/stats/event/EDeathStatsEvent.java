/**
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
package fr.evercraft.everapi.services.stats.event;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;

import fr.evercraft.everapi.server.player.EPlayer;

public class EDeathStatsEvent extends EStatsEvent implements StatsEvent.Death {	

	private final Optional<Entity> killer;

    public EDeathStatsEvent(EPlayer victim, Entity killer, DamageType damage, Long time, Cause cause) {
    	super(Type.DEATH, victim, damage, time, cause);
    	this.killer = Optional.of(killer);
    }
    
    public EDeathStatsEvent(EPlayer victim, DamageType damage, Long time, Cause cause) {
    	super(Type.DEATH, victim, damage, time, cause);
    	this.killer = Optional.empty();
    }

	@Override
	public Optional<Entity> getKiller() {
		return this.killer;
	}

}
