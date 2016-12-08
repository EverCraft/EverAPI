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
package fr.evercraft.everapi.message;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.Translation;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.server.EServer;
import fr.evercraft.everapi.server.player.EPlayer;

public final class EMessageSend {	
	private final EMessageFormat messages;
	private final Map<String, Object> replaces;
	
	public EMessageSend(EMessageFormat messages) {
		this.messages = messages;
		this.replaces = new HashMap<String, Object>();
		this.clear();
	}
	
	public EMessageSend replace(String key, Text value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, value); 
		return this;
	}
	
	public EMessageSend replace(String key, String value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, value); 
		return this;
	}
	
	public EMessageSend replace(String key, Translation value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, value); 
		return this;
	}
	
	public EMessageSend replace(String key, EMessageSend value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, value); 
		return this;
	}
	
	public EMessageSend clear() {
		this.replaces.clear();
		return this;
	}
	
	public boolean sendTo(EPlayer player) {
		this.messages.getChat().ifPresent(message -> message.send(this.messages.getPrefix(), player, this.replaces));
		this.messages.getActionbar().ifPresent(message -> message.send(this.messages.getPrefix(), player, this.replaces));
		this.messages.getBossbar().ifPresent(message -> message.send(this.messages.getPrefix(), player, this.replaces));
		this.messages.getTitle().ifPresent(message -> message.send(this.messages.getPrefix(), player, this.replaces));
		return false;
		
	}
	
	public boolean sendAll(EServer server) {
		return false;
		
	}
	
	public boolean sendTo(CommandSource commandSource) {
		return false;
	}
}
