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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.services.worldguard.flag.EFlag;

public abstract class DoubleFlag extends EFlag<Double> {

	public DoubleFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, final List<String> args) {
		return Arrays.asList("1.01", "2.02", "3.03");
	}

	@Override
	public String serialize(Double value) {
		return value.toString();
	}

	@Override
	public Double deserialize(String value) throws IllegalArgumentException {
		try {
			return Double.valueOf(value);
		} catch (Exception e) {
			throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", value));
		}
	}
	
	@Override
	public Text getValueFormat(Double value) {
		return Text.of(value);
	}
}
