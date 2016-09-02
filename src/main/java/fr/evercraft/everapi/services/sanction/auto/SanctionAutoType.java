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
package fr.evercraft.everapi.services.sanction.auto;

public enum SanctionAutoType {
	BAN_PROFILE(true, false, false, false),
	BAN_IP(false, true, false, false),
	MUTE(false, false, true, false),
	JAIL(false, false, false, true),
	BAN_PROFILE_AND_BAN_IP(true, true, false, false),
	MUTE_AND_JAIL(false, false, true, true);
	
	private final boolean ban;
	private final boolean ban_ip;
	private final boolean mute;
	private final boolean jail;
	
	SanctionAutoType(final boolean ban, final boolean ban_ip, final boolean mute, final boolean jail) {
		this.ban = ban;
		this.ban_ip = ban_ip;
		this.mute = mute;
		this.jail = jail;
	}
	
	public boolean isBan() {
		return this.ban;
	}
	
	public boolean isBanIP() {
		return this.ban_ip;
	}
	
	public boolean isMute() {
		return this.mute;
	}
	
	public boolean isJail() {
		return this.jail;
	}
}
