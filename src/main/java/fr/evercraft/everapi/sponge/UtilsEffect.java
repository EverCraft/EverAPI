package fr.evercraft.everapi.sponge;

import java.util.ArrayList;
import java.util.List;

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
	MINING_FATIGUE(PotionEffectTypes.MINING_FATIGUE, 1, 1),
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
		this.name = type.getName().replaceAll("minecraft:", ""); 
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
	
	public String getName(){
		return this.name;
	}
	
	public List<String> getEffects(){
		List<String> list = new ArrayList<String>();
		for(UtilsEffect utils : getUtilsEffects()){
			list.add(utils.getName());
		}
		return list;
	}
	
	public UtilsEffect[] getUtilsEffects(){
		return UtilsEffect.values();
	}
}