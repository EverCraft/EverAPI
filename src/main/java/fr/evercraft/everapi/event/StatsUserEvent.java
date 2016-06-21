package fr.evercraft.everapi.event;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;

import fr.evercraft.everapi.server.player.EPlayer;

public interface StatsUserEvent extends Event {	
	public static enum Type {
    	DEATH,
    	KILL;
    }

    public EPlayer getVictim();

    public Long getTime();
    
    public DamageType getDamageType();
    
    public Type getType();
    
    @Override
  	public Cause getCause();
    
    interface Death extends StatsUserEvent {
    	public Optional<Entity> getKiller();
    }
    
    interface Kill extends StatsUserEvent{
    	public EPlayer getKiller();
    }
}
