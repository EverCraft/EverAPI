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
package fr.evercraft.everapi.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.World;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;

public class EServer extends ServerWarp {	
	private String name;
	
	private ConcurrentMap<UUID, EPlayer> players;
	
	public EServer(EverAPI plugin){
		super(plugin);
		
		this.players = new ConcurrentHashMap<UUID, EPlayer>();
		
		reload();
	}
	
	public void reload(){
		this.name = this.plugin.getConfigs().get("server.name").getString("");
	}
	
	public String getName(){
		return this.name;
	}
	
	/*
	 * EPlayers
	 */	
	/**
	 * Ajouter un EPlayer
	 * @param player
	 * @return
	 */
	private void addEPlayer(Player player) {
		if(player != null) {
			players.put(player.getUniqueId(), new EPlayer(this.plugin, player));
		}
	}
	
	/**
	 * Supprimer un EPlayer
	 * @param player
	 * @return
	 */
	public EPlayer removeEPlayer(Player player) {
		 return players.remove(player.getUniqueId());
	}
	
	/**
	 * Retourne un EPlayer
	 * @param player Le pseudo ou UUID du joueur
	 * @return Un EPlayer
	 */	
	public Optional<EPlayer> getEPlayer(String name){
		try {
			if(name.length() == 36){
				return getEPlayer(UUID.fromString(name));
			} else {
				Optional<Player> player = this.getPlayer(name);
				if(player.isPresent()){
					return getEPlayer(player.get().getUniqueId());
				}
			}
		} catch(IllegalArgumentException e) {}
		return Optional.empty();
	}
	
	/**
	 * Retourne un EPlayer
	 * @param player Un Player
	 * @return  Un EPlayer
	 */
	public Optional<EPlayer> getEPlayer(Player player) {
		return getEPlayer(player.getUniqueId());
	}
	
	/**
	 * Retourne un EPlayer
	 * @param uniqueId L'UUID du joueur
	 * @return Un EPlayer
	 */
	public Optional<EPlayer> getEPlayer(UUID uniqueId) {
		if(!this.players.containsKey(uniqueId)){
			addEPlayer(getPlayer(uniqueId).orElse(null));
		}
		return Optional.ofNullable(players.get(uniqueId));
	}
	
	/**
	 * Retourne la liste des EPlayer en ligne
	 * @return
	 */
	public Collection<EPlayer> getOnlineEPlayers() {
		return this.players.values();
	}
	
	public Collection<EPlayer> getOnlineEPlayers(EPlayer player) {
		List<EPlayer> players = new ArrayList<EPlayer>();
		for(EPlayer onlinePlayer : this.players.values()) {
			if(!onlinePlayer.isVanish() || player.canSeePlayer(onlinePlayer)) {
				players.add(onlinePlayer);
			}
		}
		return players;
	}
	
	
	public Optional<User> getUser(String identifier){
		Preconditions.checkNotNull(identifier, "identifier");
		
		try {
			if(this.plugin.getEverAPI().getManagerService().getUserStorage().isPresent()) {
				if(identifier.length() == 36){
					return this.plugin.getEverAPI().getManagerService().getUserStorage().get().get(UUID.fromString(identifier));
				} else {
					return this.plugin.getEverAPI().getManagerService().getUserStorage().get().get(identifier);
				}
			} else {
				if(identifier.length() == 36){
					return Optional.ofNullable(this.getPlayer(UUID.fromString(identifier)).orElse(null));
				} else {
					return Optional.ofNullable(this.getPlayer(identifier).orElse(null));
				}
			}
		} catch(IllegalArgumentException e) {}
		return Optional.empty();
	}
	
	public Optional<User> getUser(UUID identifier){
		Preconditions.checkNotNull(identifier, "identifier");
		
		if(this.plugin.getEverAPI().getManagerService().getUserStorage().isPresent()) {
			return this.plugin.getEverAPI().getManagerService().getUserStorage().get().get(identifier);
		} else {
			return Optional.ofNullable(this.getPlayer(identifier).orElse(null));
		}
	}
	
	public Optional<GameProfile> getGameProfile(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		try {
			if(identifier.length() == 36){
				return this.getGameProfile(UUID.fromString(identifier));
			} else {
				Optional<Player> player = this.getPlayer(identifier);
				if(player.isPresent()) {
					return Optional.ofNullable(player.get().getProfile());
				} else {
					return Optional.ofNullable(this.plugin.getEServer().getGameProfileManager().get(identifier).get());
				}
			}
		} catch(IllegalArgumentException | InterruptedException | ExecutionException e) {}
		return Optional.empty();
	}
	
	public Optional<GameProfile> getGameProfile(UUID identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		Optional<Player> player = this.getPlayer(identifier);
		if(player.isPresent()) {
			return Optional.ofNullable(player.get().getProfile());
		} else {
			try {
				return Optional.ofNullable(this.plugin.getEServer().getGameProfileManager().get(identifier).get());
			} catch (InterruptedException | ExecutionException e) {} 
		}
		return Optional.empty();
	}
	
	/**
	 * Retourne le nombre de joueur qui n'est pas en vanish
	 * @return Le nombre de joueur n'est pas en vanish
	 */
	public int playerNotVanish(){
		int cpt = 0;
		for (EPlayer player : getOnlineEPlayers()){
	    	if (!player.isVanish()){
	    		cpt++;
	    	}
	    }
		return cpt;
	}
	
	/**
	 * Retourne le nombre de joueur qu'un joueur peut voir
	 * @param player Le joueur
	 * @return Le nombre de joueur qu'un joueur peut voir
	 */
	public int playerNotVanish(EPlayer player){
		return this.getOnlineEPlayers(player).size();
	}
	
	/**
	 * Envoyer un message à tous les joueurs
	 * @param message Le message
	 */
	public void broadcast(String message){
		this.getBroadcastChannel().send(Text.of(message));
	}
	
	/**
	 * Envoyer un title à tous les joueurs
	 * @param title Le title
	 */
	public void broadcast(Title title){
		for (EPlayer player : getOnlineEPlayers()){
	    	player.sendTitle(title);
	    }
	}

	public Transform<World> getSpawn() {
		Optional<World> world = this.server.getWorld(this.server.getDefaultWorldName());
		if(world.isPresent()) {
			return new Transform<World>(world.get().getSpawnLocation());
		}
		return null;
	}
}