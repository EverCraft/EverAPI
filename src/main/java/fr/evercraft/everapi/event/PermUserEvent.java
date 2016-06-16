package fr.evercraft.everapi.event;

import java.util.Optional;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.Subject;

import fr.evercraft.everapi.server.player.EPlayer;

public interface PermUserEvent extends Event {

    public static enum Action {
    	USER_PERMISSION_CHANGED,
    	USER_OPTION_CHANGED,
    	USER_GROUP_CHANGED,
    	USER_SUBGROUP_CHANGED,
    	USER_ADDED,
    	USER_REMOVED;
    };

    public Subject getSubject();
    
    public Action getAction();
    
    public Optional<EPlayer> getPlayer();

	@Override
	public Cause getCause();
	
	public interface Permission {}
	public interface Option {}
	public interface Group {}
	public interface SubGroup {}
	public interface Add {}
	public interface Remove {}
	
}
