package fr.evercraft.everapi.services.bossbar;

import org.spongepowered.api.boss.ServerBossBar;

import fr.evercraft.everapi.server.player.EPlayer;

public interface BossBarService {

	public boolean add(EPlayer player, String identifier, ServerBossBar bossbar);

	public boolean add(EPlayer player, int priority, ServerBossBar bossbar);
	
	public boolean remove(EPlayer player, String identifier);

	public boolean remove(EPlayer player, int priority);
	
}
