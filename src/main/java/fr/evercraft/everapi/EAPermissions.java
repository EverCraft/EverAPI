package fr.evercraft.everapi;

import org.spongepowered.api.command.CommandSource;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.plugin.EnumPermission;

public enum EAPermissions implements EnumPermission {
	EVERAPI("command"),
	
	HELP("help"),
	RELOAD("reload"),
	PLUGINS("plugins"),
	
	VIEW_OTHERS("view.others"),
	
	WORLDS("worlds"),
	
	COOLDOWN("cooldown"),
	COOLDOWN_BYPASS("cooldown.bypass");
	
	private final static String prefix = "everapi";
	
	private final String permission;
    
    private EAPermissions(final String permission) {   	
    	Preconditions.checkNotNull(permission, "La permission '" + this.name() + "' n'est pas définit");
    	
    	this.permission = permission;
    }

    public String get() {
		return EAPermissions.prefix + "." + this.permission;
	}
    
    public boolean has(CommandSource player) {
    	return player.hasPermission(this.get());
    }
}
