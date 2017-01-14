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

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.ImmutableList;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.worldguard.SelectType;
import fr.evercraft.everapi.services.worldguard.SubjectWorldGuard;

public class UserWorldGuard extends UserStats implements SubjectWorldGuard {
	
	private SubjectWorldGuard subject;

	public UserWorldGuard(EverAPI plugin, User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getWorldGuard().isPresent()) {
			this.subject = this.plugin.getManagerService().getWorldGuard().get().get(this.user.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}

	@Override
	public Optional<Vector3i> getSelectPos1() {
		if (this.isPresent()) {
			return this.subject.getSelectPos1();
		}
		return Optional.empty();
	}

	@Override
	public boolean setSelectPos1(@Nullable Vector3i pos) {
		if (this.isPresent()) {
			return this.subject.setSelectPos1(pos);
		}
		return false;
	}

	@Override
	public Optional<Vector3i> getSelectPos2() {
		if (this.isPresent()) {
			return this.subject.getSelectPos2();
		}
		return Optional.empty();
	}

	@Override
	public boolean setSelectPos2(@Nullable Vector3i pos) {
		if (this.isPresent()) {
			return this.subject.setSelectPos2(pos);
		}
		return false;
	}

	@Override
	public List<Vector3i> getSelectPoints() {
		if (this.isPresent()) {
			return this.subject.getSelectPoints();
		}
		return ImmutableList.of();
	}

	@Override
	public boolean addSelectPoint(Vector3i pos) {
		if (this.isPresent()) {
			return this.subject.addSelectPoint(pos);
		}
		return false;
	}

	@Override
	public boolean removeSelectPoint(Vector3i pos) {
		if (this.isPresent()) {
			return this.subject.removeSelectPoint(pos);
		}
		return false;
	}

	@Override
	public boolean clearSelectPoints() {
		if (this.isPresent()) {
			return this.subject.clearSelectPoints();
		}
		return false;
	}

	@Override
	public SelectType getSelectType() {
		if (this.isPresent()) {
			return this.subject.getSelectType();
		}
		return SelectType.CUBOID;
	}

	@Override
	public boolean setSelectType(@Nullable SelectType type) {
		if (this.isPresent()) {
			return this.subject.setSelectType(type);
		}
		return false;
	}
	
	@Override
	public Optional<Integer> getSelectArea() {
		if (this.isPresent()) {
			return this.subject.getSelectArea();
		}
		return Optional.empty();
    }
}
