package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public Collection<String> getSuggestAdd(final List<String> args) {
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
}
