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
package fr.evercraft.everapi.sponge;

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.SpawnableData;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class UtilsItemStack {
	

	public static Optional<ItemStack> getItem(final String itemstack) {
		Optional<ItemType> type = UtilsItemType.getItemType(itemstack);
		if (type.isPresent()) {
			return Optional.of(ItemStack.of(type.get(), 1));
		}
		return Optional.empty();
	}

	public static Text getName(final ItemStack item) {
		Optional<SpawnableData> data = item.getOrCreate(SpawnableData.class);
		if (data.isPresent()) {
			return Text.builder(item.getTranslation())
					.append(Text.of(" "))
					.append(Text.of(data.get().type().get().getTranslation()))
					.build();
		}
		return Text.of(item.getTranslation());
	}
	
	public static ItemStack repairInventory(final ItemStack stack) {
		Optional<Integer> data = stack.get(Keys.ITEM_DURABILITY);
		if (data.isPresent()) {
			stack.offer(Keys.ITEM_DURABILITY, Integer.MAX_VALUE);
		}
		return stack;
	}
	
	public static int getMaxDurability(final ItemStack stack) {
		Optional<Integer> data = ItemStack.of(stack.getType(), 1).get(Keys.ITEM_DURABILITY);
		if (data.isPresent()) {
			return data.get();
		}
		return 0;
	}
	
	public static ItemStack createPlayerHead(GameProfile profile) {
		ItemStack skull = ItemStack.of(ItemTypes.SKULL, 1);
		skull.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
		skull.offer(Keys.REPRESENTED_PLAYER, profile);
		return ItemStack.builder()
		        .itemType(ItemTypes.SKULL)
		        .keyValue(Keys.SKULL_TYPE, SkullTypes.PLAYER)
		        .keyValue(Keys.REPRESENTED_PLAYER, profile)
		        .build();
	}
	
	public static void dropItem(final Location<World> location, final ItemStack itemstack, final Cause cause) {
		Entity entity = location.getExtent().createEntity(EntityTypes.ITEM, location.getPosition());
		entity.offer(Keys.REPRESENTED_ITEM, itemstack.createSnapshot());
		location.getExtent().spawnEntity(entity, cause);
	}
}
