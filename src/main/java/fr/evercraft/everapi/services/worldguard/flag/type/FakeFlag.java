package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Collection;
import java.util.List;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public class FakeFlag<T> extends EFlag<T> {
	
	public static <T> FakeFlag<T> of(String name) {
		return new FakeFlag<T>(name);
	}

	public FakeFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}
	
	@Override
	public Collection<String> getSuggestRemove(final List<String> args) {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}

	@Override
	public String serialize(T value) {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}

	@Override
	public T deserialize(String value) throws IllegalArgumentException {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}

	@Override
	public String getDescription() {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}

	@Override
	public T getDefault() {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}
}
