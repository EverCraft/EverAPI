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
import java.util.UUID;

import fr.evercraft.everapi.server.player.EPlayer;

public interface TabListService {

	public boolean sendTabList(EPlayer player, String identifier);
	
	public boolean sendTabList(EPlayer player, String identifier, int priority);
	
	public boolean removeTabList(EPlayer player, String identifier);
	
	public boolean hasTabList(EPlayer ePlayer, String identifier);
	
	public boolean has(UUID uuid);
	
	public Optional<String> get(UUID uuid);

}
