/*
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
package fr.evercraft.everapi.event;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class BossBarEvent extends AbstractEvent {
	public static enum Action {
    	ADD,
    	REMOVE,
    	REPLACE;
    }

	private final EPlayer player;
	private final String identifier;
	private final ServerBossBar serverBossBar;
	private final Cause cause;
	
	public BossBarEvent(EPlayer player, String identifier, ServerBossBar serverBossBar, Cause cause) {
		this.player = player;
		this.identifier = identifier;
		this.serverBossBar = serverBossBar;
		this.cause = cause;
	}

	/**
	 * Retourne le joueur
	 * @return Le joueur
	 */
	public EPlayer getPlayer() {
		return this.player;
	}
    
	/**
	 * Retourne l'identifiant
	 * @return L'identifiant
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * Retourne le ServerBossBar
	 * @return ServerBossBar
	 */
	public ServerBossBar getServerBossBar() {
		return this.serverBossBar;
	}

	/**
	 * Retourne l'action
	 * @return L'action
	 */
    public abstract Action getAction();

    @Override
	public Cause getCause() {
    	return this.cause;
    }
    
    public static class Add extends BossBarEvent {
		public Add(EPlayer player, String identifier, ServerBossBar serverBossBar, Cause cause) {
			super(player, identifier, serverBossBar, cause);
		}
		
		@Override
		public Action getAction() {
			return Action.ADD;
		}
	}
    
    public static class Remove extends BossBarEvent {
    	public Remove(EPlayer player, String identifier, ServerBossBar serverBossBar, Cause cause) {
			super(player, identifier, serverBossBar, cause);
		}
		
		@Override
		public Action getAction() {
			return Action.REMOVE;
		}
    }
    
    public static class Replace extends BossBarEvent {
    	private final String newIdentifier;
    	private final ServerBossBar newServerBossBar;
    	
    	public Replace(EPlayer player, String identifier, ServerBossBar serverBossBar, String newIdentifier, ServerBossBar newServerBossBar, Cause cause) {
			super(player, identifier, serverBossBar, cause);
			
			this.newIdentifier = newIdentifier;
			this.newServerBossBar = newServerBossBar;
		}
    	
    	@Override
		public Action getAction() {
			return Action.REPLACE;
		}
    	
    	/**
    	 * Retourne le nouvelle identifiant
    	 * @return Le nouvelle identifiant
    	 */
		public String getNewIdentifier() {
			return this.newIdentifier;
		}
		
		public ServerBossBar getNewServerBossBar() {
			return this.newServerBossBar;
		}
	}
}
