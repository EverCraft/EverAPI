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

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.services.sanction.Sanction.SanctionBanProfile;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionJail;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionMute;
import fr.evercraft.everapi.services.sanction.auto.SanctionAuto;
import fr.evercraft.everapi.services.sanction.manual.SanctionManualProfile;
import fr.evercraft.everapi.services.sanction.manual.SanctionManualProfile.BanIp;

public interface SanctionUserSubject {
	public Collection<Sanction> getAll();
	
	public boolean isBanIp(InetAddress inetAddress);
	public boolean isBanIp();
	
	public boolean isBanProfile();
	public boolean isMute();
	public boolean isJail();
	
	public Optional<SanctionBanProfile> getBanProfile();
	public Optional<SanctionMute> getMute();
	public Optional<SanctionJail> getJail();
	
	public Optional<SanctionManualProfile> getManual(SanctionManualProfile.Type type);
	public Collection<SanctionAuto> getAuto(SanctionAuto.Type type);
	
	/*
	 * Manual
	 */
	
	public boolean ban(long creation, Optional<Long> duration, Text reason, CommandSource source);
	public boolean banIp(InetAddress address, long creation, Optional<Long> duration, Text reason, CommandSource source);
	public boolean mute(long creation, Optional<Long> duration, Text reason, CommandSource source);
	public boolean jail(Jail jail, long creation, Optional<Long> duration, Text reason, CommandSource source);
	
	public Optional<SanctionManualProfile.Ban> pardonBan(long date, Text reason, CommandSource source);
	public Collection<SanctionManualProfile.BanIp> pardonBanIp(long date, Text reason, CommandSource source);
	public Optional<BanIp> pardonBanIp(InetAddress address, long date, Text reason, CommandSource source);
	public Optional<SanctionManualProfile.Mute> pardonMute(long date, Text reason, CommandSource source);
	public Optional<SanctionManualProfile.Jail> pardonJail(long date, Text reason, CommandSource source);
	
	public boolean removeManual(SanctionManualProfile profile);
	
	/*
	 * Auto
	 */
	
	public boolean addSanction(SanctionAuto.Reason reason_sanction, long creation, CommandSource source);
	public boolean pardonSanction(SanctionAuto.Reason reason_sanction, long date, Text reason_text, CommandSource source);
	public boolean removeSanction(SanctionAuto profile);	
}
