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
import java.util.regex.Pattern;

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
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.sponge.UtilsDirection;

public class EPlayer extends PlayerSponge {
	
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
	
	public boolean isDisconnected() {
		return this.plugin.getEServer().disconnects.contains(this.getUniqueId());
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
	public void broadcastMessage(final String message){
		this.broadcastMessage(EChat.of(message));
	}
	
	/**
	 * Envoyer un message à tous les joueurs
	 * @param message Le message
	 */
	public void broadcastMessage(final Text message){
		for (EPlayer player : this.plugin.getEServer().getOnlineEPlayers()) {
			if (!player.equals(this) && !player.ignore(this.getUniqueId())) {
				player.sendMessage(message);
			}
		}
	}
	
	/*
	 * Teleport
	 */
	
	public boolean reposition() {
		Location<World> location = this.getLocation();
		return this.setLocation(location.setPosition(Vector3d.from(location.getBlockX() + 0.5, location.getY(), location.getBlockZ() + 0.5)));
	}

	public boolean teleport(final World world, final Vector3d vector){
		if (this.getVehicle().isPresent()){
			final Entity horse = this.getVehicle().get();
			if (horse.toContainer().getView(DataQuery.of("UnsafeData")).isPresent()){
				if (horse.toContainer().getView(DataQuery.of("UnsafeData")).get().getString(DataQuery.of("OwnerUUID")).isPresent()){
					UUID owner = UUID.fromString(horse.toContainer().getView(DataQuery.of("UnsafeData")).get().getString(DataQuery.of("OwnerUUID")).get());
					if (this.getUniqueId().equals(owner)){
						this.setVehicle(null);
						horse.transferToWorld(world, vector);
						
						if (this.setLocation(world.getLocation(vector))) {
							this.setVehicle(horse);
							return true;
						}
					}
				}
			}
		}
		
		return this.setLocation(world.getLocation(vector));
	}
	
	/**
	 * Téléporte un joueur et sauvegarde la dernière position
	 * @param location La location
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleport(final Location<World> location, boolean back) {
		return this.teleport(this.getTransform().setLocation(location), back);
	}
	
	/**
	 * Téléporte un joueur et sauvegarde la dernière position
	 * @param transform Le transform
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleport(final Transform<World> transform, boolean back) {
		if (back) {
			Transform<World> back_transform = this.getTransform();
			return this.setTransform(transform) && this.setBack(back_transform);
		} else {
			return this.setTransform(transform);
		}
	}
	
	/**
	 * Téléporter un joueur seulement si la position est SAFE
	 * @param location La location
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafe(final Location<World> location, boolean back) {
		return teleportSafe(this.getTransform().setLocation(location), back);
	}
	
	/**
	 * Téléporter un joueur seulement si la position est SAFE
	 * @param transform Le transform
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafe(final Transform<World> transform, boolean back) {
		if (this.isGod() || this.getGameMode().equals(GameModes.CREATIVE) || this.plugin.getEverAPI().getManagerUtils().getLocation().isPositionSafe(transform)) {
			return this.teleport(transform, back);
		}
		return false;
	}
	
	/**
	 * Téléporter un joueur seulement si il existe un endroit SAFE : changement de y
	 * @param location La location
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafeZone(final Location<World> location, boolean back) {
		return teleportSafeZone(this.getTransform().setLocation(location), back);
	}
	
	/**
	 * Téléporter un joueur seulement si il existe un endroit SAFE : changement de y
	 * @param transform Le transform
	 * @return True si le joueur a bien été téléporté
	 */
	public boolean teleportSafeZone(final Transform<World> transform, boolean back) {
		Optional<Transform<World>> optTransform = this.plugin.getEverAPI().getManagerUtils().getLocation().getBlock(
														transform, !(this.isGod() || this.getGameMode().equals(GameModes.CREATIVE)));
		if (optTransform.isPresent()) {
			return this.teleport(optTransform.get(), back);
		}
		return false;
	}
	
	
	public boolean teleportBottom() {
		return this.teleportBottom(this.getTransform(), false);
	}
	
	public boolean teleportBottom(final Transform<World> transform, boolean back) {
		Optional<Transform<World>> optTransform = this.plugin.getEverAPI().getManagerUtils().getLocation().getBlockBottom(transform);
		if (optTransform.isPresent()) {
			return this.teleport(optTransform.get(), back);
		}
		return false;
	}
	
	/*
	 * Item
	 */
	
	/**
	 * Ajouter un item dans l'inventaire du joueur et de faire drop le reste au sol
	 * @param itemstack L'itemstack
	 * @return True si il y a des items au sol
	 */
	public boolean giveItemAndDrop(final ItemStack itemstack) {
		Optional<ItemStack> reste = this.giveItem(itemstack);
		if (reste.isPresent()) {
			this.dropItem(reste.get());
			return true;
		}
		return false;
	}
	
	/**
	 * Ajouter un item dans l'inventaire du joueur
	 * @param itemstack L'itemstack
	 */
	public Optional<ItemStack> giveItem(final ItemStack itemstack) {
		InventoryTransactionResult transaction = this.getInventory().query(Hotbar.class).offer(itemstack);
		if (!transaction.getRejectedItems().isEmpty()) {
			transaction = this.getInventory().query(GridInventory.class).offer(transaction.getRejectedItems().iterator().next().createStack());
			if (!transaction.getRejectedItems().isEmpty()) {
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
		Entity entity = this.getWorld().createEntity(EntityTypes.ITEM, this.getLocation().getPosition());
		if (entity instanceof Item) {
			Item item = (Item) entity;
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
	public Optional<Vector3i> getViewBlock(final double limit) {
		BlockRay<World> blocks = BlockRay.from(this.player).distanceLimit(limit).build();
		BlockRayHit<World> block = null;
		while(blocks.hasNext() && block == null) {
			BlockRayHit<World> tempoBlock = blocks.next();
			if (!this.getWorld().getBlockType(tempoBlock.getBlockPosition()).equals(BlockTypes.AIR)) {
				block = tempoBlock;
			}
		}
		if (block != null) {
			return Optional.of(block.getBlockPosition());
		}
		return Optional.empty();
	}
	
	public Direction getDirection() {
		Transform<World> transform = this.getTransform();
		return UtilsDirection.of(transform.getPitch(), transform.getYaw());
	}
	
	/*
	 * Java
	 */
	
	/**
	 * Test l'égalité
	 */
	public boolean equals(final Object object){
		if (object instanceof EPlayer){
			return ((EPlayer) object).getUniqueId().equals(this.getUniqueId());
		} else if (object instanceof Subject){
			return ((Subject) object).getIdentifier().equals(this.getIdentifier());
		}
		return this.player.equals(object);
	}
	
	public Map<EPlayer, Integer> getEPlayers(int distance) {
		Map<EPlayer, Integer> list = new HashMap<EPlayer, Integer>();
		distance = Math.max((distance ^ 2) - 1, 0);
		Vector3d vect = this.getLocation().getPosition();
		for (EPlayer player : this.plugin.getEServer().getOnlineEPlayers()) {
			if (!this.equals(player) && this.getWorld().equals(player.getWorld())) {
				//if (player.isHidden(player)) {
					Integer delta = (int) Math.floor(player.getLocation().getPosition().distance(vect));
					if (delta < distance) {
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
	
	public Text getDisplayNameHover() {
		return getDisplayHover(getActiveContexts());
	}
	
	public Optional<Text> getHover() {
		return getHover(getActiveContexts());
	}
	
	
	public Text getDisplayHover(Set<Context> contexts) {
		Optional<String> suggest = this.getSuggest(contexts);
		Optional<Text> hover = this.getHover(contexts);
		
		String name = this.plugin.getChat().replace(this.getDisplayName(contexts));
		Builder builder = EFormatString.of(name).toText(this.getReplaces()).toBuilder();
		
		if (suggest.isPresent()) {
			builder.onClick(TextActions.suggestCommand(suggest.get()));
		}
		
		if (hover.isPresent()) {
			builder.onHover(TextActions.showText(hover.get()));
		}
		
		return builder.build();
	}
	
	
	public Optional<Text> getHover(Set<Context> contexts) {
		Optional<String> optHover = this.getOption(contexts, "hover");
		if (optHover.isPresent()) {
			String hover = this.plugin.getChat().replace(optHover.get());
			return Optional.of(EFormatString.of(hover).toText(this.getReplaces()));
		}
		return Optional.empty();
	}
	
	public Optional<String> getSuggest() {
		return this.getSuggest(this.getActiveContexts());
	}
	
	public Optional<String> getSuggest(Set<Context> contexts) {
		Optional<String> optHover = this.getOption(contexts, "suggest");
		if (optHover.isPresent()) {
			String hover = this.plugin.getChat().replaceGlobal(optHover.get());
			return Optional.of(this.plugin.getChat().replacePlayer(this, hover));
		}
		return Optional.empty();
	}
	
	/*
	 * ActionBar
	 */
	
	public boolean sendActionBar(String identifier, long stay, Text message) {
		if (this.plugin.getManagerService().getActionBar().isPresent()) {
			return this.plugin.getManagerService().getActionBar().get().send(this, identifier, stay, message);
		}
		return false;
	}
	
	public boolean sendActionBar(String identifier, int priority, long stay, Text message) {
		if (this.plugin.getManagerService().getActionBar().isPresent()) {
			return this.plugin.getManagerService().getActionBar().get().send(this, identifier, priority, stay, message);
		}
		return false;
	}
	
	public boolean sendTitle(String identifiant, Title title) {
		if (this.plugin.getManagerService().getTitle().isPresent()) {
			return this.plugin.getManagerService().getTitle().get().send(this, identifiant, title);
		}
		return false;
	}
	
	public boolean sendTitle(String identifiant, int priority, Title title) {
		if (this.plugin.getManagerService().getTitle().isPresent()) {
			return this.plugin.getManagerService().getTitle().get().send(this, identifiant, priority, title);
		}
		return false;
	}
	
	public Map<Pattern, EReplace<?>> getReplaces() {
		return this.plugin.getChat().getReplaceAll(this);
	}
	
	public boolean addObjective(DisplaySlot display, Objective objective) {
		if (this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().addObjective(this, display, objective);
		}
		return false;
	}
	
	public boolean addObjective(int priority, DisplaySlot display, Objective objective) {
		if (this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().addObjective(this, priority, display, objective);
		}
		return false;
	}
	
	public boolean removeObjective(DisplaySlot display, Objective objective) {
		if (this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().removeObjective(this, display, objective);
		}
		this.getScoreboard().removeObjective(objective);
		return true;
	}
	
	public boolean removeObjective(DisplaySlot display, String identifier) {
		if (this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getScoreBoard().get().removeObjective(this, display, identifier);
		} else {
			Optional<Objective> objective = this.getScoreboard().getObjective(identifier);
			if (objective.isPresent()) {
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
		if (this.plugin.getManagerService().getNameTag().isPresent()) {
			return this.plugin.getManagerService().getNameTag().get().sendNameTag(this, identifier, teamRepresentation, prefix, suffix);
		}
		return false;
	}
	
	public boolean removeNameTag(String identifier, Text teamRepresentation) {
		if (this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getNameTag().get().removeNameTag(this, identifier, teamRepresentation);
		}
		return false;
	}
	
	public boolean clearNameTag(String identifier) {
		if (this.plugin.getManagerService().getScoreBoard().isPresent()) {
			return this.plugin.getManagerService().getNameTag().get().clearNameTag(this, identifier);
		}
		return false;
	}
	
	public boolean sendTabList(String identifier) {
		if (this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().sendTabList(this, identifier);
		}
		return false;
	}
	
	public boolean sendTabList(String identifier, int priority) {
		if (this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().sendTabList(this, identifier, priority);
		}
		return false;
	}
	
	public boolean removeTabList(String identifier) {
		if (this.plugin.getManagerService().getTabList().isPresent()) {
			return this.plugin.getManagerService().getTabList().get().removeTabList(this, identifier);
		}
		return false;
	}
	
	public boolean hasTabList(String identifier) {
		if (this.plugin.getManagerService().getTabList().isPresent()) {
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
	
	public boolean sendBossBar(String identifier, ServerBossBar bossbar) {
		if (this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().add(this, identifier, bossbar);
		}
		return false;
	}
	
	public boolean sendBossBar(String identifier, int priority, ServerBossBar bossbar) {
		if (this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().add(this, identifier, priority, bossbar);
		}
		return false;
	}
	
	public boolean sendBossBar(String identifier, long stay, ServerBossBar bossbar) {
		if (this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().add(this, identifier, stay, bossbar);
		}
		return false;
	}
	
	public boolean sendBossBar(String identifier, int priority, long stay, ServerBossBar bossbar) {
		if (this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().add(this, identifier, priority, stay, bossbar);
		}
		return false;
	}
	
	public boolean removeBossBar(String identifier) {
		if (this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().remove(this, identifier);
		}
		return true;
	}
	
	public Optional<ServerBossBar> getBossBar(String identifier) {
		if (this.plugin.getManagerService().getBossBar().isPresent()) {
			return this.plugin.getManagerService().getBossBar().get().get(this, identifier);
		}
		return Optional.empty();
	}

	public void broadcastMessage(Text message, String permission) {
		for (EPlayer other : this.plugin.getEServer().getOnlineEPlayers()) {
			if (!this.equals(other) && other.hasPermission(permission)) {
				other.sendMessage(message);
			}
		}
	}
	
	/*
	 * Essentials
	 */
	
	public boolean addHome(final String identifier) {
		return this.addHome(identifier, this.getTransform());
	}
	
	public boolean moveHome(final String identifier) {
		return this.moveHome(identifier, this.getTransform());
	}
	
	public boolean setBack() {
		return this.setBack(this.getTransform());
	}
	
	public boolean teleportSpawn() {
		return this.player.setTransform(this.getSpawn());
	}
}
