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
package fr.evercraft.everapi.server.user;


import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.sanction.Jail;
import fr.evercraft.everapi.services.sanction.SanctionUserSubject;
import fr.evercraft.everapi.services.sanction.auto.SanctionAuto;
import fr.evercraft.everapi.services.sanction.manual.SanctionManualProfile;

public class UserSanction extends UserAccount {
	
	private SanctionUserSubject subject;

	public UserSanction(EverAPI plugin, User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getSanction().isPresent()) {
			this.subject = this.plugin.getManagerService().getSanction().get().get(this.user.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}

	public Optional<SanctionManualProfile> getManual(SanctionManualProfile.Type type) {
		if (this.isPresent()) {
			return this.subject.getManual(type);
		}
		return Optional.empty();
	}
	
	public Collection<SanctionAuto> getAuto(SanctionAuto.Type type) {
		if (this.isPresent()) {
			return this.subject.getAuto(type);
		}
		return Arrays.asList();
	}

	public boolean isBan() {
		if (this.isPresent()) {
			return this.subject.isBan();
		}
		return false;
	}

	public boolean isBanIp() {
		if (this.isPresent()) {
			return this.subject.isBanIp();
		}
		return false;
	}

	public boolean isMute() {
		if (this.isPresent()) {
			return this.subject.isMute();
		}
		return false;
	}

	public boolean isJail() {
		if (this.isPresent()) {
			return this.subject.isJail();
		}
		return false;
	}
	
	public boolean ban(Long creation, Optional<Long> expiration, Text reason, String source) {
		if (this.isPresent()) {
			return this.subject.ban(creation, expiration, reason, source);
		}
		return false;
	}

	public boolean ban(Optional<Long> expiration, Text reason, String source) {
		if (this.isPresent()) {
			return this.subject.ban(expiration, reason, source);
		}
		return false;
	}

	public boolean banIp(InetAddress address, Optional<Long> expiration, Text reason, String source) {
		if (this.isPresent()) {
			return this.subject.banIp(address, expiration, reason, source);
		}
		return false;
	}

	public boolean mute(Optional<Long> expiration, Text reason, String source) {
		if (this.isPresent()) {
			return this.subject.mute(expiration, reason, source);
		}
		return false;
	}

	public boolean jail(Jail jail, Optional<Long> expiration, Text reason, String source) {
		if (this.isPresent()) {
			return this.subject.jail(jail, expiration, reason, source);
		}
		return false;
	}

	public Optional<SanctionManualProfile> pardon(SanctionManualProfile.Type type, Text reason, String source) {
		if (this.isPresent()) {
			return this.subject.pardon(type, reason, source);
		}
		return Optional.empty();
	}

	public boolean addSanction(SanctionAuto.Reason reason, String source) {
		if (this.isPresent()) {
			return this.subject.addSanction(reason, source);
		}
		return false;
	}
}
