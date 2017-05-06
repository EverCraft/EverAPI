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
package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class StateFlag extends EFlag<StateFlag.State> {
	
	public enum State {
		ALLOW,
		DENY;
	}

	public StateFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, final List<String> args) {
		Set<String> suggests = new HashSet<String>();
		for (StateFlag.State state : StateFlag.State.values()) {
			suggests.add(state.name());
		}
		return suggests;
	}

	@Override
	public String serialize(StateFlag.State value) {
		return value.name();
	}

	@Override
	public StateFlag.State deserialize(String value) throws IllegalArgumentException {
		if (value.equalsIgnoreCase("ALLOW")) {
			return StateFlag.State.ALLOW;
		} else if (value.equalsIgnoreCase("DENY")) {
			return StateFlag.State.DENY;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public Text getValueFormat(StateFlag.State value) {
		return Text.of(this.serialize(value));
	}
}
