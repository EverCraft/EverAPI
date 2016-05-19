package fr.evercraft.everapi.services.mojang.namehistory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.evercraft.everapi.EverAPI;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MojangNameHistory {
	
	private static final String URL = "https://status.mojang.com/user/profiles/%s/names";
	
	@SuppressWarnings("unused")
	private final EverAPI plugin;
	
	private final Gson gson;
	
	private final LoadingCache<UUID, List<NameHistory>> players;
	
    public MojangNameHistory(final EverAPI plugin) {
        this.plugin = plugin;
        
        this.gson = (new GsonBuilder()).registerTypeAdapter(NameHistory.class, new NameHistoryDeserializer()).create();
        
        this.players = CacheBuilder.newBuilder()
                .maximumSize(500)
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<UUID, List<NameHistory>>() {

                            public List<NameHistory> load(UUID uuid) throws IOException{
                                return getPlayerNameHistoryInternal(uuid);
                            }
                        }
                );
    }
    
    public void reload() {
		this.players.cleanUp();
	}
    
    public List<NameHistory> getPlayerNameHistory(UUID uuid) throws ExecutionException {
    	return this.players.get(uuid);
    }
    
    private List<NameHistory> getPlayerNameHistoryInternal(UUID uuid) throws IOException {
        InputStream inputStream = getAPIResponse(uuid);

        NameHistory[] names = gson.fromJson(new InputStreamReader(inputStream), NameHistory[].class);

        if (names != null) {
            return Arrays.asList(names);
        }
        return Arrays.asList();
    }
    
    private InputStream getAPIResponse(UUID uuid) throws MalformedURLException, IOException {
    	return new URL(String.format(URL, uuid.toString().replace("-", ""))).openConnection().getInputStream();
    }

}
