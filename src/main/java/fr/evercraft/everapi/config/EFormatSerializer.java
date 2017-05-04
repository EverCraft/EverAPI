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

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.text.TextTemplate;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.format.EFormatListString;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.format.EFormatTemplate;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class EFormatSerializer implements TypeSerializer<EFormat> {

	@Override
	public EFormat deserialize(TypeToken<?> type, ConfigurationNode node) throws ObjectMappingException {
		if (node.getValue() instanceof List) {
			return new EFormatListString(node.getList(TypeToken.of(String.class)));
		} else if (node.getValue() instanceof String) {
			return new EFormatString(node.getString(""));
		} else {
			return new EFormatTemplate(node.getValue(TypeToken.of(TextTemplate.class)));
		}
	}

	@Override
	public void serialize(TypeToken<?> type, EFormat format, ConfigurationNode node) throws ObjectMappingException {
		if (format instanceof EFormatListString) {
			node.setValue(((EFormatListString) format).getMessage());
		} else if (format instanceof EFormatString) {
			node.setValue(((EFormatString) format).getMessage());
		} else if (format instanceof EFormatTemplate) {
			node.setValue(((EFormatTemplate) format).getMessage());
		} else {
			throw new ObjectMappingException();
		}
	}
	
	public static EFormat deserialize(JsonElement json) throws ObjectMappingException {
		try {
			if (json.isJsonArray()) {
				List<String> list = new ArrayList<String>();
				json.getAsJsonArray().forEach(element -> list.add(element.getAsString()));
				return new EFormatListString(list);
			} else  {
				return new EFormatString(json.getAsString());
			}
		} catch (Exception e) {
			throw new ObjectMappingException(e.getMessage(), e.getCause());
		}
	}

	public static JsonElement serialize(EFormat format) throws ObjectMappingException {
		if (format instanceof EFormatListString) {
			JsonArray array = new JsonArray();
			((EFormatListString) format).getMessage().forEach(string -> array.add(new JsonPrimitive(string)));
			return array;
		} else if (format instanceof EFormatString) {
			return new JsonPrimitive(((EFormatString) format).getMessage());
		} else {
			throw new ObjectMappingException();
		}
	}

}
