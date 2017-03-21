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
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public interface EReplace<T> {
	
	T get(String replace);
	
	Optional<String> getPrefix();
	Optional<Pattern> getPattern();
	
	EReplace<T> reset();
	
	EReplace<T> clone();

	public static <T> EReplace<T> of(T value) {
		return new EReplaceValue<T>(value);
	}
	
	public static <T> EReplace<T> of(Supplier<T> fun) {
		return new EReplaceSupplier<T>(fun);
	}
	
	public static <T> EReplace<T> of(String prefix, Function<String, T> fun) {
		return new EReplaceFun<T>(prefix, fun);
	}
}
