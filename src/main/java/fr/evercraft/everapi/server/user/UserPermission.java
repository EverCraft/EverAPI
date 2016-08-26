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
package fr.evercraft.everapi.server.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectCollection;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;

public class UserPermission extends UserKeys implements Subject {
	
	private Subject optionSubject;

	public UserPermission(EverAPI plugin, User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.optionSubject == null && this.plugin.getManagerService().getPermission().isPresent()) {
			this.optionSubject = this.plugin.getManagerService().getPermission().get().getUserSubjects().get(this.user.getIdentifier());
		}
		return this.optionSubject != null;
	}
	
	public Optional<Subject> getGroup() {
		return this.getGroup(getActiveContexts());
	}
	
	public Optional<Subject> getGroup(final Set<Context> contexts) {
		Preconditions.checkNotNull(contexts, "contexts");
		
		if (this.isPresent()) {
			List<Subject> groups = this.getSubjectData().getParents(contexts);
			if (!groups.isEmpty()) {
				return Optional.of(groups.get(0));
			}
		}
		return Optional.empty();
    }

	@Override
	public Optional<CommandSource> getCommandSource() {
		return this.user.getCommandSource();
	}

	@Override
	public SubjectCollection getContainingCollection() {
		return this.user.getContainingCollection();
	}

	@Override
	public boolean hasPermission(Set<Context> contexts, String permission) {
		return this.user.hasPermission(contexts, permission);
	}

	@Override
	public Tristate getPermissionValue(Set<Context> contexts, String permission) {
		return this.user.getPermissionValue(contexts, permission);
	}

	@Override
	public boolean isChildOf(Set<Context> contexts, Subject parent) {
		return this.user.isChildOf(contexts, parent);
	}

	@Override
	public List<Subject> getParents(Set<Context> contexts) {
		return this.user.getParents();
	}

	@Override
	public String getIdentifier() {
		return this.user.getIdentifier();
	}

	@Override
	public Set<Context> getActiveContexts() {
		return this.user.getActiveContexts();
	}

	@Override
	public SubjectData getSubjectData() {
		if (this.isPresent()) {
			return this.optionSubject.getSubjectData();
		}
		return null;
	}

	@Override
	public SubjectData getTransientSubjectData() {
		if (this.isPresent()) {
			return this.optionSubject.getTransientSubjectData();
		}
		return null;
	}

	@Override
	public Optional<String> getOption(Set<Context> contexts, String key) {
		if (this.isPresent()) {
			return this.optionSubject.getOption(contexts, key);
		}
		return Optional.empty();
	}
}
