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
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.services.sanction.auto.SanctionAuto;

public interface SanctionIpSubject {
	public String getIdentifier();
	public InetAddress getAddress();
	public SocketAddress getSocketAddress();
	
	public boolean isBanManual();
	public boolean isBanAuto();
	
	public Collection<Sanction> getAllManuals();
	public Collection<SanctionAuto> getAllAutos();
	
	public boolean ban(long creation, Optional<Long> empty, Text reason, CommandSource source);
	public Collection<Sanction> pardonBan(long date, Text reason, CommandSource source);
}
