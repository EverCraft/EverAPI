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

import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.sanction.Jail;

public interface JailEvent extends Event, Cancellable {
	
	public EUser getUser();
	public Optional<EPlayer> getPlayer();
	
	public boolean getValue();
	public Jail getJail();
	
	public Text getReason();
	public long getCreationDate();
	public boolean isIndefinite();
	public Optional<Long> getExpirationDate();
	public String getSource();
	
	public interface Enable extends JailEvent {
		public CommandSource getCommandSource();
	}
	
	public interface Disable extends JailEvent {
		public boolean isPardon();		
		public Optional<Text> getPardonReason();
		public Optional<Long> getPardonDate();
		public Optional<String> getPardonSource();
		public Optional<CommandSource> getPardonCommandSource();
	}
}