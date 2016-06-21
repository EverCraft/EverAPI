package fr.evercraft.everapi.services.bossbar.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.BossBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.bossbar.EBossBar;

public class EAddBossBarEvent extends EBossBarEvent implements BossBarEvent.Add {
	
	public EAddBossBarEvent(final EPlayer player, final EBossBar bossbar, final Cause cause) {
    	super(player, bossbar, cause, Action.ADD);
    }
}
