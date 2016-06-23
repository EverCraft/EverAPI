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
	
	private static final String URL = "https://api.mojang.com/user/profiles/%s/names";
	
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

                            public List<NameHistory> load(UUID uuid) throws IOException {
                                return requete(uuid);
                            }
                        }
                );
    }
    
    public void reload() {
		this.players.cleanUp();
	}
    
    public List<NameHistory> get(UUID uuid) throws ExecutionException {
    	return this.players.get(uuid);
    }
    
    private List<NameHistory> requete(UUID uuid) throws IOException {
    	this.plugin.getLogger().debug("MojangNameHistory : Requete(uuid='" + uuid.toString() + "')");
    	
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
