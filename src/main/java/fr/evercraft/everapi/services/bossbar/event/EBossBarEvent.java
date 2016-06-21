package fr.evercraft.everapi.services.bossbar.event;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.BossBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.bossbar.EBossBar;

public class EBossBarEvent implements BossBarEvent {

	private final EPlayer player;
	private final EBossBar bossbar;
	private final Cause cause;
	private final Action action;

    public EBossBarEvent(final EPlayer player, final EBossBar bossbar, final Cause cause, final Action action) {
    	this.player = player; 
    	this.bossbar = bossbar;
        this.cause = cause;
        this.action = action;
    }

    @Override
    public EPlayer getPlayer() {
        return this.player;
    }
    
    @Override
    public Action getAction() {
        return this.action;
    }
    
    @Override
    public String getIdentifier() {
        return this.bossbar.getIdentifier();
    }
    
    @Override
    public ServerBossBar getServerBossBar() {
        return this.bossbar.getServerBossBar();
    }
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
}
