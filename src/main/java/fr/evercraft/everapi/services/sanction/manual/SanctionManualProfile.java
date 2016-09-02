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
package fr.evercraft.everapi.services.sanction.manual;

import java.util.Optional;

public interface SanctionManualProfile extends SanctionManual {
	
	public interface Ban extends SanctionManualProfile {
		public default SanctionManualType getType() {
			return SanctionManualType.BAN_PROFILE;
		}
	}
	public interface Mute extends SanctionManualProfile {
		public default SanctionManualType getType() {
			return SanctionManualType.MUTE;
		}
	}
	public interface Jail extends SanctionManualProfile {
		public String getJailName();
		public Optional<Jail> getJail();
		
		public default SanctionManualType getType() {
			return SanctionManualType.JAIL;
		}
	}
}