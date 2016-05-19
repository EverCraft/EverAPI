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
