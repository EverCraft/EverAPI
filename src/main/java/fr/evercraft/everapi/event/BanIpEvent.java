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

import java.net.InetAddress;
import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionBanIp;

public abstract class BanIpEvent extends AbstractEvent {
	
	private final EUser user;
	private final Optional<EPlayer> player;
	private final InetAddress address;
	private final SanctionBanIp sanction;
	
	private final Text reason;
	private final long creationDate;
	private final Optional<Long> expirationDate;
	private final String source;
	private final Cause cause;
	
	public BanIpEvent(EUser user, Optional<EPlayer> player, InetAddress address, Text reason, long creationDate, 
			Optional<Long> expirationDate, String source, SanctionBanIp sanction, Cause cause) {
		super();
		this.user = user;
		this.player = player;
		this.address = address;
		this.sanction = sanction;
		this.reason = reason;
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
		this.source = source;
		this.cause = cause;
	}

	public abstract boolean getValue();
	
	public EUser getUser() {
		return this.user;
	}
	
	public Optional<EPlayer> getPlayer() {
		return this.player;
	}
	
	public InetAddress getAddress() {
		return this.address;
	}
	
	public SanctionBanIp getSanction() {
		return this.sanction;
	}
	
	public Text getReason() {
		return this.reason;
	}
	
	public long getCreationDate() {
		return this.creationDate;
	}
	
	public boolean isIndefinite() {
		return !this.expirationDate.isPresent();
	}
	
	public Optional<Long> getExpirationDate() {
		return this.expirationDate;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public Cause getCause() {
		return this.cause;
	}
	
	public static class Enable extends BanIpEvent implements Cancellable {
		private final CommandSource commandSource;
		private boolean cancelled;
		
		public Enable(EUser user, Optional<EPlayer> player, InetAddress address, Text reason, long creationDate, 
				Optional<Long> expirationDate, SanctionBanIp sanction, CommandSource commandSource, Cause cause) {
			super(user, player, address, reason, creationDate, expirationDate, commandSource.getName(), sanction, cause);

			this.commandSource = commandSource;
			this.cancelled = false;
		}

		public CommandSource getCommandSource() {
			return this.commandSource;
		}

		@Override
		public boolean isCancelled() {
			return this.cancelled;
		}

		@Override
		public void setCancelled(boolean cancel) {
			this.cancelled = cancel;
		}

		@Override
		public boolean getValue() {
			return true;
		}
	}
	
	public static class Disable extends BanIpEvent {
		private final Optional<Text> pardonReason; 
		private final Optional<Long> pardonDate; 
		private final Optional<CommandSource> pardonCommandSource; 
		
		public Disable(EUser user, Optional<EPlayer> player, InetAddress address, Text reason, long creationDate, 
				Optional<Long> expirationDate, SanctionBanIp sanction, String source, 
				Optional<Text> pardonReason, Optional<Long> pardonDate, 
				Optional<CommandSource> pardonCommandSource, Cause cause) {
			super(user, player, address, reason, creationDate, expirationDate, source, sanction, cause);
			
			this.pardonReason = pardonReason;
			this.pardonDate = pardonDate;
			this.pardonCommandSource = pardonCommandSource;
		}
		
		public boolean isPardon() {
			return this.pardonDate.isPresent();
		}
		
		public Optional<Text> getPardonReason() {
			return this.pardonReason;
		}
		
		public Optional<Long> getPardonDate() {
			return this.pardonDate;
		}
		
		public Optional<CommandSource> getPardonCommandSource() {
			return this.pardonCommandSource;
		}
		
		@Override
		public boolean getValue() {
			return false;
		}
	}
}

