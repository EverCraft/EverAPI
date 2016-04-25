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
package fr.evercraft.everapi.servives.title;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.sponge.UtilsTick;

public class TitleMessage {
	
	private final Player player;
	
	private final long time;
	private final int priority;
	private final Title title;
	
	public TitleMessage(final Player player, final int priority, final Title title) {
		this.player = player;
		this.priority = priority;
		this.time = System.currentTimeMillis() + ((title.getStay().orElse(60) * 1000) / UtilsTick.TICK_SECONDS);
		this.title = title;
	}

	public Player getPlayer() {
		return this.player;
	}

	public long getTime() {
		return this.time;
	}

	public Title getTitle() {
		return this.title;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public boolean send() {
		if(this.player.isOnline()) {
			this.player.sendTitle(title);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "TitleMessage [player=" + player + ", time=" + time
				+ ", priority=" + priority + ", title=" + title + "]";
	}	
}
