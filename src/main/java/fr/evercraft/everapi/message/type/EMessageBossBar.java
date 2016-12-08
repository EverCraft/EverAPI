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
package fr.evercraft.everapi.message.type;

import java.util.Map;

import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.message.EMessageType;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageBossBar {
	
	private final Object message;
	private final EMessageType format;
	
	private final double stay;
	private final float percent;
	private final BossBarColor color;
	private final BossBarOverlay overlay;
	private final boolean darkenSky;
	private final boolean playEndBossMusic;
	private final boolean createFog;
	
	private final String priority;
	private final boolean prefix;

	public EMessageBossBar(final Object message, final EMessageType format, final double stay, float percent, BossBarColor color, final BossBarOverlay overlay, 
			final boolean darkenSky, final boolean playEndBossMusic, final boolean createFog, final String priority, final boolean prefix) {
		this.message = message;
		this.format = format;
		this.stay = stay;
		this.percent = percent;
		this.color = color;
		this.overlay = overlay;
		this.darkenSky = darkenSky;
		this.playEndBossMusic = playEndBossMusic;
		this.createFog = createFog;
		this.priority = priority;
		this.prefix = prefix;
	}

	public Object getMessage() {
		return this.message.toString();
	}

	public EMessageType getFormat() {
		return this.format;
	}

	public double getStay() {
		return this.stay;
	}

	public float getPercent() {
		return percent;
	}

	public BossBarColor getColor() {
		return color;
	}

	public BossBarOverlay getOverlay() {
		return overlay;
	}

	public boolean isDarkenSky() {
		return darkenSky;
	}

	public boolean isPlayEndBossMusic() {
		return playEndBossMusic;
	}

	public boolean isCreateFog() {
		return createFog;
	}

	public String getPriority() {
		return this.priority;
	}

	public boolean isPrefix() {
		return this.prefix;
	}

	public Object send(Text prefix, EPlayer player, Map<String, Object> replaces) {
		// TODO Auto-generated method stub
		return null;
	}
}
