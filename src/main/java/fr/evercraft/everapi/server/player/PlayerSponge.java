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
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.SkinPart;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.sound.SoundCategory;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatVisibility;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.text.translation.Translation;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.util.RelativePositions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;

import com.flowpowered.math.vector.Vector3d;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.user.EUser;

public class PlayerSponge extends EUser implements Player {
	protected Player player;
	
	public PlayerSponge(final EverAPI plugin, final Player player) {
		super(plugin, player);
		
		this.player = player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public boolean isViewingInventory() {
		return this.player.isViewingInventory();
	}

	@Override
	public Optional<Container> getOpenInventory() {
		return this.player.getOpenInventory();
	}

	@Override
	public Optional<Container> openInventory(Inventory inventory, Cause cause) {
		return this.player.openInventory(inventory, cause);
	}

	@Override
	public boolean closeInventory(Cause cause) {
		return this.player.closeInventory(cause);
	}

	@Override
	public EntityType getType() {
		return this.player.getType();
	}

	@Override
	public World getWorld() {
		return this.player.getWorld();
	}

	@Override
	public EntitySnapshot createSnapshot() {
		return this.player.createSnapshot();
	}

	@Override
	public Random getRandom() {
		return this.player.getRandom();
	}

	@Override
	public Location<World> getLocation() {
		return this.player.getLocation();
	}

	@Override
	public boolean setLocation(Location<World> location) {
		return this.player.setLocation(location);
	}

	@Override
	public Vector3d getRotation() {
		return this.player.getRotation();
	}

	@Override
	public void setRotation(Vector3d rotation) {
		this.player.setRotation(rotation);
	}

	@Override
	public boolean setLocationAndRotation(Location<World> location, Vector3d rotation) {
		return this.player.setLocationAndRotation(location, rotation);
	}

	@Override
	public boolean setLocationAndRotation(Location<World> location, Vector3d rotation, EnumSet<RelativePositions> relativePositions) {
		return this.player.setLocationAndRotation(location, rotation, relativePositions);
	}
	
	@Override
	public Vector3d getScale() {
		return this.player.getScale();
	}

	@Override
	public void setScale(Vector3d scale) {
		this.player.setScale(scale);
	}

	@Override
	public Transform<World> getTransform() {
		return this.player.getTransform();
	}

	@Override
	public boolean setTransform(Transform<World> transform) {
		return this.player.setTransform(transform);
	}

	@Override
	public Optional<Entity> getVehicle() {
		return this.player.getVehicle();
	}

	@Override
	public Entity getBaseVehicle() {
		return this.player.getBaseVehicle();
	}

	@Override
	public boolean isOnGround() {
		return this.player.isOnGround();
	}

	@Override
	public boolean isRemoved() {
		return this.player.isRemoved();
	}

	@Override
	public boolean isLoaded() {
		return this.player.isLoaded();
	}

	@Override
	public void remove() {
		this.player.remove();
	}

	@Override
	public boolean damage(double damage, DamageSource damageSource, Cause cause) {
		return this.player.damage(damage, damageSource, cause);
	}

	@Override
	public UUID getUniqueId() {
		return this.player.getUniqueId();
	}

	@Override
	public int getContentVersion() {
		return this.player.getContentVersion();
	}

	@Override
	public DataContainer toContainer() {
		return this.player.toContainer();
	}

	@Override
	public <T extends Property<?, ?>> Optional<T> getProperty(Class<T> propertyClass) {
		return this.player.getProperty(propertyClass);
	}

	@Override
	public Collection<Property<?, ?>> getApplicableProperties() {
		return this.player.getApplicableProperties();
	}

	@Override
	public <T extends DataManipulator<?, ?>> Optional<T> get(Class<T> containerClass) {
		return this.player.get(containerClass);
	}

	@Override
	public <T extends DataManipulator<?, ?>> Optional<T> getOrCreate(Class<T> containerClass) {
		return this.player.getOrCreate(containerClass);
	}

	@Override
	public boolean supports(Class<? extends DataManipulator<?, ?>> holderClass) {
		return this.player.supports(holderClass);
	}

	@Override
	public <E> DataTransactionResult offer(Key<? extends BaseValue<E>> key, E value) {
		return this.player.offer(key, value);
	}

	@Override
	public DataTransactionResult offer(DataManipulator<?, ?> valueContainer, MergeFunction function) {
		return this.player.offer(valueContainer, function);
	}
	
	@Override
	public <E> DataTransactionResult offer(Key<? extends BaseValue<E>> key, E value, Cause cause) {
		return this.offer(key, value, cause);
	}

	@Override
	public DataTransactionResult offer(DataManipulator<?, ?> valueContainer, MergeFunction function, Cause cause) {
		return this.offer(valueContainer, function, cause);
	}

	@Override
	public DataTransactionResult remove(Class<? extends DataManipulator<?, ?>> containerClass) {
		return this.player.remove(containerClass);
	}

	@Override
	public DataTransactionResult remove(Key<?> key) {
		return this.player.remove(key);
	}

	@Override
	public DataTransactionResult undo(DataTransactionResult result) {
		return this.player.undo(result);
	}

	@Override
	public DataTransactionResult copyFrom(DataHolder that, MergeFunction function) {
		return this.player.copyFrom(that, function);
	}

	@Override
	public Collection<DataManipulator<?, ?>> getContainers() {
		return this.player.getContainers();
	}

	@Override
	public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
		return this.player.get(key);
	}

	@Override
	public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
		return this.player.getValue(key);
	}

	@Override
	public boolean supports(Key<?> key) {
		return this.player.supports(key);
	}

	@Override
	public DataHolder copy() {
		return this.player.copy();
	}

	@Override
	public Set<Key<?>> getKeys() {
		return this.player.getKeys();
	}

	@Override
	public Set<ImmutableValue<?>> getValues() {
		return this.player.getValues();
	}

	@Override
	public Translation getTranslation() {
		return this.player.getTranslation();
	}

	@Override
	public Text getTeamRepresentation() {
		return this.player.getTeamRepresentation();
	}

	@Override
	public <T extends Projectile> Optional<T> launchProjectile(Class<T> projectileClass) {
		return this.player.launchProjectile(projectileClass);
	}

	@Override
	public <T extends Projectile> Optional<T> launchProjectile(Class<T> projectileClass, Vector3d velocity) {
		return this.player.launchProjectile(projectileClass, velocity);
	}

	@Override
	public Optional<ItemStack> getHelmet() {
		return this.player.getHelmet();
	}

	@Override
	public void setHelmet(ItemStack helmet) {
		this.player.setHelmet(helmet);
	}

	@Override
	public Optional<ItemStack> getChestplate() {
		return this.player.getChestplate();
	}

	@Override
	public void setChestplate(ItemStack chestplate) {
		this.player.setChestplate(chestplate);
	}

	@Override
	public Optional<ItemStack> getLeggings() {
		return this.player.getLeggings();
	}

	@Override
	public void setLeggings(ItemStack leggings) {
		this.player.setLeggings(leggings);
	}

	@Override
	public Optional<ItemStack> getBoots() {
		return this.player.getBoots();
	}

	@Override
	public void setBoots(ItemStack boots) {
		this.player.setBoots(boots);
	}

	@Override
	public boolean canEquip(EquipmentType type) {
		return this.player.canEquip(type);
	}

	@Override
	public boolean canEquip(EquipmentType type, ItemStack equipment) {
		return this.player.canEquip(type, equipment);
	}

	@Override
	public Optional<ItemStack> getEquipped(EquipmentType type) {
		return this.player.getEquipped(type);
	}

	@Override
	public boolean equip(EquipmentType type, ItemStack equipment) {
		return this.player.equip(type, equipment);
	}

	@Override
	public CarriedInventory<? extends Carrier> getInventory() {
		return this.player.getInventory();
	}

	@Override
	public String getName() {
		return this.player.getName();
	}

	@Override
	public GameProfile getProfile() {
		return this.player.getProfile();
	}

	@Override
	public boolean isOnline() {
		return this.player.isOnline();
	}

	@Override
	public Optional<Player> getPlayer() {
		return this.player.getPlayer();
	}
	
	@Override
	public void sendMessage(Text message) {
		this.player.sendMessage(message);
	}

	@Override
	public MessageChannel getMessageChannel() {
		return this.player.getMessageChannel();
	}

	@Override
	public void setMessageChannel(MessageChannel channel) {
		this.player.setMessageChannel(channel);
	}

	@Override
	public void spawnParticles(ParticleEffect particleEffect, Vector3d position) {
		this.player.spawnParticles(particleEffect, position);
	}

	@Override
	public void spawnParticles(ParticleEffect particleEffect, Vector3d position, int radius) {
		this.player.spawnParticles(particleEffect, position, radius);
	}

	@Override
	public void playSound(SoundType sound, Vector3d position, double volume) {
		this.player.playSound(sound, position, volume);
	}

	@Override
	public void playSound(SoundType sound, Vector3d position, double volume, double pitch) {
		this.player.playSound(sound, position, volume, pitch);
	}

	@Override
	public void playSound(SoundType sound, Vector3d position, double volume, double pitch, double minVolume) {
		this.player.playSound(sound, position, volume, pitch, minVolume);
	}

	@Override
	public void sendMessage(ChatType type, Text message) {
		this.player.sendMessage(type, message);
	}

	@Override
	public void sendMessages(ChatType type, Text... messages) {
		this.player.sendMessages(type, messages);
	}

	@Override
	public void sendMessages(ChatType type, Iterable<Text> messages) {
		this.player.sendMessages(type, messages);
	}

	@Override
	public void sendTitle(Title title) {
		this.player.sendTitle(title);
	}

	@Override
	public int getViewDistance() {
		return this.player.getViewDistance();
	}

	@Override
	public ChatVisibility getChatVisibility() {
		return this.player.getChatVisibility();
	}

	@Override
	public boolean isChatColorsEnabled() {
		return this.player.isChatColorsEnabled();
	}

	@Override
	public Set<SkinPart> getDisplayedSkinParts() {
		return this.player.getDisplayedSkinParts();
	}

	@Override
	public PlayerConnection getConnection() {
		return this.player.getConnection();
	}

	@Override
	public void sendResourcePack(ResourcePack pack) {
		this.player.sendResourcePack(pack);
	}

	public TabList getTabList() {
		return this.player.getTabList();
	}

	@Override
	public void kick() {
		this.player.kick();
	}

	@Override
	public void kick(Text reason) {
		this.player.kick(reason);
	}

	@Override
	public Scoreboard getScoreboard() {
		return this.player.getScoreboard();
	}

	@Override
	public void setScoreboard(Scoreboard scoreboard) {
		this.player.setScoreboard(scoreboard);
	}

	@Override
	public boolean isSleepingIgnored() {
		return this.player.isSleepingIgnored();
	}

	@Override
	public void setSleepingIgnored(boolean sleepingIgnored) {
		this.player.setSleepingIgnored(sleepingIgnored);
	}

	@Override
	public Optional<UUID> getCreator() {
		return this.player.getCreator();
	}

	@Override
	public Optional<UUID> getNotifier() {
		return this.player.getNotifier();
	}

	@Override
	public void setCreator(UUID uuid) {
		this.player.setCreator(uuid);
	}

	@Override
	public void setNotifier(UUID uuid) {
		this.player.setNotifier(uuid);
	}
	
	@Override
	public Vector3d getHeadRotation() {
		return this.player.getHeadRotation();
	}

	@Override
	public void setHeadRotation(Vector3d rotation) {
		this.player.setHeadRotation(rotation);
	}
	
	@Override
	public void sendBookView(BookView bookView) {
		this.player.sendBookView(bookView);
	}

	@Override
	public void sendBlockChange(int x, int y, int z, BlockState state) {
		this.player.sendBlockChange(x, y, z, state);
	}

	@Override
	public void resetBlockChange(int x, int y, int z) {
		this.player.resetBlockChange(x, y, z);
	}

	@Override
	public List<Entity> getPassengers() {
		return this.player.getPassengers();
	}
	
	@Override
	public boolean addPassenger(Entity entity) {
		return this.player.addPassenger(entity);
	}

	@Override
	public void removePassenger(Entity entity) {
		this.player.removePassenger(entity);	
	}

	@Override
	public void clearPassengers() {
		this.player.clearPassengers();
	}

	@Override
	public boolean setVehicle(Entity entity) {
		return this.player.setVehicle(entity);
	}
	
	@Override
	public boolean hasPassenger(Entity entity) {
		return this.player.hasPassenger(entity);
	}

	@Override
	public void playSound(SoundType sound, SoundCategory category, Vector3d position, double volume) {
		this.player.playSound(sound, position, volume);
	}

	@Override
	public void playSound(SoundType sound, SoundCategory category, Vector3d position, double volume, double pitch) {
		this.player.playSound(sound, position, volume, pitch);
	}

	@Override
	public void playSound(SoundType sound, SoundCategory category, Vector3d position, double volume, double pitch, double minVolume) {
		this.player.playSound(sound, position, volume, pitch, minVolume);
	}

	@Override
	public boolean transferToWorld(World world, Vector3d position) {
		return this.player.transferToWorld(world, position);
	}

	@Override
	public Optional<ItemStack> getItemInHand(HandType handType) {
		return this.player.getItemInHand(handType);
	}

	@Override
	public void setItemInHand(HandType hand, ItemStack itemInHand) {
		this.player.setItemInHand(hand, itemInHand);
	}
	
	@Override
	public Optional<AABB> getBoundingBox() {
		return this.player.getBoundingBox();
	}
	
	@Override
	public EntityArchetype createArchetype() {
		return this.player.createArchetype();
	}

	@Override
	public boolean validateRawData(DataView container) {
		return this.player.validateRawData(container);
	}

	@Override
	public void setRawData(DataView container) throws InvalidDataException {
		this.setRawData(container);
	}

	@Override
	public Inventory getEnderChestInventory() {
		return this.player.getEnderChestInventory();
	}

	@Override
	public boolean respawnPlayer() {
		return this.player.respawnPlayer();
	}

	@Override
	public Chat simulateChat(Text message, Cause cause) {
		return this.player.simulateChat(message, cause);
	}

	@Override
	public Optional<Entity> getSpectatorTarget() {
		return this.player.getSpectatorTarget();
	}

	@Override
	public void setSpectatorTarget(Entity entity) {
		this.player.setSpectatorTarget(entity);
	}

	@Override
	public Optional<WorldBorder> getWorldBorder() {
		return this.player.getWorldBorder();
	}

	@Override
	public void setWorldBorder(WorldBorder border, Cause cause) {
		this.player.setWorldBorder(border, cause);
	}
}