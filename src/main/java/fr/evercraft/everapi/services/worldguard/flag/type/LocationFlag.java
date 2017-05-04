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
package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.location.EVirtualPosition;
import fr.evercraft.everapi.server.location.EVirtualTransform;
import fr.evercraft.everapi.server.location.VirtualTransform;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.worldguard.flag.EFlag;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public abstract class LocationFlag extends EFlag<VirtualTransform> {

	private static final String HERE = "here";
	protected static final String PATTERN_SPLIT = "[,\\s]+";
	
	protected final EPlugin<?> plugin;
	
	public LocationFlag(EPlugin<?> plugin, String name) {
		super(name);
		
		this.plugin = plugin;
	}
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, final List<String> args) {
		return Arrays.asList("here", "x,y,z", "x,y,z,yaw,pitch", "x,y,z,yaw,pitch,world");
	}

	@Override
	public String serialize(VirtualTransform value) {
		return value.getPosition().getX() + "," + value.getPosition().getY() + "," + value.getPosition().getZ() + "," + value.getYaw() + "," + value.getPitch() + "," + value.getWorldName();
	}
	
	@Override
	public VirtualTransform deserialize(String value) throws IllegalArgumentException {
		String[] values = value.split(PATTERN_SPLIT);
		if (value.length() < 3) {
			throw new IllegalArgumentException();
		}
		
		try {
			Double x = Double.parseDouble(values[0]);
			Double y = Double.parseDouble(values[1]);
			Double z = Double.parseDouble(values[2]);
			
			if (value.length() == 5) {
				Double yaw = Double.parseDouble(values[3]);
				Double pitch = Double.parseDouble(values[4]);
				
				return new EVirtualTransform(this.plugin, "", x, y, z, yaw, pitch);
			} else if (value.length() == 6) {
				Double yaw = Double.parseDouble(values[3]);
				Double pitch = Double.parseDouble(values[4]);
				
				return new EVirtualTransform(this.plugin, values[5], x, y, z, yaw, pitch);
			} else {
				return new EVirtualPosition(x, y, z);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public VirtualTransform parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) throws IllegalArgumentException {
		String value = String.join(",", values.get(0));
		if (value.equalsIgnoreCase(HERE)) {
			if (source instanceof EPlayer) {
				return new EVirtualTransform(this.plugin, ((EPlayer) source).getTransform());
			} else {
				throw new IllegalArgumentException(EAMessages.COMMAND_ERROR_FOR_PLAYER.getString());
			}
		} else {
			return this.deserialize(value);
		}
	}
}
