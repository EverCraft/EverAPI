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

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.EAMessage.EAMessages;

public class UtilsGameMode {
	public static Text getName(final GameMode gamemode) {
		if (gamemode == null) return Text.EMPTY;

		if (gamemode.equals(GameModes.SURVIVAL)){
			return EAMessages.GAMEMODE_SURVIVAL.getText();
		} else if (gamemode.equals(GameModes.CREATIVE)) {
			return EAMessages.GAMEMODE_CREATIVE.getText();
		} else if (gamemode.equals(GameModes.ADVENTURE)) {
			return EAMessages.GAMEMODE_ADVENTURE.getText();
		} else if (gamemode.equals(GameModes.SPECTATOR)) {
			return EAMessages.GAMEMODE_SPECTATOR.getText();
		} else if (gamemode.equals(GameModes.NOT_SET)) {
			return EAMessages.GAMEMODE_NOT_SET.getText();
		}
		return Text.EMPTY;
	}
	
	public static Optional<GameMode> getGameMode(final String arg) {
		if (arg == null) return Optional.empty();
		
		switch (arg) {
			case "0":
			case "s":
				return Optional.of(GameModes.SURVIVAL);
			case "1":
			case "c":
				return Optional.of(GameModes.CREATIVE);
			case "2":
			case "a":
				return Optional.of(GameModes.ADVENTURE);
			case "3":
			case "sp":
				return Optional.of(GameModes.SPECTATOR);
			case "-1":
				return Optional.of(GameModes.NOT_SET);
			default:
				return Sponge.getGame().getRegistry().getType(GameMode.class, arg);
			
		}
	}

}
