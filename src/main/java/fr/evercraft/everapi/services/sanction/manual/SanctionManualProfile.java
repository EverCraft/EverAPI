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
import java.util.Optional;

import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.Ban.Ip;
import org.spongepowered.api.util.ban.Ban.Profile;
import org.spongepowered.api.util.ban.BanTypes;
import org.spongepowered.api.util.ban.Ban.Builder;

import fr.evercraft.everapi.plugin.EChat;

public interface SanctionManualProfile extends SanctionManual {
	
	public enum Type {
		BAN_PROFILE,
		BAN_IP,
		MUTE,
		JAIL;
	}
	
	public Type getType();
	
	public interface Ban extends SanctionManualProfile {
		public default Type getType() {
			return Type.BAN_PROFILE;
		}
		
		public default Profile getBan(GameProfile profile) {
			Builder builder = org.spongepowered.api.util.ban.Ban.builder()
					.profile(profile)
					.reason(this.getReason())
					.startDate(Instant.ofEpochMilli(this.getCreationDate()))
					.type(BanTypes.PROFILE)
					.source(EChat.of(this.getSource()));
			
			if(this.getExpirationDate().isPresent()) {
				builder = builder.expirationDate(Instant.ofEpochMilli(this.getExpirationDate().get()));
			}
			return (Profile) builder.build();
		}
	}
	
	public interface BanIp extends SanctionManualProfile {
		public InetAddress getAddress();
		public default Type getType() {
			return Type.BAN_IP;
		}
		
		public default Ip getBan(GameProfile profile, InetAddress address) {
			Builder builder =  org.spongepowered.api.util.ban.Ban.builder()
					.profile(profile)
					.reason(this.getReason())
					.startDate(Instant.ofEpochMilli(this.getCreationDate()))
					.profile(profile)
					.type(BanTypes.IP)
					.address(address)
					.source(EChat.of(this.getSource()));
			
			if(this.getExpirationDate().isPresent()) {
				builder = builder.expirationDate(Instant.ofEpochMilli(this.getExpirationDate().get()));
			}
			return (Ip) builder.build();
		}
	}
	
	public interface Mute extends SanctionManualProfile {
		public default Type getType() {
			return Type.MUTE;
		}
	}
	
	public interface Jail extends SanctionManualProfile {
		public String getJailName();
		public Optional<Jail> getJail();
		
		public default Type getType() {
			return Type.JAIL;
		}
	}
}
