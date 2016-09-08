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
package fr.evercraft.everapi.services.sanction;

import java.net.InetAddress;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.service.ban.BanService;

import fr.evercraft.everapi.services.sanction.auto.SanctionAuto;

public interface SanctionService extends BanService {
	public Optional<SanctionUserSubject> get(UUID uuid);
	public boolean hasRegistered(UUID uuid);
	
	Optional<SanctionIpSubject> get(InetAddress address);
	public boolean hasRegistered(InetAddress address);
	
	public Optional<SanctionAuto.Reason> getReason(String identifier);
}
