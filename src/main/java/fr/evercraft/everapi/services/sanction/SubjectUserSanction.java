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
package fr.evercraft.everapi.services.sanction;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.services.sanction.auto.SanctionAuto;
import fr.evercraft.everapi.services.sanction.manual.SanctionManual;
import fr.evercraft.everapi.services.sanction.manual.SanctionManualProfile;

public interface SubjectUserSanction {
	public boolean isBan();
	public boolean isBanIp();
	public boolean isMute();
	public boolean isJail();
	
	public Set<InetAddress> getBanIps();
	
	public Collection<SanctionManual> getAllManual();
	public Collection<SanctionAuto> getAllAuto();
	public boolean ban(long creation, Optional<Long> duration, Text reason, String source);
	public boolean banIp(InetAddress address, long creation, Optional<Long> duration, Text reason, String source);
	public boolean mute(long creation, Optional<Long> duration, Text reason, String source);
	public boolean jail(Jail jail, long creation, Optional<Long> duration, Text reason, String source);
	public boolean pardon(SanctionManualProfile.Type type, long date, Text reason, String source);
	public boolean addSanction(SanctionAuto.Reason reason, long creation, String source);
	
	public default boolean ban(Optional<Long> duration, Text reason, String source) {
		return this.ban(System.currentTimeMillis(), duration, reason, source);
	}
	
	public default boolean banIp(InetAddress address, Optional<Long> duration, Text reason, String source) {
		return this.banIp(address, System.currentTimeMillis(), duration, reason, source);
	}
	
	public default boolean mute(Optional<Long> duration, Text reason, String source) {
		return this.mute(System.currentTimeMillis(), duration, reason, source);
	}
	
	public default boolean jail(Jail jail, Optional<Long> duration, Text reason, String source) {
		return this.jail(jail, System.currentTimeMillis(), duration, reason, source);
	}
	
	public default boolean pardonBan(Text reason, String source) {
		return this.pardon(SanctionManualProfile.Type.BAN_PROFILE, System.currentTimeMillis(), reason, source);
	}
	
	public default boolean pardonBanIp(Text reason, String source) {
		return this.pardon(SanctionManualProfile.Type.BAN_IP, System.currentTimeMillis(), reason, source);
	}
	
	public default boolean pardonBanMute(Text reason, String source) {
		return this.pardon(SanctionManualProfile.Type.MUTE, System.currentTimeMillis(), reason, source);
	}
	
	public default boolean pardonBanJail(Text reason, String source) {
		return this.pardon(SanctionManualProfile.Type.JAIL, System.currentTimeMillis(), reason, source);
	}
}
