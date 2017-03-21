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
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class EReplaceFun<T> implements EReplace<T> {
	
	private String arg;
	private T value;
	
	private Pattern pattern;
	private String prefix;
	private Function<String, T> fun;
	
	public EReplaceFun(String prefix, Function<String,T> fun) {
		Preconditions.checkNotNull(prefix, "prefix");
		Preconditions.checkNotNull(fun, "fun");
		
		this.prefix = prefix;
		this.pattern = Pattern.compile("<(?i)" + this.prefix + "=(.[^>]*)>");
		this.fun = fun;
	}
	
	@Override
	public T get(String replace) {
		if (this.value == null || this.arg == null || !this.arg.equalsIgnoreCase(replace)) {
			this.arg = replace;
			this.value = this.fun.apply(replace);
		}
		return this.value;
	}
	
	@Override
	public EReplaceFun<T> reset() {
		this.arg = null;
		this.value = null;
		return this;
	}
	
	@Override
	public EReplaceFun<T> clone() {
		return new EReplaceFun<T>(this.prefix, this.fun);
	}
	
	@Override
	public Optional<Pattern> getPattern() {
		return Optional.of(this.pattern);
	}
	
	@Override
	public Optional<String> getPrefix() {
		return Optional.of(this.prefix);
	}
}
