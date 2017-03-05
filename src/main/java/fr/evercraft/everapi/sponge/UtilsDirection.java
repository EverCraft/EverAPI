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
package fr.evercraft.everapi.sponge;

import java.util.Optional;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;

import fr.evercraft.everapi.EAMessage.EAMessages;

public class UtilsDirection {	
	public static Optional<Direction> of(String identifier) {
		if (identifier.isEmpty()) return Optional.empty();
		
		identifier = identifier.toLowerCase();
		if (identifier.length() == 1) {
			for (Direction direction : Direction.values()) {
				if (direction.isCardinal() && direction.name().toLowerCase().startsWith(identifier)) {
					return Optional.of(direction);
				}
			}
		} else {
			for (Direction direction : Direction.values()) {
				if (direction.name().equals(identifier)) {
					return Optional.of(direction);
				}
			}
		}
		return Optional.empty();
	}
	
	public static Direction of(double pitch, double yaw) {
		yaw = (yaw) % 360;
        if (yaw < 0) {
        	yaw += 360.0;
        }
		
        if (pitch > 67.5) {
            return Direction.DOWN;
        } else if (pitch < -67.5) {
            return Direction.UP;
        } else if (0 <= yaw && yaw < 22.5) {
            return Direction.SOUTH;
        } else if (22.5 <= yaw && yaw < 67.5) {
            return Direction.SOUTHWEST;
        } else if (67.5 <= yaw && yaw < 112.5) {
            return Direction.WEST;
        } else if (112.5 <= yaw && yaw < 157.5) {
            return Direction.NORTHWEST;
        } else if (157.5 <= yaw && yaw < 202.5) {
            return Direction.NORTH;
        } else if (202.5 <= yaw && yaw < 247.5) {
            return Direction.NORTHEAST;
        } else if (247.5 <= yaw && yaw < 292.5) {
            return Direction.EAST;
        } else if (292.5 <= yaw && yaw < 337.5) {
            return Direction.SOUTHEAST;
        } else if (337.5 <= yaw && yaw < 360.0) {
            return Direction.SOUTH;
        } else {
            return Direction.NONE;
        }
	}

	public static Text getText(Direction direction) {
		try {
			return EAMessages.valueOf("DIRECTIONS_" + direction.name().toUpperCase()).getText();
		} catch (IllegalArgumentException e) {
			return Text.of(direction.name().toUpperCase());
		}
	}
}
