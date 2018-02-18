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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;

public class UtilsEnchantment {
	
	public static  List<EnchantmentType> getAll(){
		return Sponge.getRegistry().getAllOf(EnchantmentType.class).stream().collect(Collectors.toList());
	}
	
	public static Optional<EnchantmentType> getID(final String name) {
		EnchantmentType enchant = null;
		List<EnchantmentType> enchantments = getAll();
		int cpt = 0;
		while(cpt < enchantments.size() && enchant == null){
			if (enchantments.get(cpt).getId().equalsIgnoreCase(name)) {
				enchant = enchantments.get(cpt);
			}
			cpt++;
		}
		return Optional.ofNullable(enchant);
	}
	
	public static boolean canBeAppliedToItemStack(ItemStack item, EnchantmentType enchantment) {
		if (enchantment.canBeAppliedToStack(item)) {
			EnchantmentData enchantment_data = item.getOrCreate(EnchantmentData.class).get();
			
			for (Enchantment enchantment_item : enchantment_data.enchantments()) {
				if (!enchantment.isCompatibleWith(enchantment_item.getType())) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
}
