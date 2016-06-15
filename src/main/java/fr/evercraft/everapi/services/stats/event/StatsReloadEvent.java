package fr.evercraft.everapi.services.stats.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public interface StatsReloadEvent extends Event {
	
	@Override
	public Cause getCause();
}
