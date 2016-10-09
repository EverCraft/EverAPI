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
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ban.Ban;

import fr.evercraft.everapi.services.sanction.Jail;

public interface SanctionAuto {	
	
	public UUID getProfile();
	
	public Long getCreationDate();
	public Optional<Long> getExpirationDate();

	public Type getType();
	public Reason getReason();
	public Text getReasonText();
	public Optional<Level> getLevel();
	public int getLevelNumber();
	public String getSource();
	
	public Optional<Jail> getJail();
	
	public Optional<String> getOption();
	Optional<InetAddress> getAddress();
	
	public Optional<Ban.Profile> getBan(GameProfile profile);
	public Optional<Ban.Ip> getBan(GameProfile profile, InetAddress address);
	
	public default boolean isBan() {
		return this.getType().isBan();
	}
	
	public default boolean isBanIP() {
		return this.getType().isBanIP();
	}
	
	public default boolean isMute() {
		return this.getType().isMute();
	}
	
	public default boolean isJail() {
		return this.getType().isJail();
	}	
	
	public default boolean isIndefinite() {
        return !this.getExpirationDate().isPresent();
    }
	
	/*
	 * Pardon
	 */
	
	public Optional<Long> getPardonDate();
	public Optional<String> getPardonSource();
	public Optional<Text> getPardonReason();
	
	public default boolean isPardon() {
        return this.getPardonDate().isPresent();
    }
	
	public default boolean isExpire() {
		if(this.isPardon()) {
			return true;
		}
		
		if(this.isIndefinite()) {
			return false;
		}
		
        return this.getExpirationDate().orElse(0L) > System.currentTimeMillis();
    }
	
	public interface Reason {
		public String getName();
		public Optional<Level> getLevel(int level);
		public Collection<Level> getLevels();
	}
	
	public interface Level {
		public SanctionAuto.Type getType();
		public Optional<String> getDuration();
		public Optional<Long> getExpirationDate(long creation);
		public String getReason();	
		public Optional<Jail> getJail();
		public Optional<InetAddress> getAddress();
		public Optional<String> getOption();
		
		public default boolean isIndefinite() {
	        return !this.getDuration().isPresent();
	    }
	}
	
	public enum Type {
		BAN_PROFILE(true, false, false, false),
		BAN_IP(false, true, false, false),
		MUTE(false, false, true, false),
		JAIL(false, false, false, true),
		BAN_PROFILE_AND_BAN_IP(true, true, false, false),
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
