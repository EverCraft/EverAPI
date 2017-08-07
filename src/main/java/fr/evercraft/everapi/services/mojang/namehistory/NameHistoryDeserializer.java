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
package fr.evercraft.everapi.services.mojang.namehistory;

import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class NameHistoryDeserializer implements JsonDeserializer<NameHistory> {

	@Override
	public NameHistory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();

        String name = obj.get("name").getAsString();
        Optional<Long> date = Optional.empty();
        if (obj.has("changedToAt")) {
            date = Optional.of(obj.get("changedToAt").getAsLong());
        }
		return new NameHistory(name, date);
	}

}
