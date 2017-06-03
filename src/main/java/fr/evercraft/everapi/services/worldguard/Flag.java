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
package fr.evercraft.everapi.services.worldguard;

import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public interface Flag<T> extends CatalogType {
	
	String getId();
	String getName();
	String getDescription();
	Text getNameFormat();
	Text getValueFormat(T value);
	
	void reload();
	
	Set<ProtectedRegion.Group> getGroups();
	
	T getDefault();
	T getDefault(ProtectedRegion region);
	
	Collection<String> getSuggestAdd(CommandSource source, List<String> args);
	Collection<String> getSuggestRemove(CommandSource source, List<String> args);
	
	String serialize(T value);
	T deserialize(String value) throws IllegalArgumentException;
	T parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) throws IllegalArgumentException;
	Optional<T> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) throws IllegalArgumentException;

}
