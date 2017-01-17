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

public class EReplaceValue<T> implements EReplace<T> {
	
	private final T value;
	
	public EReplaceValue(final T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return this.value;
	}
	
	@Override
	public EReplaceValue<T> reset() {
		return this;
	}
	
	@Override
	public EReplaceValue<T> clone() {
		return new EReplaceValue<T>(this.value);
	}
}
