package fr.evercraft.everapi.register;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.registers.*;
import fr.evercraft.everapi.registers.ChatType.ChatTypes;
import fr.evercraft.everapi.registers.IceType.IceTypes;
import fr.evercraft.everapi.registers.SnowType.SnowTypes;

public class ManagerRegister {
	
	private final EverAPI plugin;
	
	public ManagerRegister(EverAPI plugin){
		this.plugin = plugin;
		
		new ERegister<ChatType>(this.plugin, ChatType.class, ChatTypes.class);
		new ERegister<IceType>(this.plugin, IceType.class, IceTypes.class);
		new ERegister<SnowType>(this.plugin, SnowType.class, SnowTypes.class);
	}
	
	
}
