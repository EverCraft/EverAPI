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

import java.util.Optional;

public interface SanctionAuto {	
	public Long getCreationDate();
	public Optional<Long> getExpirationDate();

	public SanctionAutoType getType();
	public SanctionAutoReason getReason();
	public int getLevel();
	public String getSource();
	
	public default boolean isBan() {
		return this.getType().isBan();
	}
	
	public default boolean isBanIP() {
		return this.getType().isBanIP();
	}
	
	public default boolean isMute() {
		return this.getType().isMute();
	}
	
	public default boolean isJail() {
		return this.getType().isJail();
	}	
	
	public default boolean isIndefinite() {
        return !this.getExpirationDate().isPresent();
    }
}
