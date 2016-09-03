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
package fr.evercraft.everapi.services.sanction.manual;

import java.net.InetAddress;
import java.time.Instant;

import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.BanTypes;
import org.spongepowered.api.util.ban.Ban.Builder;

import fr.evercraft.everapi.plugin.EChat;

public interface SanctionManualIP extends SanctionManual {
	public default Ban.Ip getBan(InetAddress address) {
		Builder builder = Ban.builder()
				.address(address)
				.reason(this.getReason())
				.startDate(Instant.ofEpochMilli(this.getCreationDate()))
				.type(BanTypes.IP)
				.source(EChat.of(this.getSource()));
		
		if(this.getExpirationDate().isPresent()) {
			builder = builder.expirationDate(Instant.ofEpochMilli(this.getExpirationDate().get()));
		}
		return (Ban.Ip) builder.build();
	}
}
