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
