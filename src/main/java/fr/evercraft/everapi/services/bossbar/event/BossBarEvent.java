/**
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.services.bossbar.event;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public class BossBarEvent implements Event {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }
	
	private final EPlugin plugin;
    private final Action action;
    private final int priority;
	private final ServerBossBar bossbar;
	private final EPlayer player;

    public BossBarEvent(final EPlugin plugin, final EPlayer player, final int priority, final ServerBossBar bossbar, final Action action) {
    	this.plugin = plugin;
    	
    	this.player = player;    	
    	this.priority = priority;
    	this.action = action;
        this.bossbar = bossbar;
    }

    public EPlayer getEPlayer() {
        return this.player;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public ServerBossBar getServerBossBar() {
        return this.bossbar;
    }
    
    @Override
	public Cause getCause() {
		return Cause.source(this.plugin).build();
	}
}
