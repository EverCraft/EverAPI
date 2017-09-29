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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.util.Tristate;

public interface ESubjectData extends SubjectData {

	ESubject getSubject();

	String getIdentifier();
	String getCollectionIdentifier();
	boolean isTransient();

	Map<String, Boolean> getPermissions(String typeWorld);
	CompletableFuture<Boolean> setPermission(String typeWorld, String permission, Tristate value);
	CompletableFuture<Boolean> clearPermissions(String typeWorld);

	List<SubjectReference> getParents(String typeWorld);
	CompletableFuture<Boolean> addParent(String typeWorld, SubjectReference parent);
	CompletableFuture<Boolean> removeParent(String typeWorld, SubjectReference parent);
	CompletableFuture<Boolean> clearParents(String typeWorld);

	Map<String, String> getOptions(String typeWorld);
	CompletableFuture<Boolean> setOption(String typeWorld, String key, String value);
	CompletableFuture<Boolean> clearOptions(String typeWorld);

}
