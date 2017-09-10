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
package fr.evercraft.everapi.server.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.command.CommandSource;
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
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.SubjectCollection;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.util.Tristate;

import fr.evercraft.everapi.EverAPI;

public class UserSponge implements User {
	
	public static final double MAX_HEATH = 20;
	public static final int MAX_FOOD = 20;
	
	protected final EverAPI plugin;
	protected final User user;
	
	public UserSponge(final EverAPI plugin, final User user){
		this.plugin = plugin;
		this.user = user;
	}

	@Override
	public UUID getUniqueId() {
		return this.user.getUniqueId();
	}

	@Override
	public int getContentVersion() {
		return this.user.getContentVersion();
	}

	@Override
	public DataContainer toContainer() {
		return this.user.toContainer();
	}

	@Override
	public <T extends Property<?, ?>> Optional<T> getProperty(Class<T> propertyClass) {
		return this.user.getProperty(propertyClass);
	}

	@Override
	public Collection<Property<?, ?>> getApplicableProperties() {
		return this.user.getApplicableProperties();
	}

	@Override
	public <T extends DataManipulator<?, ?>> Optional<T> get(Class<T> containerClass) {
		return this.user.get(containerClass);
	}

	@Override
	public <T extends DataManipulator<?, ?>> Optional<T> getOrCreate(Class<T> containerClass) {
		return this.user.getOrCreate(containerClass);
	}

	@Override
	public boolean supports(Class<? extends DataManipulator<?, ?>> holderClass) {
		return this.user.supports(holderClass);
	}

	@Override
	public <E> DataTransactionResult offer(Key<? extends BaseValue<E>> key, E value) {
		return this.user.offer(key, value);
	}

	@Override
	public DataTransactionResult offer(DataManipulator<?, ?> valueContainer, MergeFunction function) {
		return this.user.offer(valueContainer, function);
	}

	@Override
	public DataTransactionResult remove(Class<? extends DataManipulator<?, ?>> containerClass) {
		return this.user.remove(containerClass);
	}

	@Override
	public DataTransactionResult remove(Key<?> key) {
		return this.user.remove(key);
	}

	@Override
	public DataTransactionResult undo(DataTransactionResult result) {
		return this.user.undo(result);
	}

	@Override
	public DataTransactionResult copyFrom(DataHolder that, MergeFunction function) {
		return this.user.copyFrom(that, function);
	}

	@Override
	public Collection<DataManipulator<?, ?>> getContainers() {
		return this.user.getContainers();
	}

	@Override
	public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
		return this.user.get(key);
	}

	@Override
	public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
		return this.user.getValue(key);
	}

	@Override
	public boolean supports(Key<?> key) {
		return this.user.supports(key);
	}

	@Override
	public DataHolder copy() {
		return this.user.copy();
	}

	@Override
	public Set<Key<?>> getKeys() {
		return this.user.getKeys();
	}

	@Override
	public Set<ImmutableValue<?>> getValues() {
		return this.user.getValues();
	}

	@Override
	public Optional<ItemStack> getHelmet() {
		return this.user.getHelmet();
	}

	@Override
	public void setHelmet(ItemStack helmet) {
		this.user.setHelmet(helmet);
	}

	@Override
	public Optional<ItemStack> getChestplate() {
		return this.user.getChestplate();
	}

	@Override
	public void setChestplate(ItemStack chestplate) {
		this.user.setChestplate(chestplate);
	}

	@Override
	public Optional<ItemStack> getLeggings() {
		return this.user.getLeggings();
	}

	@Override
	public void setLeggings(ItemStack leggings) {
		this.user.setLeggings(leggings);
	}

	@Override
	public Optional<ItemStack> getBoots() {
		return this.user.getBoots();
	}

	@Override
	public void setBoots(ItemStack boots) {
		this.user.setBoots(boots);
	}

	@Override
	public boolean canEquip(EquipmentType type) {
		return this.user.canEquip(type);
	}

	@Override
	public boolean canEquip(EquipmentType type, ItemStack equipment) {
		return this.user.canEquip(type, equipment);
	}

	@Override
	public Optional<ItemStack> getEquipped(EquipmentType type) {
		return this.user.getEquipped(type);
	}

	@Override
	public boolean equip(EquipmentType type, ItemStack equipment) {
		return this.user.equip(type, equipment);
	}

	@Override
	public CarriedInventory<? extends Carrier> getInventory() {
		return this.user.getInventory();
	}

	@Override
	public String getName() {
		return this.user.getName();
	}

	@Override
	public GameProfile getProfile() {
		return this.user.getProfile();
	}

	@Override
	public boolean isOnline() {
		return this.user.isOnline();
	}

	@Override
	public Optional<Player> getPlayer() {
		return this.user.getPlayer();
	}
	
	@Override
	public Optional<ItemStack> getItemInHand(HandType handType) {
		return this.user.getItemInHand(handType);
	}

	@Override
	public void setItemInHand(HandType hand, ItemStack itemInHand) {
		this.user.setItemInHand(hand, itemInHand);
	}

	@Override
	public boolean validateRawData(DataView container) {
		return this.user.validateRawData(container);
	}

	@Override
	public void setRawData(DataView container) throws InvalidDataException {
		this.setRawData(container);
	}
	
	@Override
	public Optional<CommandSource> getCommandSource() {
		return this.user.getCommandSource();
	}

	@Override
	public SubjectCollection getContainingCollection() {
		return this.user.getContainingCollection();
	}

	@Override
	public boolean hasPermission(Set<Context> contexts, String permission) {
		return this.user.hasPermission(contexts, permission);
	}

	@Override
	public Tristate getPermissionValue(Set<Context> contexts, String permission) {
		return this.user.getPermissionValue(contexts, permission);
	}

	@Override
	public String getIdentifier() {
		return this.user.getIdentifier();
	}

	@Override
	public Set<Context> getActiveContexts() {
		return this.user.getActiveContexts();
	}

	@Override
	public SubjectData getSubjectData() {
		return this.user.getSubjectData();
	}

	@Override
	public SubjectData getTransientSubjectData() {
		return this.user.getTransientSubjectData();
	}

	@Override
	public Optional<String> getOption(Set<Context> contexts, String key) {
		return this.user.getOption(contexts, key);
	}

	@Override
	public SubjectReference asSubjectReference() {
		return this.user.asSubjectReference();
	}

	@Override
	public boolean isSubjectDataPersisted() {
		return this.user.isSubjectDataPersisted();
	}

	@Override
	public boolean isChildOf(Set<Context> contexts, SubjectReference parent) {
		return this.user.isChildOf(contexts, parent);
	}

	@Override
	public List<SubjectReference> getParents(Set<Context> contexts) {
		return this.user.getParents(contexts);
	}
}
