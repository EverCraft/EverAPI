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

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.EServer;

public interface Sanction {
	public Long getCreationDate();
	public Optional<Long> getExpirationDate();
	public Text getReason();
	public String getSource();
	
	public default String getSourceName(Server server) {
		if (this.getSource().length() == EServer.UUID_LENGTH) {
			try {
				Optional<Player> player = server.getPlayer(UUID.fromString(this.getSource()));
				if (player.isPresent()) {
					return player.get().getName();
				}
			} catch(IllegalArgumentException e) {}
		}
		return this.getSource();
	}
	
	public default boolean isIndefinite() {
        return !this.getExpirationDate().isPresent();
    }
	
	public default boolean isExpire() {
		if(this.isPardon()) {
			return true;
		}
        return this.isExpireDate();
    }
	
	public default boolean isExpireDate() {
		if(this.isIndefinite()) {
			return false;
		}
        return this.getExpirationDate().get() < System.currentTimeMillis();
    }
	
	
	
	/*
	 * Pardon
	 */
	
	public Optional<Long> getPardonDate();
	public Optional<String> getPardonSource();
	public Optional<Text> getPardonReason();
	
	public default Optional<String> getPardonSourceName(Server server) {
		if (this.getSource().length() == EServer.UUID_LENGTH) {
			try {
				Optional<Player> player = server.getPlayer(UUID.fromString(this.getSource()));
				if (player.isPresent()) {
					return  Optional.of(player.get().getName());
				}
			} catch(IllegalArgumentException e) {}
		}
		return Optional.of(this.getSource());
	}
	
	public default boolean isPardon() {
        return this.getPardonDate().isPresent();
    }
	
	
	
	/*
	 * Sanctions
	 */
	
	public interface SanctionBanProfile extends Sanction {
		
	}
	
	public interface SanctionBanIp extends Sanction {
		public InetAddress getAddress();
	}
	
	public interface SanctionMute extends Sanction {
		
	}
	
	public interface SanctionJail extends Sanction {
		public String getJailName();
		public Optional<Jail> getJail();
	}
}
