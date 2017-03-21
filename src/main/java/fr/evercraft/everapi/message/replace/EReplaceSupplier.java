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
package fr.evercraft.everapi.message.replace;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class EReplaceSupplier<T> implements EReplace<T> {
	
	private T value;
	private Supplier<T> fun;
	
	public EReplaceSupplier(Supplier<T> fun) {
		Preconditions.checkNotNull(fun, "fun");
		
		this.fun = fun;
	}
	
	@Override
	public T get(String replace) {
		if (value == null) {
			this.value = this.fun.get();
		}
		return this.value;
	}
	
	@Override
	public EReplaceSupplier<T> reset() {
		this.value = null;
		return this;
	}
	
	@Override
	public EReplaceSupplier<T> clone() {
		return new EReplaceSupplier<T>(this.fun);
	}

	@Override
	public Optional<Pattern> getPattern() {
		return Optional.empty();
	}
	
	@Override
	public Optional<String> getPrefix() {
		return Optional.empty();
	}
}
