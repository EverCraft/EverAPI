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
package fr.evercraft.everapi.services;

import java.util.List;
import java.util.Set;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

public interface ChatService {
	
	public final static String REGEX_COLOR = "&[0-9a-f]";
	public final static String REGEX_FORMAT = "&[lmnor]";
	public final static String REGEX_MAGIC = "&k";
	
	public String replace(String message);
	
	public List<String> replace(List<String> messages);

	public String getFormat(Subject subject);

	public String getFormat(Subject subject, Set<Context> contexts);

	public String replaceIcons(String message);

	public String replaceCharacter(String message);
	
}

