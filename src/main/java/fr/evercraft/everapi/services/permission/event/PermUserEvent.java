package fr.evercraft.everapi.services.permission.event;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;

import fr.evercraft.everapi.plugin.EPlugin;

public class PermUserEvent implements Event {

    public static enum Action {
    	USER_PERMISSIONS_CHANGED,
    	USER_OPTION_CHANGED,
    	USER_GROUP_CHANGED,
    	USER_SUBGROUP_CHANGED,
    	USER_ADDED,
    	USER_REMOVED;
    };

    private final EPlugin plugin;
    private final Subject subject;
    private final Action action;

    public PermUserEvent(final EPlugin plugin, final Subject subject, final Action action) {
    	this.plugin = plugin;
    	
        this.subject = subject;
        this.action = action;
    }

    public Subject getSubject() {
        return this.subject;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public Optional<Player> getPlayer() {
    	if(this.subject.getContainingCollection().getIdentifier().equals(PermissionService.SUBJECTS_USER)) {
    		try {
    			return this.plugin.getGame().getServer().getPlayer(UUID.fromString(this.subject.getIdentifier()));
    		} catch(IllegalArgumentException e) { }
    	}
    	return Optional.empty();
    }

	@Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
