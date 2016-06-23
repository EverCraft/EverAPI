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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

public interface StatsService {
	public Optional<StatsSubject> get(UUID uuid);

	public boolean hasRegistered(UUID uuid);

	public Collection<StatsSubject> getAll();

	public LinkedHashMap<UUID, Double> getTopDeaths(int count);

	public LinkedHashMap<UUID, Double> getTopDeaths(int count, Long time);
	
	public LinkedHashMap<UUID, Double> getTopKills(int count);

	public LinkedHashMap<UUID, Double> getTopKills(int count, Long time);
	
	public LinkedHashMap<UUID, Double> getTopRatio(int count);

	public LinkedHashMap<UUID, Double> getTopRatio(int count, Long time);
	
}
