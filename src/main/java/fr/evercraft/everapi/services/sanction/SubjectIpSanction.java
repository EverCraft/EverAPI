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

import java.util.Collection;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.services.sanction.manual.SanctionManualIP;

public interface SubjectIpSanction {
	public boolean isBan();
	public Collection<SanctionManualIP> getAll();
	
	public boolean addBan(Long creation, Text reason, String source);
	public boolean addBan(Long creation, Long duration, Text reason, String source);
	public boolean pardon(Long date, Text reason, String source);
}
