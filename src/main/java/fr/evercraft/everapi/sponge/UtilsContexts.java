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
package fr.evercraft.everapi.sponge;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Set;

import org.spongepowered.api.service.context.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class UtilsContexts {
	public static String serialize(final Set<Context> contexts) {
		JsonArray map = new JsonArray();
		for(Context context : contexts){
			JsonObject entry = new JsonObject();
			entry.addProperty(context.getKey(), context.getValue());
			map.add(entry);
	    }
		return map.toString();
	}
	
	public static Set<Context> deserialize(final String json) throws JsonSyntaxException, IllegalStateException, ClassCastException {
		Set<Context> contexts = new HashSet<Context>();
		JsonArray map = (new Gson()).fromJson(json, JsonArray.class);
		for(JsonElement context : map){
			for(Entry<String, JsonElement> entry : context.getAsJsonObject().entrySet()) {
				contexts.add(new Context(entry.getKey(), entry.getValue().getAsString()));
			}
	    }
		return Collections.unmodifiableSet(contexts);
	}
}
