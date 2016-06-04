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
package fr.evercraft.everapi.sponge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;

public class UtilsEnchantment {
	public static final List<Enchantment> ENCHANTMENTS = Arrays.asList(
			Enchantments.AQUA_AFFINITY,
			Enchantments.BANE_OF_ARTHROPODS,
			Enchantments.BLAST_PROTECTION,
			Enchantments.DEPTH_STRIDER,
			Enchantments.EFFICIENCY,
			Enchantments.FEATHER_FALLING,
			Enchantments.FIRE_ASPECT,
			Enchantments.FLAME,
			Enchantments.FORTUNE,
			Enchantments.INFINITY,
			Enchantments.KNOCKBACK,
			Enchantments.LOOTING,
			Enchantments.LUCK_OF_THE_SEA,
			Enchantments.LURE,
			Enchantments.POWER,
			Enchantments.PROJECTILE_PROTECTION,
			Enchantments.PROTECTION,
			Enchantments.PUNCH,
			Enchantments.RESPIRATION,
			Enchantments.SHARPNESS,
			Enchantments.SILK_TOUCH,
			Enchantments.SMITE,
			Enchantments.THORNS,
			Enchantments.UNBREAKING	
	);
	
	public static final List<Enchantment> getEnchantments(){
		List<Enchantment> list = new ArrayList<Enchantment>();
		list.addAll(ENCHANTMENTS);
		return list;
	}
	
	public static Optional<Enchantment> getID(final String name) {
		Enchantment enchant = null;
		int cpt = 0;
		while(cpt < ENCHANTMENTS.size() && enchant == null){
			if (ENCHANTMENTS.get(cpt).getId().equalsIgnoreCase(name)) {
				enchant = ENCHANTMENTS.get(cpt);
			}
			cpt++;
		}
		return Optional.of(enchant);
	}
}
