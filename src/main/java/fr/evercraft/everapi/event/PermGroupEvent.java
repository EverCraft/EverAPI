/*
 * EverAPI
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
package fr.evercraft.everapi.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.Subject;

public interface PermGroupEvent extends Event {

    public static enum Action {
    	GROUP_PERMISSION_CHANGED,
    	GROUP_INHERITANCE_CHANGED,
    	GROUP_OPTION_CHANGED,
    	GROUP_ADDED,
    	GROUP_REMOVED;
    };

    public Subject getSubject();
    
    public Action getAction();

	@Override
	public Cause getCause();
	
	public interface Permission extends PermGroupEvent {}
	public interface Inheritance extends PermGroupEvent {}
	public interface Option extends PermGroupEvent {}
	public interface Add extends PermGroupEvent {}
	public interface Remove extends PermGroupEvent {}
}
