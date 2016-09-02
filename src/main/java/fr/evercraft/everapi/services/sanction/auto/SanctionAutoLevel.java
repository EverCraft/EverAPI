package fr.evercraft.everapi.services.sanction.auto;

import java.util.Optional;

import fr.evercraft.everapi.services.sanction.Jail;

public interface SanctionAutoLevel {
	public SanctionAutoType getType();
	public Optional<Long> getDuration();
	public String getReason();	
	public Optional<Jail> getJail();
	
	public default boolean isIndefinite() {
        return !this.getDuration().isPresent();
    }
}
