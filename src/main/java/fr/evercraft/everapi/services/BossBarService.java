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
package fr.evercraft.everapi.services;

import java.util.Optional;

import org.spongepowered.api.boss.ServerBossBar;

import fr.evercraft.everapi.server.player.EPlayer;

public interface BossBarService {
	
	boolean add(EPlayer player, String identifier, ServerBossBar bossbar, int priority, Optional<Long> stay);
	boolean remove(EPlayer player, String identifier);
	Optional<ServerBossBar> get(EPlayer player, String identifier);

	int getPriority(String identifier);
	
	default boolean add(EPlayer player, String identifier, ServerBossBar bossbar, Optional<Integer> priority, Optional<Long> stay) {
		return this.add(player, identifier, bossbar, priority.orElse(this.getPriority(identifier)), stay);
	}
}
