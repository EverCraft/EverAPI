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
import java.util.Optional;

import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.util.ban.Ban.Ip;
import org.spongepowered.api.util.ban.Ban.Profile;

public interface SanctionManualProfile extends SanctionManual {
	
	public enum Type {
		BAN_PROFILE,
		BAN_IP,
		MUTE,
		JAIL;
		
		public static Optional<Type> get(final String name) {
			Type entity = null;
			int cpt = 0;
			while(cpt < values().length && entity == null){
				if (values()[cpt].name().equalsIgnoreCase(name)) {
					entity = values()[cpt];
				}
				cpt++;
			}
			return Optional.ofNullable(entity);
		}
	}
	
	public Type getType();
	
	public interface Ban extends SanctionManualProfile {
		public default Type getType() {
			return Type.BAN_PROFILE;
		}
		
		public Profile getBan(GameProfile profile);
	}
	
	public interface BanIp extends SanctionManualProfile {
		public InetAddress getAddress();
		public default Type getType() {
			return Type.BAN_IP;
		}
		
		public Ip getBan(GameProfile profile, InetAddress address);
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
