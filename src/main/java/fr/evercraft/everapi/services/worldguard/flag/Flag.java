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
package fr.evercraft.everapi.services.worldguard.flag;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

import java.util.Collection;

import org.spongepowered.api.text.Text;

public interface Flag<T> {
	
	String getIdentifier();
	String getName();
	String getDescription();
	Text getNameFormat();
	
	TypeToken<T> getToken();
	
	T getDefault();
	T getDefault(ProtectedRegion region);
	
	Collection<String> getSuggests();
	
	String serialize(T value);
	T deserialize(String value) throws IllegalArgumentException;
	T deserialize(EPlayer player, String value) throws IllegalArgumentException;

}
