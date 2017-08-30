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
package fr.evercraft.everapi.server;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.user.EUser;

public class ServerSpawn extends ServerSponge {
	
	public ServerSpawn(EverAPI plugin){
		super(plugin);
	}
	
	public Transform<World> getSpawn(EUser player) {
		return this.plugin.getManagerService().getSpawn().getSpawn(player);
	}
}
