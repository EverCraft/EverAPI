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
package fr.evercraft.everapi.services.sanction.auto;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.BanTypes;
import org.spongepowered.api.util.ban.Ban.Builder;

import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.services.jail.Jail;
import fr.evercraft.everapi.services.sanction.Sanction;

public interface SanctionAuto extends Sanction {	
	
	public UUID getProfile();
	

	public Type getTypeSanction();
	public Reason getReasonSanction();
	public Level getLevel();
	public int getLevelNumber();
	
	public default boolean isBan() {
		return this.getTypeSanction().isBan();
	}
	
	public default boolean isBanIP() {
		return this.getTypeSanction().isBanIP();
	}
	
	public default boolean isMute() {
		return this.getTypeSanction().isMute();
	}
	
	public default boolean isJail() {
		return this.getTypeSanction().isJail();
	}
	
	/*
	 * Sanctions
	 */
	
	public interface SanctionBanProfile extends SanctionAuto, Sanction.SanctionBanProfile {
		public default Type getType() {
	        return Type.BAN_PROFILE;
	    }
		
		public default Ban.Profile getBan(GameProfile profile) {
			Builder builder = Ban.builder()
					.type(BanTypes.PROFILE)
					.profile(profile)
					.reason(this.getReason())
					.startDate(Instant.ofEpochMilli(this.getCreationDate()))
					.source(EChat.of(this.getSource()));
			
			if(this.getExpirationDate().isPresent()) {
				builder = builder.expirationDate(Instant.ofEpochMilli(this.getExpirationDate().get()));
			}
			return (Ban.Profile) builder.build();
		}
	}
	
	public interface SanctionBanIp extends SanctionAuto, Sanction.SanctionBanIp {
		public default Type getType() {
	        return Type.BAN_IP;
	    }
		
		public default Ban.Ip getBan(GameProfile profile, InetAddress address) {
			Builder builder = Ban.builder()
					.type(BanTypes.IP)
					.address(address)
					.reason(this.getReason())
					.startDate(Instant.ofEpochMilli(this.getCreationDate()))
					.source(EChat.of(this.getSource()));
			
			if(this.getExpirationDate().isPresent()) {
				builder = builder.expirationDate(Instant.ofEpochMilli(this.getExpirationDate().get()));
			}
			return (Ban.Ip) builder.build();
		}
	}
	
	public interface SanctionBanProfileAndIp extends SanctionAuto, SanctionBanProfile, SanctionBanIp {
		public default Type getType() {
	        return Type.BAN_PROFILE_AND_IP;
	    }
	}
	
	public interface SanctionMute extends SanctionAuto, Sanction.SanctionMute {
		public default Type getType() {
	        return Type.MUTE;
	    }
	}
	
	public interface SanctionJail extends SanctionAuto, Sanction.SanctionJail {
		public default Type getType() {
	        return Type.JAIL;
	    }
		
		public String getJailName();
		public Optional<Jail> getJail();
	}
	
	public interface SanctionMuteAndJail extends SanctionAuto, SanctionMute, SanctionJail {
		public default Type getType() {
	        return Type.MUTE_AND_JAIL;
	    }
	}
	
	/*
	 * Type
	 */
	
	public interface Reason {
		public String getName();
		public Level getLevel(int level);
		public TreeMap<Integer, Level> getLevels();
	}
	
	public interface Level {
		public SanctionAuto.Type getType();
		public Optional<String> getDuration();
		public Optional<Long> getExpirationDate(long creation);
		public Text getReason();	
		public Optional<Jail> getJail();
		
		public default boolean isIndefinite() {
	        return !this.getDuration().isPresent();
	    }
	}
	
	
	public enum Type {
		BAN_PROFILE(true, false, false, false),
		BAN_IP(false, true, false, false),
		MUTE(false, false, true, false),
		JAIL(false, false, false, true),
		BAN_PROFILE_AND_IP(true, true, false, false),
		MUTE_AND_JAIL(false, false, true, true);
		
		private final boolean ban;
		private final boolean ban_ip;
		private final boolean mute;
		private final boolean jail;
		
		Type(final boolean ban, final boolean ban_ip, final boolean mute, final boolean jail) {
			this.ban = ban;
			this.ban_ip = ban_ip;
			this.mute = mute;
			this.jail = jail;
		}
		
		public boolean isBan() {
			return this.ban;
		}
		
		public boolean isBanIP() {
			return this.ban_ip;
		}
		
		public boolean isMute() {
			return this.mute;
		}
		
		public boolean isJail() {
			return this.jail;
		}
		
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
}
