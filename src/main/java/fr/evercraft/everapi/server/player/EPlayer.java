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
package fr.evercraft.everapi.server.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.boss.ServerBossBar;
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
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;

public class EPlayer extends PlayerStats {
	
	public static final double CONVERSION_FLY = 0.05;
	public static final double CONVERSION_WALF = 0.1;
	
	private boolean disconnect;
	
	/**
	 * Constructeur d'un EPlayer
	 * @param plugin EverAPI
	 * @param player Le joueur
	 */
	public EPlayer(final EverAPI plugin, final Player player){
		super(plugin, player);
		
		this.disconnect = false;
	}
	
	public boolean isDisconnected() {
		return this.disconnect;
	}
	
	public void setDisconnected(boolean disconnect) {
		this.disconnect = disconnect;
	}
	
	public Optional<Team> getTeam() {
		return this.player.getScoreboard().getMemberTeam(this.player.getTeamRepresentation());
	}
	
	
	/*
	 * Autres
	 */

	/**
	 * Affiche un message au joueur
	 * @param message Le message
	 */
	public void sendMessage(final String message){
		sendMessage(EChat.of(message));
	}
	
	/**
	 * Envoyer un message à tous les joueurs
	 * @param message Le message
	 */
	public void broadcast(final String message){
		this.broadcast(EChat.of(message));
	}
	
	/**
	 * Envoyer un message à tous les joueurs
	 * @param message Le message
	 */
	public void broadcast(final Text message){
		for(EPlayer player : this.plugin.getEServer().getOnlineEPlayers()) {
			if(!player.ignore(this.getUniqueId())) {
				player.sendMessage(message);
			}
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
			this.setRemainingAir(this.getMaxAir());
			this.clearPotions();
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
						horse.transferToWorld(world, vector);
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
		return teleportSafeZone(this.getTransform().setLocation(location));
	}
	
	/**
	 * Téléporter un joueur à un endroit sûr
	 * @param transform Le transform
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafe(final Transform<World> transform) {
		if(this.isGod() || this.getGameMode().equals(GameModes.CREATIVE) || this.plugin.getEverAPI().getManagerUtils().getLocation().isPositionSafe(transform)) {
			Transform<World> back = this.getTransform();
			return this.setTransform(transform) && this.setBack(back);
		}
		return false;
	}
	
	/**
	 * Téléporter un joueur à un endroit sûr
	 * @param location La location
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafeZone(final Location<World> location) {
		return teleportSafeZone(this.getTransform().setLocation(location));
	}
	
	/**
	 * Téléporter un joueur à un endroit sûr
	 * @param transform Le transform
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafeZone(final Transform<World> transform) {
		Optional<Transform<World>> optTransform = this.plugin.getEverAPI().getManagerUtils().getLocation().getBlock(
														transform, !(this.isGod() || this.getGameMode().equals(GameModes.CREATIVE)));
		if(optTransform.isPresent()) {
			Transform<World> back = this.getTransform();
			return this.setTransform(optTransform.get()) && this.setBack(back);
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
	
	public Map<EPlayer, Integer> getEPlayers(int distance) {
		Map<EPlayer, Integer> list = new HashMap<EPlayer, Integer>();
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
    
	/*
	 * DisplayName
	 */
	
	public String getDisplayName() {
		return getDisplayName(getActiveContexts());
	}
	
	public String getDisplayName(Set<Context> contexts) {
		return getPrefix(contexts).orElse("") + this.getName() + getSuffix(contexts).orElse("");
	}
	
	public Text getDisplayNameHover() {
		return getDisplayHover(getActiveContexts());
	}
	
	public Text getDisplayHover(Set<Context> contexts) {
		Optional<String> suggest = getSuggest(contexts);
		Optional<Text> hover = getHover(contexts);
		
		String name = this.plugin.getChat().replace(getDisplayName(contexts));
		name = this.plugin.getChat().replaceGlobal(name);
		name = this.plugin.getChat().replacePlayer(this, name);
		Builder builder = this.plugin.getChat().replaceFormat(this, name).toBuilder();
		
		if(suggest.isPresent()) {
			builder.onClick(TextActions.suggestCommand(suggest.get()));
		}
		
		if(hover.isPresent()) {
			builder.onHover(TextActions.showText(hover.get()));
		}
		
		return builder.build();
	}
	
	public Optional<String> getPrefix() {
		return getPrefix(getActiveContexts());
	}
	
	public Optional<String> getPrefix(Set<Context> contexts) {
		return this.getOption(contexts, "prefix");
	}
	
	public Optional<String> getSuffix() {
		return getSuffix(getActiveContexts());
	}
	
	public Optional<String> getSuffix(Set<Context> contexts) {
		return this.getOption(contexts, "suffix");
	}
	
	public Optional<Text> getHover() {
		return getHover(getActiveContexts());
	}
	
	public Optional<Text> getHover(Set<Context> contexts) {
		Optional<String> optHover = this.getOption(contexts, "hover");
		if(optHover.isPresent()) {
			String hover = this.plugin.getChat().replace(optHover.get());
			hover = this.plugin.getChat().replaceGlobal(hover);
			hover = this.plugin.getChat().replacePlayer(this, hover);
			return Optional.of(this.plugin.getChat().replaceFormat(this, hover));
		}
		return Optional.empty();
	}
	
	public Optional<String> getSuggest() {
		return getSuggest(getActiveContexts());
	}
	
	public Optional<String> getSuggest(Set<Context> contexts) {
		Optional<String> optHover = this.getOption(contexts, "suggest");
		if(optHover.isPresent()) {
			String hover = this.plugin.getChat().replaceGlobal(optHover.get());
			return Optional.of(this.plugin.getChat().replacePlayer(this, hover));
		}
		return Optional.empty();
	}
	
	public boolean sendActionBar(String identifier, long stay, Text message) {
		if(this.plugin.getManagerService().getActionBar().isPresent()) {
			return this.plugin.getManagerService().getActionBar().get().send(this, identifier, stay, message);
		}
		return false;
	}
	
	public boolean sendActionBar(String identifier, int priority, long stay, Text message) {
		if(this.plugin.getManagerService().getActionBar().isPresent()) {
			return this.plugin.getManagerService().getActionBar().get().send(this, identifier, priority, stay, message);
		}
		return false;
	}
	
	public boolean sendTitle(String identifiant, Title title) {
		if(this.plugin.getManagerService().getTitle().isPresent()) {
			return this.plugin.getManagerService().getTitle().get().send(this, identifiant, title);
		}
		return false;
	}
	
	public boolean sendTitle(String identifiant, int priority, Title title) {
		if(this.plugin.getManagerService().getTitle().isPresent()) {
			return this.plugin.getManagerService().getTitle().get().send(this, identifiant, priority, title);
		}
		return false;
	}

	public void sendMessageVariables(String message) {
		this.sendMessage(this.plugin.getChat().replaceAllVariables(this, message));
	}

	public Text replaceVariable(String message) {
		return this.plugin.getChat().replaceAllVariables(this, message);
	}
	
	public boolean addObjective(DisplaySlot display, Objective objective) {
		if(this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().addObjective(this, display, objective);
		}
		return false;
	}
	
	public boolean addObjective(int priority, DisplaySlot display, Objective objective) {
		if(this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().addObjective(this, priority, display, objective);
		}
		return false;
	}
	
	public boolean removeObjective(DisplaySlot display, Objective objective) {
		if(this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().removeObjective(this, display, objective);
		}
		this.getScoreboard().removeObjective(objective);
		return true;
	}
	
	public boolean removeObjective(DisplaySlot display, String identifier) {
		if(this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().removeObjective(this, display, identifier);
		} else {
			Optional<Objective> objective = this.getScoreboard().getObjective(identifier);
			if(objective.isPresent()) {
				this.getScoreboard().removeObjective(objective.get());
				return true;
			}
		}
		return false;
	}

	public Collection<EPlayer> getOnlinePlayers() {
		return this.plugin.getEServer().getOnlineEPlayers(this);
	}
	
	/*
	 * EverInformations
	 */
	
	public boolean sendNameTag(String identifier, Text teamRepresentation, Text prefix, Text suffix) {
		if(this.plugin.getManagerService().getNameTag().isPresent()) {
			return this.plugin.getManagerService().getNameTag().get().sendNameTag(this, identifier, teamRepresentation, prefix, suffix);
		}
		return false;
	}
	
	public boolean removeNameTag(String identifier, Text teamRepresentation) {
		if(this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getNameTag().get().removeNameTag(this, identifier, teamRepresentation);
		}
		return false;
	}
	
	public boolean clearNameTag(String identifier) {
		if(this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getNameTag().get().clearNameTag(this, identifier);
		}
		return false;
	}
	
	public boolean sendTabList(String identifier) {
		if(this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().sendTabList(this, identifier);
		}
		return false;
	}
	
	public boolean sendTabList(String identifier, int priority) {
		if(this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().sendTabList(this, identifier, priority);
		}
		return false;
	}
	
	public boolean removeTabList(String identifier) {
		if(this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().removeTabList(this, identifier);
		}
		return false;
	}
	
	public boolean hasTabList(String identifier) {
		if(this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().hasTabList(this, identifier);
		}
		return false;
	}
	
	public Player get() {
		return this.player;
	}
	
	/*
	 * BossBar
	 */
	
	public boolean addBossBar(String identifier, ServerBossBar bossbar) {
		if(this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().add(this, identifier, bossbar);
		}
		return false;
	}
	
	public boolean addBossBar(String identifier, int priority, ServerBossBar bossbar) {
		if(this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().add(this, identifier, priority, bossbar);
		}
		return false;
	}
	
	public boolean removeBossBar(String identifier) {
		if(this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().remove(this, identifier);
		}
		return true;
	}
	
	public Optional<ServerBossBar> getBossBar(String identifier) {
		if(this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().get(this, identifier);
		}
		return Optional.empty();
	}

	public void broadcastMessage(Text message, String permission) {
		for(EPlayer other : this.plugin.getEServer().getOnlineEPlayers()) {
			if(!this.equals(other) && other.hasPermission(permission)) {
				other.sendMessage(message);
			}
		}
	}
	
}
