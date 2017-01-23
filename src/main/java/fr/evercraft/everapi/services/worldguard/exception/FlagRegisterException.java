/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package fr.evercraft.everapi.services.worldguard.exception;

import fr.evercraft.everapi.services.worldguard.flag.Flag;

public class FlagRegisterException extends RuntimeException {

	private static final long serialVersionUID = 8386730760561544393L;
	
	public enum Type {
		CONFLICT,
		INITIALIZED
	}
	
	private final Type type;
	private final Flag<?> flag;

	public FlagRegisterException(Type type, Flag<?> flag) {
        this.type = type;
        this.flag = flag;
    }
	
	@Override
	public String getMessage() {
		if (this.type.equals(Type.INITIALIZED)) {
			return "New flags cannot be registered at this time";
		} else if (this.type.equals(Type.CONFLICT)) {
			return "A flag already exists by the name " + flag.getIdentifier();
		}
		return "";
	}

	
}
