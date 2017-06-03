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
package fr.evercraft.everapi.services.worldguard.flag;

import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class FakeFlag<T> extends EFlag<T> {
	
	public static <T> FakeFlag<T> of(String name) {
		return new FakeFlag<T>(name);
	}

	public FakeFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, final List<String> args) {
		throw new UnsupportedOperationException("Flag " + this.getName() + " is not implemented");
	}
	
	@Override
	public Collection<String> getSuggestRemove(CommandSource source, final List<String> args) {
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

	@Override
	public Text getValueFormat(T value) {
		return Text.EMPTY;
	}
}
