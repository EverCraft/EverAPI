package fr.evercraft.everapi.services.essentials.event;

import java.util.UUID;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.essentials.Mail;

public class MailEvent implements Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	READ;
    }
	
	private final EPlugin plugin;
    private final UUID uuid;
    private final Mail mail;
    private final Action action;

    public MailEvent(final EPlugin plugin, final UUID uuid, final Mail mail, final Action action) {
    	this.plugin = plugin;
    	
    	this.uuid = uuid;
        this.mail = mail;
        this.action = action;
    }

    public UUID getPlayer() {
        return this.uuid;
    }
    
    public Mail getMail() {
        return this.mail;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
