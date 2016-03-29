/**
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
package fr.evercraft.everapi.server.player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.sponge.UtilsChat;

public class EPlayer extends PlayerEssentials {
	
	public static final double CONVERSION_FLY = 0.05;
	public static final double CONVERSION_WALF = 0.1;
	
	/**
	 * Constructeur d'un EPlayer
	 * @param plugin EverAPI
	 * @param player Le joueur
	 */
	public EPlayer(final EverAPI plugin, final Player player){
		super(plugin, player);
	}
	/*
	 * Autres
	 */

	/**
	 * Affiche un message au joueur
	 * @param message Le message
	 */
	public void sendMessage(final String message){
		sendMessage(UtilsChat.of(message));
	}
	
	/**
	 * Envoyer un message à tous les joueurs
	 * @param message Le message
	 */
	public void broadcast(final String message){
		this.broadcast(UtilsChat.of(message));
	}
	
	/**
	 * Envoyer un message à tous les joueurs
	 * @param message Le message
	 */
	public void broadcast(final Text message){
		for(EPlayer player : this.plugin.getEServer().getOnlineEPlayers()) {
			player.sendMessage(message);
		}
	}
	
	/*
	 * Health
	 */
	
	/**
	 * Soigner le joueur
	 * @return True si le joueur a bien été soigné
	 */
	public boolean heal(){
		if(this.getHealth() != 0) {
			this.setHealth(this.getMaxHealth());
			this.setFood(20);
			this.setSaturation(20);
			this.setFireTicks(0);
			return true;
		}
		return false;
	}
	
	/*
	 * Teleport
	 */

	public boolean teleport(final World world, final Vector3d vector){
		if(this.getVehicle().isPresent()){
			final Entity horse = this.getVehicle().get();
			if(horse.toContainer().getView(DataQuery.of("UnsafeData")).isPresent()){
				if(horse.toContainer().getView(DataQuery.of("UnsafeData")).get().getString(DataQuery.of("OwnerUUID")).isPresent()){
					UUID owner = UUID.fromString(horse.toContainer().getView(DataQuery.of("UnsafeData")).get().getString(DataQuery.of("OwnerUUID")).get());
					if(this.getUniqueId().equals(owner)){
						this.setVehicle(null);
						horse.transferToWorld(world.getName(), vector);
					}
				}
			}
		}
		this.setLocation(world.getLocation(vector));
		return true;
	}
	
	/**
	 * Téléporter un joueur à un endroit sûr
	 * @param location La location
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafe(final Location<World> location) {
		return teleportSafe(this.getTransform().setLocation(location));
	}
	
	/**
	 * Téléporter un joueur à un endroit sûr
	 * @param transform Le transform
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafe(final Transform<World> transform) {
		Optional<Transform<World>> optTransform = this.plugin.getEverAPI().getManagerUtils().getLocation().getBlock(
														transform, 
														!(/*this.isGodModeEnabled() ||*/ this.getGameMode().equals(GameModes.CREATIVE)));
		if(optTransform.isPresent()) {
			
			/*if(this.isViewingInventory()) {
				this.closeInventory();
			}*/
			
			this.setTransform(optTransform.get());
			return true;
		}
		return false;
	}
	
	/*
	 * Item
	 */
	
	/**
	 * Ajouter un item dans l'inventaire du joueur et de faire drop le reste au sol
	 * @param itemstack L'itemstack
	 */
	public void giveItemAndDrop(final ItemStack itemstack) {
		Optional<ItemStack> reste = giveItem(itemstack);
		if(reste.isPresent()) {
			dropItem(reste.get());
		}
	}
	
	/**
	 * Ajouter un item dans l'inventaire du joueur
	 * @param itemstack L'itemstack
	 */
	public Optional<ItemStack> giveItem(final ItemStack itemstack) {
		InventoryTransactionResult transaction = this.getInventory().query(Hotbar.class).offer(itemstack);
		if(!transaction.getRejectedItems().isEmpty()) {
			transaction = this.getInventory().query(GridInventory.class).offer(transaction.getRejectedItems().iterator().next().createStack());
			if(!transaction.getRejectedItems().isEmpty()) {
				return Optional.of(transaction.getRejectedItems().iterator().next().createStack());
			}
		}
		return Optional.empty();
	}
	
	/**
	 * Faire drop un item pied du joueur
	 * @param itemstack L'itemstack
	 */
	public void dropItem(final ItemStack itemstack) {
		Optional<Entity> optional = this.getWorld().createEntity(EntityTypes.ITEM, this.getLocation().getPosition());
		if (optional.isPresent()) {
			Item item = (Item) optional.get();
			item.offer(Keys.REPRESENTED_ITEM, itemstack.createSnapshot());
			this.getWorld().spawnEntity(item, Cause.source(this.getPlayer().get()).build());
		}
	}
	
	/*
	 * View Block
	 */
	
	/**
	 * Retourne la position du block que le joueur regarde
	 * @return La position du block
	 */
	public Optional<Vector3i> getViewBlock() {
		return getViewBlock(200);
	}
	
	/**
	 * Retourne la position du block que le joueur regarde
	 * @param limit La distance maximum du block
	 * @return La position du block
	 */
	public Optional<Vector3i> getViewBlock(final int limit) {
		BlockRay<World> blocks = BlockRay.from(this.player).blockLimit(limit).build();
		BlockRayHit<World> block = null;
		while(blocks.hasNext() && block == null) {
			BlockRayHit<World> tempoBlock = blocks.next();
			if(!this.getWorld().getBlockType(tempoBlock.getBlockPosition()).equals(BlockTypes.AIR)) {
				block = tempoBlock;
			}
		}
		if(block != null) {
			return Optional.of(block.getBlockPosition());
		}
		return Optional.empty();
	}
	
	/*
	 * Java
	 */
	
	/**
	 * Test l'égalité
	 */
	public boolean equals(final Object object){
		if(object instanceof EPlayer){
			return ((EPlayer) object).getUniqueId().equals(this.getUniqueId());
		} else if(object instanceof Subject){
			return ((Subject) object).getIdentifier().equals(this.getIdentifier());
		}
		return this.player.equals(object);
	}
	
	public HashMap<EPlayer, Integer> getEPlayers(int distance) {
		HashMap<EPlayer, Integer> list = new HashMap<EPlayer, Integer>();
		distance = Math.max((distance ^ 2) - 1, 0);
		Vector3d vect = this.getLocation().getPosition();
		for (EPlayer player : this.plugin.getEServer().getOnlineEPlayers()) {
			if(!this.equals(player) && this.getWorld().equals(player.getWorld())) {
				//if(player.isHidden(player)) {
					Integer delta = (int) Math.floor(player.getLocation().getPosition().distance(vect));
					if(delta < distance) {
						list.put(player, delta);
					}
				//}
			}
		}
		return list;
	}
    
}