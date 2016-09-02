package fr.evercraft.everapi.services.sanction.manual;

import java.util.Optional;

import org.spongepowered.api.text.Text;

public interface SanctionManual {

	public Long getCreationDate();
	public Optional<Long> getExpirationDate();
	
	public default boolean isIndefinite() {
        return !this.getExpirationDate().isPresent();
    }

	public Text getReason();
	public String getSource();
	
}
