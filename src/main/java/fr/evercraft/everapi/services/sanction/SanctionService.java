package fr.evercraft.everapi.services.sanction;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.service.ban.BanService;

public interface SanctionService extends BanService {
	public Optional<SubjectUserSanction> get(UUID uuid);
	public boolean hasRegistered(UUID uuid);
}
