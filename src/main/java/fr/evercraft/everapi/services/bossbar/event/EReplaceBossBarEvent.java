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
