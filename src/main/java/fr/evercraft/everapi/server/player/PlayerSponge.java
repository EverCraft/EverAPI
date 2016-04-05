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

import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.type.SkinPart;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatVisibility;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.text.translation.Translation;
import org.spongepowered.api.util.RelativePositions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import fr.evercraft.everapi.EverAPI;

public abstract class PlayerSponge implements Player, OptionSubject{
	protected final EverAPI plugin;
	protected final Player player;
	
	public PlayerSponge(final EverAPI plugin, final Player player){
		this.plugin = plugin;
		this.player = player;
	}

	public boolean isViewingInventory() {
		return this.player.isViewingInventory();
	}

	public Optional<Inventory> getOpenInventory() {
		return this.player.getOpenInventory();
	}

	public void openInventory(Inventory inventory, Cause cause) {
		this.player.openInventory(inventory, cause);
	}

	public void closeInventory(Cause cause) {
		this.player.closeInventory(cause);
	}

	public EntityType getType() {
		return this.player.getType();
	}

	public World getWorld() {
		return this.player.getWorld();
	}

	public EntitySnapshot createSnapshot() {
		return this.player.createSnapshot();
	}

	public Random getRandom() {
		return this.player.getRandom();
	}

	public Location<World> getLocation() {
		return this.player.getLocation();
	}

	public void setLocation(Location<World> location) {
		this.player.setLocation(location);
	}

	public boolean setLocationSafely(Location<World> location) {
		return this.player.setLocationSafely(location);
	}

	public Vector3d getRotation() {
		return this.player.getRotation();
	}

	public void setRotation(Vector3d rotation) {
		this.player.setRotation(rotation);
	}

	public void setLocationAndRotation(Location<World> location, Vector3d rotation) {
		this.player.setLocationAndRotation(location, rotation);
	}

	public boolean setLocationAndRotationSafely(Location<World> location, Vector3d rotation) {
		return this.player.setLocationAndRotationSafely(location, rotation);
	}

	public void setLocationAndRotation(Location<World> location, Vector3d rotation, EnumSet<RelativePositions> relativePositions) {
		this.player.setLocationAndRotation(location, rotation, relativePositions);
	}

	public boolean setLocationAndRotationSafely(Location<World> location, Vector3d rotation, EnumSet<RelativePositions> relativePositions) {
		return this.player.setLocationAndRotationSafely(location, rotation, relativePositions);
	}

	public Vector3d getScale() {
		return this.player.getScale();
	}

	public void setScale(Vector3d scale) {
		this.player.setScale(scale);
	}

	public Transform<World> getTransform() {
		return this.player.getTransform();
	}

	public void setTransform(Transform<World> transform) {
		this.player.setTransform(transform);
	}

	public boolean transferToWorld(String worldName, Vector3d position) {
		return this.player.transferToWorld(worldName, position);
	}

	public boolean transferToWorld(UUID uuid, Vector3d position) {
		return this.player.transferToWorld(uuid, position);
	}

	public Optional<Entity> getPassenger() {
		return this.player.getPassenger();
	}

	public DataTransactionResult setPassenger(Entity entity) {
		return this.player.setPassenger(entity);
	}

	public Optional<Entity> getVehicle() {
		return this.player.getVehicle();
	}

	public DataTransactionResult setVehicle(Entity entity) {
		return this.player.setVehicle(entity);
	}

	public Entity getBaseVehicle() {
		return this.player.getBaseVehicle();
	}

	public boolean isOnGround() {
		return this.player.isOnGround();
	}

	public boolean isRemoved() {
		return this.player.isRemoved();
	}

	public boolean isLoaded() {
		return this.player.isLoaded();
	}

	public void remove() {
		this.player.remove();
	}

	public boolean damage(double damage, DamageSource damageSource, Cause cause) {
		return this.player.damage(damage, damageSource, cause);
	}

	public UUID getUniqueId() {
		return this.player.getUniqueId();
	}

	public boolean validateRawData(DataContainer container) {
		return this.player.validateRawData(container);
	}

	public void setRawData(DataContainer container) {
		this.player.setRawData(container);
	}

	public int getContentVersion() {
		return this.player.getContentVersion();
	}

	public DataContainer toContainer() {
		return this.player.toContainer();
	}

	public <T extends Property<?, ?>> Optional<T> getProperty(Class<T> propertyClass) {
		return this.player.getProperty(propertyClass);
	}

	public Collection<Property<?, ?>> getApplicableProperties() {
		return this.player.getApplicableProperties();
	}

	public <T extends DataManipulator<?, ?>> Optional<T> get(Class<T> containerClass) {
		return this.player.get(containerClass);
	}

	public <T extends DataManipulator<?, ?>> Optional<T> getOrCreate(Class<T> containerClass) {
		return this.player.getOrCreate(containerClass);
	}

	
	public boolean supports(Class<? extends DataManipulator<?, ?>> holderClass) {
		return this.player.supports(holderClass);
	}

	public <E> DataTransactionResult offer(Key<? extends BaseValue<E>> key, E value) {
		return this.player.offer(key, value);
	}

	public DataTransactionResult offer(DataManipulator<?, ?> valueContainer, MergeFunction function) {
		return this.player.offer(valueContainer, function);
	}

	public DataTransactionResult remove(Class<? extends DataManipulator<?, ?>> containerClass) {
		return this.player.remove(containerClass);
	}

	public DataTransactionResult remove(Key<?> key) {
		return this.player.remove(key);
	}

	public DataTransactionResult undo(DataTransactionResult result) {
		return this.player.undo(result);
	}

	public DataTransactionResult copyFrom(DataHolder that, MergeFunction function) {
		return this.player.copyFrom(that, function);
	}

	public Collection<DataManipulator<?, ?>> getContainers() {
		return this.player.getContainers();
	}

	public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
		return this.player.get(key);
	}

	public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
		return this.player.getValue(key);
	}

	public boolean supports(Key<?> key) {
		return this.player.supports(key);
	}

	public DataHolder copy() {
		return this.player.copy();
	}

	public Set<Key<?>> getKeys() {
		return this.player.getKeys();
	}

	public Set<ImmutableValue<?>> getValues() {
		return this.player.getValues();
	}

	public Translation getTranslation() {
		return this.player.getTranslation();
	}

	public Text getTeamRepresentation() {
		return this.player.getTeamRepresentation();
	}

	public <T extends Projectile> Optional<T> launchProjectile(Class<T> projectileClass) {
		return this.player.launchProjectile(projectileClass);
	}

	public <T extends Projectile> Optional<T> launchProjectile(Class<T> projectileClass, Vector3d velocity) {
		return this.player.launchProjectile(projectileClass, velocity);
	}

	public Optional<ItemStack> getHelmet() {
		return this.player.getHelmet();
	}

	public void setHelmet(ItemStack helmet) {
		this.player.setHelmet(helmet);
	}

	public Optional<ItemStack> getChestplate() {
		return this.player.getChestplate();
	}

	public void setChestplate(ItemStack chestplate) {
		this.player.setChestplate(chestplate);
	}

	public Optional<ItemStack> getLeggings() {
		return this.player.getLeggings();
	}

	public void setLeggings(ItemStack leggings) {
		this.player.setLeggings(leggings);
	}

	public Optional<ItemStack> getBoots() {
		return this.player.getBoots();
	}

	public void setBoots(ItemStack boots) {
		this.player.setBoots(boots);
	}

	public Optional<ItemStack> getItemInHand() {
		return this.player.getItemInHand();
	}

	public void setItemInHand(ItemStack itemInHand) {
		this.player.setItemInHand(itemInHand);
	}

	public boolean canEquip(EquipmentType type) {
		return this.player.canEquip(type);
	}

	public boolean canEquip(EquipmentType type, ItemStack equipment) {
		return this.player.canEquip(type, equipment);
	}

	public Optional<ItemStack> getEquipped(EquipmentType type) {
		return this.player.getEquipped(type);
	}

	public boolean equip(EquipmentType type, ItemStack equipment) {
		return this.player.equip(type, equipment);
	}

	public CarriedInventory<? extends Carrier> getInventory() {
		return this.player.getInventory();
	}

	public String getName() {
		return this.player.getName();
	}

	public GameProfile getProfile() {
		return this.player.getProfile();
	}

	public boolean isOnline() {
		return this.player.isOnline();
	}

	public Optional<Player> getPlayer() {
		return this.player.getPlayer();
	}
	

	public void sendMessage(Text message) {
		this.player.sendMessage(message);
	}

	public MessageChannel getMessageChannel() {
		return this.player.getMessageChannel();
	}

	public void setMessageChannel(MessageChannel channel) {
		this.player.setMessageChannel(channel);
	}

	public void spawnParticles(ParticleEffect particleEffect, Vector3d position) {
		this.player.spawnParticles(particleEffect, position);
	}

	public void spawnParticles(ParticleEffect particleEffect, Vector3d position, int radius) {
		this.player.spawnParticles(particleEffect, position, radius);
	}

	public void playSound(SoundType sound, Vector3d position, double volume) {
		this.player.playSound(sound, position, volume);
	}

	public void playSound(SoundType sound, Vector3d position, double volume, double pitch) {
		this.player.playSound(sound, position, volume, pitch);
	}

	public void playSound(SoundType sound, Vector3d position, double volume, double pitch, double minVolume) {
		this.player.playSound(sound, position, volume, pitch, minVolume);
	}

	public void sendMessage(ChatType type, Text message) {
		this.player.sendMessage(type, message);
	}

	public void sendMessages(ChatType type, Text... messages) {
		this.player.sendMessages(type, messages);
	}

	public void sendMessages(ChatType type, Iterable<Text> messages) {
		this.player.sendMessages(type, messages);
	}

	public void sendTitle(Title title) {
		this.player.sendTitle(title);
	}

	public int getViewDistance() {
		return this.player.getViewDistance();
	}

	public ChatVisibility getChatVisibility() {
		return this.player.getChatVisibility();
	}

	public boolean isChatColorsEnabled() {
		return this.player.isChatColorsEnabled();
	}

	public Set<SkinPart> getDisplayedSkinParts() {
		return this.player.getDisplayedSkinParts();
	}

	public PlayerConnection getConnection() {
		return this.player.getConnection();
	}

	public void sendResourcePack(ResourcePack pack) {
		this.player.sendResourcePack(pack);
	}

	public TabList getTabList() {
		return this.player.getTabList();
	}

	public void kick() {
		this.player.kick();
	}

	public void kick(Text reason) {
		this.player.kick(reason);
	}

	public Scoreboard getScoreboard() {
		return this.player.getScoreboard();
	}

	public void setScoreboard(Scoreboard scoreboard) {
		this.player.setScoreboard(scoreboard);
	}

	public boolean isSleepingIgnored() {
		return this.player.isSleepingIgnored();
	}

	public void setSleepingIgnored(boolean sleepingIgnored) {
		this.player.setSleepingIgnored(sleepingIgnored);
	}

	public Optional<UUID> getCreator() {
		return this.player.getCreator();
	}

	public Optional<UUID> getNotifier() {
		return this.player.getNotifier();
	}

	public void setCreator(UUID uuid) {
		this.player.setCreator(uuid);
	}

	public void setNotifier(UUID uuid) {
		this.player.setNotifier(uuid);
	}

	public Vector3d getHeadRotation() {
		return this.player.getHeadRotation();
	}

	public void setHeadRotation(Vector3d rotation) {
		this.player.setHeadRotation(rotation);
	}
	
	@Override
	public void sendBookView(BookView bookView) {
		this.player.sendBookView(bookView);
	}

}
