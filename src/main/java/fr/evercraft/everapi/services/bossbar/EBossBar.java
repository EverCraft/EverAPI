package fr.evercraft.everapi.services.bossbar;

import org.spongepowered.api.boss.ServerBossBar;

public class EBossBar {

	private final int priority;
	
	private final ServerBossBar bossbar;
	
	public EBossBar(final int priority, final ServerBossBar bossbar) {
		this.priority = priority;
		this.bossbar = bossbar;
	}

	public int getPriority() {
		return this.priority;
	}

	public ServerBossBar getServerBossBar() {
		return this.bossbar;
	}
	
}
