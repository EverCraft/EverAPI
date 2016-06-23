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
package fr.evercraft.everapi.services.actionbar;

import java.util.UUID;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

import fr.evercraft.everapi.server.player.EPlayer;

public class ActionBarMessage {
	
	private final UUID uuid;
	
	private final long time;
	private final String identifier;
	private final Text message;
	
	public ActionBarMessage(final UUID uuid, final String identifier, final long time, final Text message){
		this.uuid = uuid;
		this.identifier = identifier;
		this.time = time;
		this.message = message;
	}

	public UUID getPlayer() {
		return this.uuid;
	}

	public long getTime() {
		return this.time;
	}

	public Text getMessage() {
		return this.message;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public void send(EPlayer player) {
		player.sendMessage(ChatTypes.ACTION_BAR, this.message);
	}

	@Override
	public String toString() {
		return "ActionBarMessage [uuid=" + uuid + ", time=" + time
				+ ", identifier=" + identifier + ", message=" + message + "]";
	}
	
}
