package fr.evercraft.everapi.services.bossbar.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.BossBarEvent;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.bossbar.EBossBar;

public class ERemoveBossBarEvent extends EBossBarEvent implements BossBarEvent.Remove {
	
    public ERemoveBossBarEvent(final EPlayer player, final EBossBar bossbar, final Cause cause) {
    	super(player, bossbar, cause, Action.REMOVE);
    }
}
