package fr.evercraft.everapi.sponge;

import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;

public enum UtilsEffect {
	ABSORPTION(PotionEffectTypes.ABSORPTION, 1, 1),
	BLINDNESS(PotionEffectTypes.BLINDNESS),
	FIRE_RESISTANCE	(PotionEffectTypes.FIRE_RESISTANCE),
	GLOWING(PotionEffectTypes.GLOWING),
	HASTE(PotionEffectTypes.HASTE),
	HEALTH_BOOST(PotionEffectTypes.HEALTH_BOOST),
	HUNGER(PotionEffectTypes.HUNGER),
	INSTANT_DAMAGE(PotionEffectTypes.INSTANT_DAMAGE),
	INSTANT_HEALTH(PotionEffectTypes.INSTANT_HEALTH),
	INVISIBILITY(PotionEffectTypes.INVISIBILITY),
	JUMP_BOOST(PotionEffectTypes.JUMP_BOOST),
	LEVITATION(PotionEffectTypes.LEVITATION),
	LUCK(PotionEffectTypes.LUCK),
	MINING_FATIGUE(PotionEffectTypes.MINING_FATIGUE),
	NAUSEA(PotionEffectTypes.NAUSEA),
	NIGHT_VISION(PotionEffectTypes.NIGHT_VISION),
	POISON(PotionEffectTypes.POISON),
	REGENERATION(PotionEffectTypes.REGENERATION),
	RESISTANCE(PotionEffectTypes.RESISTANCE),
	SATURATION(PotionEffectTypes.SATURATION),
	SLOWNESS(PotionEffectTypes.SLOWNESS),
	SPEED(PotionEffectTypes.SPEED),
	UNLUCK(PotionEffectTypes.UNLUCK),
	WATER_BREATHING(PotionEffectTypes.WATER_BREATHING),
	WEAKNESS(PotionEffectTypes.WEAKNESS),
	WITHER(PotionEffectTypes.WITHER);

	private PotionEffectType type;
	private int min;
	private int max;

	UtilsEffect(PotionEffectType type, int min, int max){
		this.type = type;
		this.min = min;
		this.max = max;
	}
	
	UtilsEffect(PotionEffectType type){
		this.type = type;
	}
	
	public int getMinAmplifier(UtilsEffect effect){
		return this.min;
	}
	
	public int getMaxAmplifier(UtilsEffect effect){
		return this.max;
	}

	public PotionEffectType getType() {
		return type;
	}
}
