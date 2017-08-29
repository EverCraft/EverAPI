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
import java.util.Optional;
import java.util.regex.Pattern;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.server.player.EPlayer;

public class EMessageBossBar {
	
	private final EFormat message;
	
	private final long stay;
	private final ServerBossBar bossbar;
	
	private final String priority;
	private final boolean prefix;

	public EMessageBossBar(final EFormat message, final long stay, final ServerBossBar bossbar, final String priority, final boolean prefix) {
		this.message = message;
		this.stay = stay;
		this.bossbar = bossbar;
		this.priority = priority;
		this.prefix = prefix;
	}

	public EFormat getMessage() {
		return this.message;
	}

	public long getStay() {
		return this.stay;
	}

	public ServerBossBar getServerBossBar() {
		return this.bossbar;
	}

	public String getPriority() {
		return this.priority;
	}

	public boolean isPrefix() {
		return this.prefix;
	}

	public void send(EFormat prefix, EPlayer player, Map<Pattern, EReplace<?>> replaces) {
		ServerBossBar bossbar;
		if (this.prefix) {
			bossbar = ServerBossBar.builder().from(this.bossbar).name(prefix.toText().concat(this.message.toText(replaces))).build();
		} else {
			bossbar = ServerBossBar.builder().from(this.bossbar).name(this.message.toText(replaces)).build();
		}
		player.sendBossBar(this.priority, bossbar, Optional.empty(), Optional.of(this.stay));
	}
	
	public void send(EFormat prefix, CommandSource source, Map<Pattern, EReplace<?>> replaces) {
		if (source instanceof EPlayer) {
			this.send(prefix, (EPlayer) source, replaces);
		} else {
			if (this.prefix) {
				source.sendMessage(prefix.toText().concat(this.message.toText(replaces)));
			} else {
				source.sendMessage(this.message.toText(replaces));
			}
		}
	}
}
