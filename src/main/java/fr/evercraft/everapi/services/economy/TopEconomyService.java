package fr.evercraft.everapi.services.economy;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.spongepowered.api.service.economy.Currency;

public interface TopEconomyService {
	public LinkedHashMap<UUID, BigDecimal> topUniqueAccount(final int count);
	public LinkedHashMap<UUID, BigDecimal> topUniqueAccount(final Currency currency, final int count);
}
