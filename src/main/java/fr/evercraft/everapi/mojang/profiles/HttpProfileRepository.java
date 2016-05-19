package fr.evercraft.everapi.mojang.profiles;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.mojang.http.BasicHttpClient;
import fr.evercraft.everapi.mojang.http.HttpBody;
import fr.evercraft.everapi.mojang.http.HttpClient;
import fr.evercraft.everapi.mojang.http.HttpHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpProfileRepository {
	
	private static final String URL = "https://status.mojang.com/check";
	
	private final EverAPI plugin;
	
	private final Gson gson;

    public HttpProfileRepository(final EverAPI plugin) {
        this.plugin = plugin;
        
        this.gson = new Gson();
    }

    private void update() throws IOException {
    	Type type = new TypeToken<Map<String, String>>(){}.getType();
    	
        InputStream inputStream = new URL(URL).openConnection().getInputStream();

        
        
       /* for(server : this.gson.fromJson(new InputStreamReader(inputStream), type).) {
        	
        }*/
    }

}
