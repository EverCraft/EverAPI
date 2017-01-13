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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.Translation;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.file.EnumMessage;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;

public final class EMessageSender {	
	private EFormat prefix;
	private final EMessageFormat messages;
	private final Map<String, EReplace<?>> replaces;
	
	public EMessageSender(EMessageFormat messages) {
		this.messages = messages;
		this.replaces = new HashMap<String, EReplace<?>>();
		this.clear();
	}
	
	public EMessageSender replace(String key, Text value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, EReplace.of(value)); 
		return this;
	}
	
	public EMessageSender replace(String key, String value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, EReplace.of(value)); 
		return this;
	}
	
	public EMessageSender replace(String key, Translation value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, EReplace.of(value)); 
		return this;
	}
	
	public EMessageSender replace(String key, EMessageSender value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, EReplace.of(value)); 
		return this;
	}
	
	public EMessageSender replace(String key, Supplier<?> value) {
		Preconditions.checkNotNull(key, "key");
		Preconditions.checkNotNull(value, "value");
		
		this.replaces.put(key, EReplace.of(value)); 
		return this;
	}
	
	public EMessageSender replace(Map<String, EReplace<?>> replaces) {
		Preconditions.checkNotNull(replaces, "replaces");
		
		this.replaces.putAll(replaces); 
		return this;
	}
	
	public EMessageSender prefix(EnumMessage prefix) {
		Preconditions.checkNotNull(prefix, "prefix");
		
		this.prefix = prefix.getFormat();
		return this;
	}
	
	public EMessageSender clear() {
		this.prefix = this.messages.getPrefix();
		this.replaces.clear();
		return this;
	}
	
	public boolean sendTo(EPlayer player) {
		this.messages.getChat().ifPresent(message -> message.send(this.prefix, player, this.replaces));
		this.messages.getActionbar().ifPresent(message -> message.send(this.prefix, player, this.replaces));
		this.messages.getBossbar().ifPresent(message -> message.send(this.prefix, player, this.replaces));
		this.messages.getTitle().ifPresent(message -> message.send(this.prefix, player, this.replaces));
		return true;
		
	}
	
	public boolean sendTo(CommandSource source) {
		if (source instanceof EPlayer) {
			return this.sendTo((EPlayer) source);
		} else {
			this.messages.getChat().ifPresent(message -> message.send(this.prefix, source, this.replaces));
			this.messages.getActionbar().ifPresent(message -> message.send(this.prefix, source, this.replaces));
			this.messages.getBossbar().ifPresent(message -> message.send(this.prefix, source, this.replaces));
			this.messages.getTitle().ifPresent(message -> message.send(this.prefix, source, this.replaces));
			return true;
		}
	}
	
	public boolean sendTo(EUser user) {
		if (user instanceof EPlayer) {
			return this.sendTo((EPlayer) user);
		}
		return false;
	}
	
	public boolean sendAll(Collection<EPlayer> collection) {
		collection.stream().forEach(player -> this.sendTo(player));
		return false;
	}
	
	public boolean sendAll(Collection<EPlayer> collection, Predicate<Player> predicate) {
		collection.stream().filter(predicate).forEach(player -> this.sendTo(player));
		return false;
	}
}
