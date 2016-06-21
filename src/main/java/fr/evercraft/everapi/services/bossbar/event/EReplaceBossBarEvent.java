package fr.evercraft.everapi.services.bossbar.event;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.BossBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.bossbar.EBossBar;

public class EReplaceBossBarEvent extends EBossBarEvent implements BossBarEvent.Replace {
	
	private final EBossBar new_bossbar;
	
    public EReplaceBossBarEvent(final EPlayer player, final EBossBar bossbar, final EBossBar new_bossbar, final Cause cause) {
    	super(player, bossbar, cause, Action.REPLACE);
    	
    	this.new_bossbar = new_bossbar;
    }
    
    @Override
    public String getNewIdentifier() {
    	return this.new_bossbar.getIdentifier();
    }

	@Override
	public ServerBossBar getNewServerBossBar() {
		return this.new_bossbar.getServerBossBar();
	}
}
