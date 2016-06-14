package fr.evercraft.everapi.services.stats;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface StatsService {
	public Optional<StatsSubject> get(UUID uuid);

	public boolean hasRegistered(UUID uuid);

	Collection<StatsSubject> getAll();
}
