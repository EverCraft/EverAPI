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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public abstract class IntegerFlag extends EFlag<Integer> {

	public IntegerFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, final List<String> args) {
		if (args.size() == 1) {
			return Arrays.asList("1", "2", "3");
		}
		return Arrays.asList();
	}

	@Override
	public String serialize(Integer value) {
		return value.toString();
	}

	@Override
	public Integer deserialize(String value) throws IllegalArgumentException {
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public Text getValueFormat(Integer value) {
		return Text.of(value);
	}
}
