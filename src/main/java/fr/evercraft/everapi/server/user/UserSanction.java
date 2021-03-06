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
import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import com.google.common.collect.ImmutableList;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.jail.Jail;
import fr.evercraft.everapi.services.sanction.Sanction;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionBanProfile;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionJail;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionMute;
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
	
	public Optional<SanctionAuto> getAuto(SanctionAuto.Reason reason) {
		if (this.isPresent()) {
			return this.subject.getAuto(reason);
		}
		return Optional.empty();
	}
	
	public boolean isBanIp() {
		if (this.isPresent()) {
			return this.subject.isBanIp();
		}
		return false;
	}

	public boolean isBanIp(InetAddress inetAddress) {
		if (this.isPresent()) {
			return this.subject.isBanIp(inetAddress);
		}
		return false;
	}
	
	public boolean isBanProfile() {
		if (this.isPresent()) {
			return this.subject.isBanProfile();
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
	
	public Optional<SanctionBanProfile> getBanProfile() {
		if (this.isPresent()) {
			return this.subject.getBanProfile();
		}
		return Optional.empty();
	}
	
	public Optional<SanctionMute> getMute() {
		if (this.isPresent()) {
			return this.subject.getMute();
		}
		return Optional.empty();
	}
	
	public Optional<SanctionJail> getJail() {
		if (this.isPresent()) {
			return this.subject.getJail();
		}
		return Optional.empty();
	}
	
	/*
	 * Manual
	 */
	
	public boolean ban(Long creation, Optional<Long> expiration, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.ban(creation, expiration, reason, source);
		}
		return false;
	}

	public boolean banIp(InetAddress address, Long creation, Optional<Long> expiration, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.banIp(address, creation, expiration, reason, source);
		}
		return false;
	}

	public boolean mute(Long creation, Optional<Long> expiration, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.mute(creation, expiration, reason, source);
		}
		return false;
	}

	public boolean jail(Jail jail, Long creation, Optional<Long> expiration, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.jail(jail, creation, expiration, reason, source);
		}
		return false;
	}

	public Optional<SanctionManualProfile.Ban> pardonBan(Long date, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.pardonBan(date, reason, source);
		}
		return Optional.empty();
	}
	
	public Collection<SanctionManualProfile.BanIp> pardonBanIp(Long date, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.pardonBanIp(date, reason, source);
		}
		return ImmutableList.of();
	}
	
	public Optional<SanctionManualProfile.Mute> pardonMute(Long date, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.pardonMute(date, reason, source);
		}
		return Optional.empty();
	}
	
	public Optional<SanctionManualProfile.Jail> pardonJail(Long date, Text reason, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.pardonJail(date, reason, source);
		}
		return Optional.empty();
	}

	/*
	 * Auto
	 */
	
	public Optional<SanctionAuto> addSanction(SanctionAuto.Reason reason, Long creation, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.addSanction(reason, creation, source);
		}
		return Optional.empty();
	}
	
	public Optional<SanctionAuto> pardonSanction(SanctionAuto.Reason reason, Long date, Text reason_text, CommandSource source) {
		if (this.isPresent()) {
			return this.subject.pardonSanction(reason, date, reason_text, source);
		}
		return Optional.empty();
	}
	
	/*
	 * All
	 */
	
	public Collection<Sanction> getAllSanctions() {
		if (this.isPresent()) {
			return this.subject.getAll();
		}
		return ImmutableList.of();
	}
}
