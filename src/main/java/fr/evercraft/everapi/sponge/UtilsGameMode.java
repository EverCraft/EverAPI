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

import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.EAMessage.EAMessages;

public class UtilsGameMode {
	public static Text getName(final GameMode gamemode){
		Text name = Text.EMPTY;
		if (gamemode != null){
			if (gamemode.equals(GameModes.SURVIVAL)){
				name = EAMessages.GAMEMODE_SURVIVAL.getText();
			} else if (gamemode.equals(GameModes.CREATIVE)) {
				name = EAMessages.GAMEMODE_CREATIVE.getText();
			} else if (gamemode.equals(GameModes.ADVENTURE)) {
				name = EAMessages.GAMEMODE_ADVENTURE.getText();
			} else if (gamemode.equals(GameModes.SPECTATOR)) {
				name = EAMessages.GAMEMODE_SPECTATOR.getText();
			} else if (gamemode.equals(GameModes.NOT_SET)) {
				name = EAMessages.GAMEMODE_NOT_SET.getText();
			}
		}
		return name;
	}
	
	public static Optional<GameMode> getGameMode(final String arg){
		GameMode gamemode = null;
		if (arg != null){
			if (arg.equalsIgnoreCase("survival") || arg.equalsIgnoreCase("0")){
				gamemode = GameModes.SURVIVAL;
			} else if (arg.equalsIgnoreCase("creative") || arg.equalsIgnoreCase("1")){
				gamemode = GameModes.CREATIVE;
			} else if (arg.equalsIgnoreCase("adventure") || arg.equalsIgnoreCase("2")){
				gamemode = GameModes.ADVENTURE;
			} else if (arg.equalsIgnoreCase("spectator") || arg.equalsIgnoreCase("3")){
				gamemode = GameModes.SPECTATOR;
			} else if (arg.equalsIgnoreCase("empty") || arg.equalsIgnoreCase("4")){
				gamemode = GameModes.NOT_SET;
			}
		}
		return Optional.ofNullable(gamemode);
	}

}
