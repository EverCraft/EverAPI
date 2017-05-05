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
package fr.evercraft.everapi.plugin.file;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.EMessageSender;
import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.EChat;

public interface EnumMessage {
	
	public String getName();

	public String getPath();
	
	public EMessageBuilder getFrench();

	public EMessageBuilder getEnglish();
	
	public EMessageFormat getMessage();

	public void set(EMessageBuilder format);
	
	public default Text getText() {
		return this.getFormat().toText();
	}
	
	public default String getString() {
		return this.getFormat().toString();
	}
	
	public default TextColor getColor() {
		return EChat.getTextColor(this.getString());
	}
	
	public default EFormat getFormat() {
		if (this.getMessage().getChat().isPresent()) {
			return this.getMessage().getChat().get().getMessage();
		}
		return new EFormatString("");
	}
	
	public default EMessageSender sender() {
		return this.getMessage().sender();
	}
	
	public default void sendTo(CommandSource player) {
		this.sender().sendTo(player);
	}
	
	public default boolean has() {
		return this.getMessage().getChat().isPresent() || 
			   this.getMessage().getActionbar().isPresent() || 
			   this.getMessage().getBossbar().isPresent() || 
			   this.getMessage().getTitle().isPresent();
	}
}
