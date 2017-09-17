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
package fr.evercraft.everapi;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EnumPermission;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public enum EAPermissions implements EnumPermission {
	//Commands :
	EVERAPI("commands.execute", EAMessages.PERMISSIONS_COMMANDS_EXECUTE, true),
	HELP("commands.help", EAMessages.PERMISSIONS_COMMANDS_HELP, true),
	RELOAD("commands.reload", EAMessages.PERMISSIONS_COMMANDS_RELOAD),
	PLUGINS("commands.plugins", EAMessages.PERMISSIONS_COMMANDS_PLUGINS, true),
	TEST("commands.test", EAMessages.PERMISSIONS_COMMANDS_TEST),
	BLOCK_INFO("commands.blockinfo", EAMessages.PERMISSIONS_COMMANDS_BLOCKINFO),
	DEBUG("commands.debug", EAMessages.PERMISSIONS_COMMANDS_DEBUG),
	
	WORLDS("worlds", EAMessages.PERMISSIONS_WORLDS);
	
	private static final String PREFIX = "everapi";
	
	private final String permission;
	private final EnumMessage message;
	private final boolean value;
    
    private EAPermissions(final String permission, final EnumMessage message) {
    	this(permission, message, false);
    }
    
    private EAPermissions(final String permission, final EnumMessage message, final boolean value) {   	    	
    	this.permission = PREFIX + "." + permission;
    	this.message = message;
    	this.value = value;
    }

    @Override
    public String get() {
		return this.permission;
	}

	@Override
	public boolean getDefault() {
		return this.value;
	}

	@Override
	public EnumMessage getMessage() {
		return this.message;
	}
}
