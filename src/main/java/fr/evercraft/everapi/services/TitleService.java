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

import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.title.TitleMessage;

public interface TitleService {

	public boolean send(EPlayer player, String identifier, Title title);
	
	public boolean send(EPlayer player, String identifier, int priority, Title title);
	
	public boolean remove(EPlayer player, String identifier);
	
	public boolean has(UUID uuid);
	public Optional<TitleMessage> get(UUID uuid);
}
