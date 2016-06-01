package fr.evercraft.everapi.services.bossbar;

import java.util.Optional;

import org.spongepowered.api.boss.ServerBossBar;

import fr.evercraft.everapi.server.player.EPlayer;

public interface BossBarService {

	public boolean add(EPlayer player, String identifier, ServerBossBar bossbar);

	public boolean add(EPlayer player, int priority, ServerBossBar bossbar);
	
	public boolean remove(EPlayer player, String identifier);

	public boolean remove(EPlayer player, int priority);

	public Optional<ServerBossBar> get(EPlayer ePlayer, String identifier);
	
	public Optional<ServerBossBar> get(EPlayer ePlayer, int priority);
	
}
