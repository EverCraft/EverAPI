package fr.evercraft.everapi.plugin;

import org.spongepowered.api.command.CommandSource;

public interface EnumPermission {
	
	public String get();
	public boolean has(CommandSource player);
	
}
