package fr.evercraft.everapi.services.stats;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

public interface StatsService {
	public Optional<StatsSubject> get(UUID uuid);

	public boolean hasRegistered(UUID uuid);

	public Collection<StatsSubject> getAll();

	public LinkedHashMap<UUID, Integer> getTopDeaths(int count);

	public LinkedHashMap<UUID, Integer> getTopDeaths(int count, Long time);
	
	public LinkedHashMap<UUID, Integer> getTopKills(int count);

	public LinkedHashMap<UUID, Integer> getTopKills(int count, Long time);
	
	public LinkedHashMap<UUID, Double> getTopRatio(int count);

	public LinkedHashMap<UUID, Double> getTopRatio(int count, Long time);
	
}
