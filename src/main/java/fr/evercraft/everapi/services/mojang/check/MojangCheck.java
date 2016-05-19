package fr.evercraft.everapi.services.mojang.check;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.mojang.check.MojangServer.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

public class MojangCheck {
	
	private static final String URL = "https://status.mojang.com/check";
	private static final long UPDATE = 120000;
	
	private final EverAPI plugin;
	
	private final Gson gson;
	
	private long last;

    public MojangCheck(final EverAPI plugin) {
        this.plugin = plugin;
        
        this.gson = new Gson();
        this.last = 0;
        
        try {
			this.update();
		} catch (IOException e) {
			this.plugin.getLogger().warn("Erreur pendant la requete MojangCheck");
		}
    }
    
    public boolean update() throws IOException {
    	if(this.last + UPDATE < System.currentTimeMillis()) {
    		this.last = System.currentTimeMillis();
    		this.requete();
    		return true;
    	}
    	return false;
    }

    private void requete() throws IOException {
    	this.plugin.getLogger().debug("MojangCheck : Update");
    	
        InputStream inputStream = new URL(URL).openConnection().getInputStream();

        JsonArray services = this.gson.fromJson(new InputStreamReader(inputStream), JsonArray.class);

		for (JsonElement json : services.getAsJsonArray()) {
			Iterator<Entry<String, JsonElement>> iterator = json.getAsJsonObject().entrySet().iterator();
			if(iterator.hasNext()) {
				Entry<String, JsonElement> service = iterator.next();

				Optional<MojangServer> url = MojangServer.get(service.getKey());
	        	Optional<Color> color = Color.get(service.getValue().getAsString());
	        	
	        	if(url.isPresent() && color.isPresent()) {
	        		if(!url.get().getColor().equals(color.get())) {
	        			this.plugin.getGame().getEventManager().post(new MojangCheckEvent(this.plugin, url.get(), url.get().getColor(), color.get()));
	        			url.get().setColor(color.get());
	        		}
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
