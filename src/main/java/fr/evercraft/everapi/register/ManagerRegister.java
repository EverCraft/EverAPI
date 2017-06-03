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
package fr.evercraft.everapi.register;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.registers.*;
import fr.evercraft.everapi.registers.ChatType.ChatTypes;
import fr.evercraft.everapi.registers.IceType.IceTypes;
import fr.evercraft.everapi.registers.MoveType.MoveTypes;
import fr.evercraft.everapi.registers.SnowType.SnowTypes;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public class ManagerRegister {
	
	private final EverAPI plugin;
	
	public ManagerRegister(EverAPI plugin){
		this.plugin = plugin;
		
		new ERegister<ChatType>(this.plugin, ChatType.class, ChatTypes.class);
		new ERegister<IceType>(this.plugin, IceType.class, IceTypes.class);
		new ERegister<SnowType>(this.plugin, SnowType.class, SnowTypes.class);
		new ERegister<MoveType>(this.plugin, MoveType.class, MoveTypes.class);
		
		new ERegister<ProtectedRegion.Group>(this.plugin, ProtectedRegion.Group.class, ProtectedRegion.Groups.class);
		new ERegister<ProtectedRegion.Type>(this.plugin, ProtectedRegion.Type.class, ProtectedRegion.Types.class);
		new ERegister<ProtectedRegion.RemoveType>(this.plugin, ProtectedRegion.RemoveType.class, ProtectedRegion.RemoveTypes.class);
	}
	
	
}
