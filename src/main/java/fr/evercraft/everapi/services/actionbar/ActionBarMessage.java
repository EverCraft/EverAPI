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

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

public class ActionBarMessage {
	
	private final Player player;
	
	private final long time;
	private final int priority;
	private final Text message;
	
	public ActionBarMessage(final Player player, final int priority, final long time, final Text message){
		this.player = player;
		this.priority = priority;
		this.time = time;
		this.message = message;
	}

	public Player getPlayer() {
		return this.player;
	}

	public long getTime() {
		return this.time;
	}

	public Text getMessage() {
		return this.message;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public boolean send() {
		if(this.player.isOnline()) {
			this.player.sendMessage(ChatTypes.ACTION_BAR, this.message);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ActionBarMessage [player=" + player + ", time=" + time
				+ ", priority=" + priority + ", message=" + message + "]";
	}
	
}
