package fr.evercraft.everapi.services.pvp;

import java.util.Optional;
import java.util.UUID;

public interface PVPService {
	public boolean isFight(UUID uuid);
	public Optional<Long> getTime(UUID uuid);
}
