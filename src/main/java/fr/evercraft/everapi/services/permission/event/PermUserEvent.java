package fr.evercraft.everapi.services.permission.event;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

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
    private Optional<EPlayer> player;

    public PermUserEvent(final EPlugin plugin, final Subject subject, final Action action) {
    	this.plugin = plugin;
    	
        this.subject = subject;
        this.action = action;
        
        if(this.subject.getContainingCollection().getIdentifier().equals(PermissionService.SUBJECTS_USER)) {
    		try {
    			this.player = this.plugin.getEServer().getEPlayer(UUID.fromString(this.subject.getIdentifier()));
    		} catch(IllegalArgumentException e) {
    			this.player = Optional.empty();
    		}
    	} else {
    		this.player = Optional.empty();
    	}
    }

    public Subject getSubject() {
        return this.subject;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public Optional<EPlayer> getEPlayer() {
    	return this.player;
    }
    
    public Optional<Player> getPlayer() {
    	if(this.player.isPresent()) {
    		return Optional.of(this.player.get());
    	}
    	return Optional.empty();
    }

	@Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
