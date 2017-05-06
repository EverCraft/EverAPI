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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EPlugin;
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
		if (!(value instanceof EVirtualTransform)) {
			return value.getPosition().getX() + "," + value.getPosition().getY() + "," + value.getPosition().getZ();
		} else if (value.getWorldIdentifier().isEmpty()){
			return value.getPosition().getX() + "," + value.getPosition().getY() + "," + value.getPosition().getZ() + "," + value.getYaw() + "," + value.getPitch();
		} else {
			return value.getPosition().getX() + "," + value.getPosition().getY() + "," + value.getPosition().getZ() + "," + value.getYaw() + "," + value.getPitch() + "," + value.getWorldIdentifier();
		}
	}
	
	@Override
	public VirtualTransform deserialize(String value) throws IllegalArgumentException {
		String[] values = value.split(PATTERN_SPLIT);
		if (values.length < 3) {
			throw new IllegalArgumentException();
		}

		try {
			Double x = Double.parseDouble(values[0]);
			Double y = Double.parseDouble(values[1]);
			Double z = Double.parseDouble(values[2]);
			
			if (values.length == 5) {
				Double yaw = Double.parseDouble(values[3]);
				Double pitch = Double.parseDouble(values[4]);
				
				return VirtualTransform.of(this.plugin, "", x, y, z, yaw, pitch);
			} else if (values.length == 6) {
				Double yaw = Double.parseDouble(values[3]);
				Double pitch = Double.parseDouble(values[4]);
				
				return VirtualTransform.of(this.plugin, values[5], x, y, z, yaw, pitch);
			} else {
				return VirtualTransform.of(x, y, z);
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
	
	@Override
	public Text getValueFormat(VirtualTransform value) {
		List<Text> hover = new ArrayList<Text>();
		hover.add(EAMessages.FLAG_LOCATION_X.getFormat()
				.toText("<x>", Math.round(value.getPosition().getX())));
		hover.add(EAMessages.FLAG_LOCATION_Y.getFormat()
				.toText("<y>", Math.round(value.getPosition().getY())));
		hover.add(EAMessages.FLAG_LOCATION_Z.getFormat()
				.toText("<z>", Math.round(value.getPosition().getZ())));
		
		if (value instanceof EVirtualTransform) {
			hover.add(EAMessages.FLAG_LOCATION_YAW.getFormat()
					.toText("<yaw>", Math.round(value.getYaw())));
			hover.add(EAMessages.FLAG_LOCATION_PITCH.getFormat()
					.toText("<pitch>", Math.round(value.getPitch())));
		}
		
		if (value.getWorldName().isPresent()) {
			hover.add(EAMessages.FLAG_LOCATION_WORLD.getFormat()
					.toText("<world>", value.getWorldName().get()));
		}
		
		return EAMessages.FLAG_LOCATION.getFormat()
				.toText("<x>", Math.round(value.getPosition().getX()),
						"<y>", Math.round(value.getPosition().getY()),
						"<z>", Math.round(value.getPosition().getZ()))
				.toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
				.build();
	}
}
