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
package fr.evercraft.everapi.services.sign;

import java.util.List;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;

public interface SignSubject {
	
	public static final TextColor COLOR_ENABLE = TextColors.DARK_BLUE;
	public static final TextColor COLOR_DISABLE = TextColors.RED;
	
	public String getName();
	
	public boolean create(EPlayer player, Location<World> location, SignData data);
	public boolean useEnable(EPlayer player, Sign sign);
	public boolean useDisable(EPlayer player, Sign sign);
	public boolean remove(EPlayer player, Location<World> location, List<Text> sign);
	
	public boolean valide(Sign sign);
}
