package fr.evercraft.everapi.services.sanction;

import java.net.InetAddress;
import java.util.Optional;

import org.spongepowered.api.text.Text;

public interface Sanction {
	public Long getCreationDate();
	public Optional<Long> getExpirationDate();
	public Text getReason();
	public String getSource();
	public String getSourceName();
	
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
		public Optional<Jail> getJail();
	}
}
