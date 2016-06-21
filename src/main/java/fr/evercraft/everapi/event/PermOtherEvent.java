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

public interface PermOtherEvent extends Event {

    public static enum Action {
    	OTHER_PERMISSION_CHANGED,
    	OTHER_OPTION_CHANGED,
    	OTHER_INHERITANCE_CHANGED,
    	OTHER_ADDED,
    	OTHER_REMOVED;
    };

    public Subject getSubject();

    public Action getAction();

	@Override
	public Cause getCause();
	
	public interface Permission extends PermOtherEvent {}
	public interface Option extends PermOtherEvent {}
	public interface Inheritance extends PermOtherEvent {}
	public interface Add extends PermOtherEvent {}
	public interface Remove extends PermOtherEvent {}
}
