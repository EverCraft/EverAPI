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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;

public enum UtilsEffect {
	ABSORPTION(PotionEffectTypes.ABSORPTION, 1, 4),
	BLINDNESS(PotionEffectTypes.BLINDNESS, 1, 1),
	FIRE_RESISTANCE	(PotionEffectTypes.FIRE_RESISTANCE, 1, 1),
	GLOWING(PotionEffectTypes.GLOWING, 1, 1),
	HASTE(PotionEffectTypes.HASTE, 1, 2),
	HEALTH_BOOST(PotionEffectTypes.HEALTH_BOOST, 1, 5),
	HUNGER(PotionEffectTypes.HUNGER, 1, 3),
	INSTANT_DAMAGE(PotionEffectTypes.INSTANT_DAMAGE, 1, 2),
	INSTANT_HEALTH(PotionEffectTypes.INSTANT_HEALTH, 1, 2),
	INVISIBILITY(PotionEffectTypes.INVISIBILITY, 1, 1),
	JUMP_BOOST(PotionEffectTypes.JUMP_BOOST, 1, 2),
	LEVITATION(PotionEffectTypes.LEVITATION, 1, 1),
	LUCK(PotionEffectTypes.LUCK, 1, 1),
	MINING_FATIGUE(PotionEffectTypes.MINING_FATIGUE, 1, 3),
	NAUSEA(PotionEffectTypes.NAUSEA, 1, 1),
	NIGHT_VISION(PotionEffectTypes.NIGHT_VISION, 1, 1),
	POISON(PotionEffectTypes.POISON, 1, 4),
	REGENERATION(PotionEffectTypes.REGENERATION, 1, 2),
	RESISTANCE(PotionEffectTypes.RESISTANCE, 1, 2),
	SATURATION(PotionEffectTypes.SATURATION, 1, 20),
	SLOWNESS(PotionEffectTypes.SLOWNESS, 1, 1),
	SPEED(PotionEffectTypes.SPEED, 1, 2),
	UNLUCK(PotionEffectTypes.UNLUCK, 1, 1),
	WATER_BREATHING(PotionEffectTypes.WATER_BREATHING, 1, 2),
	WEAKNESS(PotionEffectTypes.WEAKNESS, 1, 2),
	WITHER(PotionEffectTypes.WITHER, 1, 1);

	private PotionEffectType type;
	private int min;
	private int max;
	private String name;

	UtilsEffect(PotionEffectType type, int min, int max){
		this.type = type;
		this.min = min;
		this.max = max;
		this.name = type.getId().replaceAll("minecraft:", "");
	}
	
	public int getMinAmplifier(){
		return this.min;
	}
	
	public int getMaxAmplifier(){
		return this.max;
	}

	public PotionEffectType getType() {
		return type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static List<String> getEffects(){
		List<String> list = new ArrayList<String>();
		for(UtilsEffect utils : getUtilsEffects()){
			list.add(utils.getName());
		}
		return list;
	}
	
	public static UtilsEffect[] getUtilsEffects(){
		return UtilsEffect.values();
	}
	
	public static Optional<UtilsEffect> getEffect(final String name) {
		UtilsEffect effect = null;
		int cpt = 0;
		UtilsEffect[] utilsEffect = getUtilsEffects();
		while(cpt < utilsEffect.length && effect == null){
			if (utilsEffect[cpt].getName().equalsIgnoreCase(name)) {
				effect = utilsEffect[cpt];
			}
			cpt++;
		}
		return Optional.of(effect);
	}
}