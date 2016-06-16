package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public interface StatsReloadEvent extends Event {
	
	@Override
	public Cause getCause();
}
