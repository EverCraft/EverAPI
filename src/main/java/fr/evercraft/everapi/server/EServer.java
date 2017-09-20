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

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
import fr.evercraft.everapi.server.user.EUser;

public class EServer extends ServerWarp {
	public static final int UUID_LENGTH = 36;
	
	private String name;
	
	public CopyOnWriteArraySet<UUID> disconnects;
	
	public EServer(EverAPI plugin){
		super(plugin);
		
		this.disconnects = new CopyOnWriteArraySet<UUID>();
		
		this.reload();
	}

	public void reload(){
		this.name = this.plugin.getConfigs().getServerName();
	}
	
	public String getName(){
		return this.name;
	}
	
	/*
	 * EPlayers
	 */
	
	/**
	 * Retourne un EPlayer
	 * @param player Le pseudo ou UUID du joueur
	 * @return Un EPlayer
	 */	
	public Optional<EPlayer> getEPlayer(String name){
		try {
			if (name.length() == EServer.UUID_LENGTH){
				return getEPlayer(UUID.fromString(name));
			} else {
				Optional<Player> player = this.getPlayer(name);
				if (player.isPresent()){
					return Optional.of(this.getEPlayer(player.get()));
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
	public EPlayer getEPlayer(Player player) {
		return new EPlayer(this.plugin, player);
	}
	
	/**
	 * Retourne un EPlayer
	 * @param uniqueId L'UUID du joueur
	 * @return Un EPlayer
	 */
	public Optional<EPlayer> getEPlayer(UUID uniqueId) {
		Optional<Player> player = this.getPlayer(uniqueId);
		if (player.isPresent()) {
			return Optional.of(this.getEPlayer(player.get()));
		}
		return Optional.empty();
	}
	
	/**
	 * Retourne la liste des EPlayer en ligne
	 * @return
	 */
	public Collection<EPlayer> getOnlineEPlayers() {
		return this.getOnlinePlayers().stream()
				.map(player -> new EPlayer(this.plugin, player))
				.collect(Collectors.toSet());
	}
	
	public Collection<EPlayer> getOnlineEPlayers(EPlayer player) {
		return this.getOnlinePlayers().stream()
				.map(onlinePlayer -> new EPlayer(this.plugin, onlinePlayer))
				.filter(onlinePlayer -> player.canSeePlayer(onlinePlayer))
				.collect(Collectors.toSet());
	}
	
	/*
	 * User
	 */
	
	public Optional<EUser> getOrCreateEUser(String identifier) {
		Optional<EUser> user = this.getEUser(identifier);
		if(user.isPresent()) {
			return user;
		} else {
			Optional<GameProfile> profile = this.getGameProfile(identifier);
			if (profile.isPresent()) {
				return Optional.of(this.getEUser(this.plugin.getEverAPI().getManagerService().getUserStorage().getOrCreate(profile.get())));
			} else {
				return Optional.empty();
			}
		}
	}
	
	public Optional<EUser> getOrCreateEUser(UUID identifier) {
		Optional<EUser> user = this.getEUser(identifier);
		if(user.isPresent()) {
			return user;
		} else {
			Optional<GameProfile> profile = this.getGameProfile(identifier);
			if (profile.isPresent()) {
				return Optional.of(this.getEUser(this.plugin.getEverAPI().getManagerService().getUserStorage().getOrCreate(profile.get())));
			} else {
				return Optional.empty();
			}
		}
	}
	
	public EUser getOrCreateEUser(GameProfile profile) {
		Optional<EPlayer> player = this.getEPlayer(profile.getUniqueId());
		if(player.isPresent()) {
			return player.get();
		} else {
			return this.getEUser(this.plugin.getEverAPI().getManagerService().getUserStorage().getOrCreate(profile));
		}
	}
	
	public Optional<EUser> getEUser(String identifier) {
		Optional<EPlayer> player = this.getEPlayer(identifier);
		if(player.isPresent()) {
			return Optional.of(player.get());
		} else {
			Optional<User> user = this.getUser(identifier);
			if(user.isPresent()) {
				return Optional.of(this.getEUser(user.get()));
			}
		}
		return Optional.empty();
	}
	
	public Optional<EUser> getEUser(UUID uuid) {
		Optional<EPlayer> player = this.getEPlayer(uuid);
		if(player.isPresent()) {
			return Optional.of(player.get());
		} else {
			Optional<User> user = this.getUser(uuid);
			if(user.isPresent()) {
				return Optional.of(this.getEUser(user.get()));
			}
		}
		return Optional.empty();
	}
	
	public EUser getEUser(User user) {
		return new EUser(this.plugin, user);
	}
	
	public Optional<User> getUser(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		if (identifier.length() == EServer.UUID_LENGTH) {
			try {
				return this.getUser(UUID.fromString(identifier));
			} catch(IllegalArgumentException e) {
				return Optional.empty();
			}
		}
		
		Optional<User> user = this.plugin.getEverAPI().getManagerService().getUserStorage().get(identifier);
		
		// TODO Bug : Il faut faire 2 fois la requete pour avoir le resultat
		if (!user.isPresent()) {
			return this.plugin.getEverAPI().getManagerService().getUserStorage().get(identifier);
		}
		return user;
	}
	
	public Optional<User> getUser(UUID identifier){
		Preconditions.checkNotNull(identifier, "identifier");
		
		Optional<User> user = this.plugin.getEverAPI().getManagerService().getUserStorage().get(identifier);
		
		// TODO Bug : Il faut faire 2 fois la requete pour avoir le resultat
		if (!user.isPresent()) {
			return this.plugin.getEverAPI().getManagerService().getUserStorage().get(identifier);
		}
		return user;
	}
	
	/*
	 * GameProfile
	 */
	
	public Optional<GameProfile> getGameProfile(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		try {
			if (identifier.length() == EServer.UUID_LENGTH) {
				return this.getGameProfile(UUID.fromString(identifier));
			} else {
				Optional<Player> player = this.getPlayer(identifier);
				if (player.isPresent()) {
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
		if (player.isPresent()) {
			return Optional.ofNullable(player.get().getProfile());
		} else {
			try {
				return Optional.ofNullable(this.plugin.getEServer().getGameProfileManager().get(identifier).get());
			} catch (InterruptedException | ExecutionException e) {} 
		}
		return Optional.empty();
	}
	
	public CompletableFuture<GameProfile> getGameProfileFuture(String identifier) {
		Preconditions.checkNotNull(identifier, "identifier");
		
		if (identifier.length() == EServer.UUID_LENGTH) {
			try {
				return this.plugin.getEServer().getGameProfileManager().get(UUID.fromString(identifier));
			} catch(IllegalArgumentException e) {}
		}
		return this.plugin.getEServer().getGameProfileManager().get(identifier);
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
		if (world.isPresent()) {
			return new Transform<World>(world.get().getSpawnLocation());
		}
		return null;
	}
	
	public Optional<World> getEWorld(String identifier) {
		try {
			if (identifier.length() == EServer.UUID_LENGTH){
				return this.getWorld(UUID.fromString(identifier));
			} else {
				return this.getWorld(identifier);
			}
		} catch(IllegalArgumentException e) {}
		return Optional.empty();
	}
}
