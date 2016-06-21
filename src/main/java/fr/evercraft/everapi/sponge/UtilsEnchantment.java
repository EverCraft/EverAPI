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
