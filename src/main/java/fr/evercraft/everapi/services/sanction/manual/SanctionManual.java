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

import org.spongepowered.api.text.Text;

public interface SanctionManual {

	public Long getCreationDate();
	public Optional<Long> getExpirationDate();
	public Optional<Long> getDuration();
	public Text getReason();
	public String getSource();
	
	public default boolean isIndefinite() {
        return !this.getExpirationDate().isPresent();
    }
	
	public default boolean isExpire() {
		if(this.isPardon()) {
			return true;
		}
		
		if(this.isIndefinite()) {
			return false;
		}
		
        return this.getExpirationDate().orElse(0L) > System.currentTimeMillis();
    }
	
	/*
	 * Pardon
	 */
	
	public Optional<Long> getPardonDate();
	public Optional<String> getPardonSource();
	public Optional<Text> getPardonReason();
	
	public default boolean isPardon() {
        return this.getPardonDate().isPresent();
    }
}
