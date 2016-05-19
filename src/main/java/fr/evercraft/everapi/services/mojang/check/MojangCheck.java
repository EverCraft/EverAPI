package fr.evercraft.everapi.services.mojang.check;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.mojang.check.Server.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class MojangCheck {
	
	private static final String URL = "https://status.mojang.com/check";
	
	private final EverAPI plugin;
	
	private final Gson gson;

    public MojangCheck(final EverAPI plugin) {
        this.plugin = plugin;
        
        this.gson = new Gson();
    }

    public void update() throws IOException {
    	Type type = new TypeToken<Map<String, String>>(){}.getType();
    	
        InputStream inputStream = new URL(URL).openConnection().getInputStream();

        Map<String, String> servers = this.gson.fromJson(new InputStreamReader(inputStream), type);
        
       for(Entry<String, String> server : servers.entrySet()) {
        	Optional<Server> url = Server.get(server.getKey());
        	Optional<Color> color = Color.get(server.getValue());
        	if(url.isPresent() && color.isPresent()) {
        		if(!url.get().getColor().equals(color.get())) {
        			this.plugin.getGame().getEventManager().post(new MojangCheckEvent(this.plugin, url.get(), url.get().getColor(), color.get()));
        			url.get().setColor(color.get());
        		}
        	}
        }
    }

	public void reload() {
		try {
			this.update();
		} catch (IOException e) {}
	}

}
