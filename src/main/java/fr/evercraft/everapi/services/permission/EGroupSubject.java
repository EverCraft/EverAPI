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
package fr.evercraft.everapi.services.permission;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface EGroupSubject extends ESubject {

	EGroupData getSubjectData();
	EGroupData getTransientSubjectData();

	CompletableFuture<Boolean> setFriendlyIdentifier(String name);
	default String getName() {
		return this.getFriendlyIdentifier().orElse(this.getIdentifier());
	}

	CompletableFuture<Boolean> setDefault(String typeWorld, boolean value);
	boolean isDefault(String typeWorld);

	Set<String> getTypeWorlds();
	boolean hasTypeWorld(String typeWorld);
	CompletableFuture<Boolean> removeTypeWorld(String typeWorld);
}
