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
package fr.evercraft.everapi.services.mojang;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.ESpongeEventFactory;
import fr.evercraft.everapi.registers.MojangServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.spongepowered.api.event.cause.Cause;

public class MojangStatus {
	
	private static final String URL = "https://status.mojang.com/check";
	private static final long UPDATE = 120000;
	
	private final EverAPI plugin;
	private final Gson gson;
	
	private long last;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;

    public MojangStatus(final EverAPI plugin) {
        this.plugin = plugin;
        
        this.gson = new Gson();
        this.last = 0;
        
        // MultiThreading
 		this.lock = new ReentrantReadWriteLock();
 		this.write_lock = this.lock.writeLock();
 		this.read_lock = this.lock.readLock();
        
        this.update().join();
    }
    
    public CompletableFuture<Boolean> update()  {
    	this.read_lock.lock();
    	try {
    		if (this.last + UPDATE > System.currentTimeMillis()) return CompletableFuture.completedFuture(true);
	    } finally {
			this.read_lock.unlock();
		}
    	
    	this.write_lock.lock();
    	try {
    		this.last = System.currentTimeMillis();
	    } finally {
			this.write_lock.unlock();
		}
    	
    	return this.requete().thenApply(result -> {
			if (result) return true;
			
			this.write_lock.lock();
	    	try {
	    		this.last = 0;
	    	} finally {
				this.write_lock.unlock();
	    	}
			return false;
		});
    }

    private CompletableFuture<Boolean> requete() {
    	this.plugin.getELogger().debug("MojangCheck : Update");
    	
    	return CompletableFuture.supplyAsync(() -> {
	        InputStream inputStream;
			try {
				inputStream = new URL(URL).openConnection().getInputStream();
				JsonArray services = this.gson.fromJson(new InputStreamReader(inputStream), JsonArray.class);
					
					for (JsonElement json : services.getAsJsonArray()) {
						Iterator<Entry<String, JsonElement>> iterator = json.getAsJsonObject().entrySet().iterator();
						if (!iterator.hasNext()) continue;
						
						Entry<String, JsonElement> service = iterator.next();
		
						Optional<MojangServer> url = this.plugin.getGame().getRegistry().getType(MojangServer.class, service.getKey());
			        	Optional<MojangServer.Status> status = MojangServer.Status.get(service.getValue().getAsString());
			        	
			        	if (url.isPresent() && status.isPresent() && !url.get().getStatus().equals(status.get())) {
		        			this.plugin.getGame().getEventManager().post(
		        					ESpongeEventFactory.createMojangCheckEvent(url.get(), url.get().getStatus(), status.get(), this.plugin.getGame().getCauseStackManager().getCurrentCause()));
		        			url.get().setStatus(status.get());
			        	}
					}
					return true;
			} catch (IOException e) {
				this.plugin.getELogger().warn("Erreur pendant la requete MojangCheck");
				return false;
			}
    	}, this.plugin.getThreadAsync());
    }

	public void reload() {
		this.update().join();
	}

}
