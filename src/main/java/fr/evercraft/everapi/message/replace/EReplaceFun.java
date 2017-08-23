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

import java.util.function.Function;

import com.google.common.base.Preconditions;

public class EReplaceFun<T> implements EReplace<T> {
	
	private String arg;
	private T value;
	
	private Function<String, T> fun;
	
	public EReplaceFun(Function<String,T> fun) {
		Preconditions.checkNotNull(fun, "fun");
		
		this.fun = fun;
	}
	
	@Override
	public T get(String replace) {
		if (this.value == null || this.arg == null || !this.arg.equals(replace)) {
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
		return new EReplaceFun<T>(this.fun);
	}
}
