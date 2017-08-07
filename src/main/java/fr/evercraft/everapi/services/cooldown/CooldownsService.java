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
package fr.evercraft.everapi.services.cooldown;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CooldownsService {
	public final static String NAME_DEFAULT = "default";
	public final static Long DEFAULT = 0L;
	
	public Optional<CooldownsSubject> get(UUID identifier);
	public boolean hasRegistered(UUID identifier);
	
	public Collection<CooldownsSubject> getAll();
}
