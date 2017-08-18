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
package fr.evercraft.everapi.event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;

/**
 * Fired after a given {@link Subject}'s {@link SubjectData} is updated.
 *
 * <p>The event will be fired after the change has been fully applied, meaning
 * calls querying the {@link Subject} will reflect the change.</p>
 *
 * <p>This event is always called asynchronously.</p>
 */
public class SubjectDataUpdateEvent extends AbstractEvent {

    private final SubjectData subjectData;
    private final Cause cause;
    
    public SubjectDataUpdateEvent(SubjectData subjectData, Cause cause) {
		this.subjectData = subjectData;
		this.cause = cause;
	}

	/**
     * Gets the updated {@link SubjectData}.
     *
     * @return the updated data
     */
    public SubjectData getUpdatedData() {
    	return this.subjectData;
    }

	@Override
	public Cause getCause() {
		return this.cause;
	}

}