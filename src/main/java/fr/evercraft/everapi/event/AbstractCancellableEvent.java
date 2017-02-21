package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.impl.AbstractEvent;

public abstract class AbstractCancellableEvent extends AbstractEvent implements Cancellable {

	private boolean cancelled;
	
	public AbstractCancellableEvent() {
		this.cancelled = false;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

}
