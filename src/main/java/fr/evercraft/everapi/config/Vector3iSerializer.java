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
package fr.evercraft.everapi.config;

import java.util.Arrays;
import java.util.List;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class Vector3iSerializer implements TypeSerializer<Vector3i> {

	@Override
	public Vector3i deserialize(TypeToken<?> type, ConfigurationNode node) throws ObjectMappingException {
		List<Integer> positions = node.getList(TypeToken.of(Integer.class));
		if (positions.size() != 3) {
			throw new ObjectMappingException();
		}
		
		return Vector3i.from(positions.get(0), positions.get(1), positions.get(2));
	}

	@Override
	public void serialize(TypeToken<?> type, Vector3i vector, ConfigurationNode node) throws ObjectMappingException {
		node.setValue(Arrays.asList(vector.getX(), vector.getY(), vector.getZ()));
	}

}
