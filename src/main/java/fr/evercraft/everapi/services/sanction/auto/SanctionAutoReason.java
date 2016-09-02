package fr.evercraft.everapi.services.sanction.auto;

import java.util.Collection;
import java.util.Optional;

public interface SanctionAutoReason {
	public String getName();
	public Optional<SanctionAutoLevel> getLevel(int level);
	public Collection<SanctionAutoLevel> getLevels();
}
