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
import fr.evercraft.everapi.services.sanction.auto.SanctionAuto.Reason;
import fr.evercraft.everapi.services.sanction.manual.SanctionManual;
import fr.evercraft.everapi.services.sanction.manual.SanctionManualProfile.Type;

public class UserSanction extends UserAccount implements SanctionUserSubject {
	
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

	@Override
	public Collection<SanctionManual> getManualBans() {
		if (this.isPresent()) {
			return this.subject.getManualBans();
		}
		return Arrays.asList();
	}
	
	@Override
	public Collection<SanctionAuto> getAutoBans() {
		if (this.isPresent()) {
			return this.subject.getAutoBans();
		}
		return Arrays.asList();
	}

	@Override
	public boolean isBan() {
		if (this.isPresent()) {
			return this.subject.isBan();
		}
		return false;
	}

	@Override
	public boolean isBanIp() {
		if (this.isPresent()) {
			return this.subject.isBanIp();
		}
		return false;
	}

	@Override
	public boolean isMute() {
		if (this.isPresent()) {
			return this.subject.isMute();
		}
		return false;
	}

	@Override
	public boolean isJail() {
		if (this.isPresent()) {
			return this.subject.isJail();
		}
		return false;
	}

	@Override
	public boolean ban(long creation, Optional<Long> duration, Text reason, String source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean banIp(InetAddress address, long creation, Optional<Long> duration, Text reason, String source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mute(long creation, Optional<Long> duration, Text reason, String source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jail(Jail jail, long creation, Optional<Long> duration, Text reason, String source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pardon(Type type, long date, Text reason, String source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addSanction(Reason reason, long creation, String source) {
		// TODO Auto-generated method stub
		return false;
	}
}
