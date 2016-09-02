package fr.evercraft.everapi.services.sanction.auto;

import java.util.Optional;

public interface SanctionAuto {	
	public Long getCreationDate();
	public Optional<Long> getExpirationDate();

	public SanctionAutoType getType();
	public SanctionAutoReason getReason();
	public int getLevel();
	public String getSource();
	
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
}
