package fr.evercraft.everapi.services.sanction.manual;

import java.util.Optional;

public interface SanctionManualProfile extends SanctionManual {
	
	public interface Ban extends SanctionManualProfile {}
	public interface Mute extends SanctionManualProfile {}
	public interface Jail extends SanctionManualProfile {
		public String getJailName();
		public Optional<Jail> getJail();
	}
}
