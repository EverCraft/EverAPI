package fr.evercraft.everapi.services.bossbar;

import org.spongepowered.api.boss.ServerBossBar;

public class EBossBar {

	private final String identifier;
	
	private final ServerBossBar bossbar;
	
	public EBossBar(final String identifier, final ServerBossBar bossbar) {
		this.identifier = identifier;
		this.bossbar = bossbar;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public ServerBossBar getServerBossBar() {
		return this.bossbar;
	}
	
}
