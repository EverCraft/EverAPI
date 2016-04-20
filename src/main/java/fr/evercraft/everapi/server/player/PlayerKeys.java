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

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import fr.evercraft.everapi.EverAPI;

public abstract class PlayerKeys extends PlayerSponge {
	
	public static final double MAX_HEATH = 20;
	public static final int MAX_FOOD = 20;
	
	public PlayerKeys(EverAPI plugin, Player player){
		super(plugin, player);
	}

	/*
	 * Dead
	 */
	
	public boolean isDead() {
		return this.getHealth() <= 0;
	}
	
	/*
	 * Health
	 */
	
	public double getHealth(){
		return this.get(Keys.HEALTH).orElse(0.0);
	}
	
	public boolean setHealth(final double health){
		return this.offer(Keys.HEALTH, health).isSuccessful();
	}
	
	/*
	 * HealthScale
	 */
	
	public double getHealthScale(){
		return this.get(Keys.HEALTH_SCALE).orElse(0.0);
	}
	
	public boolean setHealthScale(final double health){
		return this.offer(Keys.HEALTH_SCALE, health).isSuccessful();
	}
	
	/*
	 * Max Health
	 */
	
	public double getMaxHealth(){
		return this.get(Keys.MAX_HEALTH).orElse(1.0);
	}
	
	public boolean setMaxHealth(final double max_health){
		return this.offer(Keys.MAX_HEALTH, max_health).isSuccessful();
	}
	
	public boolean resetMaxHealth(){
		return setMaxHealth(MAX_HEATH);
	}
	
	/*
	 * Food
	 */
	
	public int getFood(){
		return get(Keys.FOOD_LEVEL).orElse(0);
	}
	
	public boolean setFood(final int food){
		return this.offer(Keys.FOOD_LEVEL, food).isSuccessful();
	}
	
	/*
	 * Saturation
	 */
	
	public double getSaturation(){
		return get(Keys.SATURATION).orElse(0.0);
	}
	
	public boolean setSaturation(final double saturation){
		return this.offer(Keys.SATURATION, saturation).isSuccessful();
	}
	
	/*
	 * Exhaustion
	 */
	
	public double getExhaustion(){
		return get(Keys.EXHAUSTION).orElse(0.0);
	}
	
	public boolean setExhaustion(final double Exhaustion){
		return this.offer(Keys.EXHAUSTION, Exhaustion).isSuccessful();
	}
	
	/*
	 * Level
	 */
	
	public void addLevel(final int level){
		this.offer(Keys.EXPERIENCE_LEVEL, getLevel() + level);	
	}
	
	public boolean setLevel(final int level){
		return this.offer(Keys.EXPERIENCE_LEVEL, level).isSuccessful();	
	}
	
	public int getLevel() {
		return this.get(Keys.EXPERIENCE_LEVEL).orElse(0);
	}
	
	/*
	 * TotalExperience
	 */
	
	public void addExp(final int experience){
		this.offer(Keys.EXPERIENCE_SINCE_LEVEL, getTotalExperience() + experience);
	}
	
	public boolean setExp(final int experience){
		return this.offer(Keys.EXPERIENCE_SINCE_LEVEL, experience).isSuccessful();
	}
	
	public int getExp() {
		return this.get(Keys.EXPERIENCE_SINCE_LEVEL).orElse(0);
	}
	
	/*
	 * TotalExperience
	 */
	
	public void addTotalExperience(final int experience){
		this.offer(Keys.TOTAL_EXPERIENCE, getTotalExperience() + experience);
	}
	
	public boolean setTotalExperience(final int experience){
		return this.offer(Keys.TOTAL_EXPERIENCE, experience).isSuccessful();
	}
	
	public int getTotalExperience() {
		return this.get(Keys.TOTAL_EXPERIENCE).orElse(0);
	}
	
	/*
	 * GameMode
	 */
	
	public GameMode getGameMode(){
		return getGameModeData().get(Keys.GAME_MODE).orElse(GameModes.SURVIVAL);
	}
	
	public boolean setGameMode(final GameMode gamemode){
		if(gamemode != null){
			return this.offer((GameModeData)this.getGameModeData().set(Keys.GAME_MODE, gamemode)).isSuccessful();
		}
		return false;
	}
	
	/*
	 * Can Fly
	 */
	public boolean getAllowFlight(){
		return this.get(Keys.CAN_FLY).orElse(false);
	}
	
	public boolean setAllowFlight(final boolean canfly){
		return this.offer(Keys.CAN_FLY, canfly).isSuccessful();
	}
	
	/*
	 * Flying
	 */
	public boolean isFlying(){
		return get(Keys.IS_FLYING).orElse(false);
	}
	
	public boolean setFlying(final boolean isFlying){
		return this.offer(Keys.IS_FLYING, isFlying).isSuccessful();
	}
	
	/*
	 * Sneaking
	 */
	
	public boolean isSneaking() {
        return this.get(Keys.IS_SNEAKING).orElse(false);
    }
	
	public boolean setSneaking(final boolean sneak) {
		return this.offer(Keys.IS_SNEAKING, sneak).isSuccessful();
    }
	
	/*
	 * Sprinting
	 */
	
	public boolean isSprinting() {
		return this.get(Keys.IS_SPRINTING).orElse(false);
    }

    public boolean setSprinting(final boolean sprinting) {
    	return this.offer(Keys.IS_SPRINTING, sprinting).isSuccessful();
    }
    
    /*
     * Sleeping
     */
    
    public boolean isSleeping() {
        return this.get(Keys.IS_SLEEPING).orElse(false);
    }
    
    /*
	 * FlySpeed
	 */
	
	public double getFlySpeed() {
        return this.get(Keys.FLYING_SPEED).orElse(1.0);
    }
	
	public boolean setFlySpeed(final double speed) {
		return this.offer(Keys.FLYING_SPEED, speed).isSuccessful();
    }
	
	/*
	 * WalkSpeed
	 */
	
	public double getWalkSpeed() {
        return this.get(Keys.WALKING_SPEED).orElse(1.0);
    }
	
	public boolean setWalkSpeed(final double speed) {
		return this.offer(Keys.WALKING_SPEED, speed).isSuccessful();
    }
    
	
	/*
	 * StuckArrows
	 */
	
	public int getStuckArrows() {
        return this.get(Keys.STUCK_ARROWS).orElse(0);
    }
	
	public boolean setStuckArrows(final int arrows) {
		return this.offer(Keys.STUCK_ARROWS, arrows).isSuccessful();
    }
	
	/*
	 * PotionEffects
	 */
	
	public List<PotionEffect> getPotionEffects() {
        return this.get(Keys.POTION_EFFECTS).orElse(new ArrayList<PotionEffect>());
    }
	
	public boolean setPotionEffects(final List<PotionEffect> potionEffects) {
		return this.offer(Keys.POTION_EFFECTS, potionEffects).isSuccessful();
    }
	
	/*
	 * Aflame
	 */
	
	public boolean isAflame() {
		return get(Keys.IS_AFLAME).orElse(false);
	}
	
	public boolean setAflame(final boolean aflame) {
		return this.offer(Keys.IS_AFLAME, aflame).isSuccessful();
	}
	
	/*
	 * Fire ticks
	 */
	
	public Integer getFireTicks() {
		return get(Keys.FIRE_TICKS).orElse(0);
	}
	
	public boolean setFireTicks(final int ticks){
		return this.offer(Keys.FIRE_TICKS, ticks).isSuccessful();
	}
	
	/*
	 * Date
	 */
	
	public long getLastDatePlayed() {
		if(get(Keys.LAST_DATE_PLAYED).isPresent()) {
			return get(Keys.LAST_DATE_PLAYED).get().toEpochMilli();
		}
		return System.currentTimeMillis();
	}
	
	public long getFirstDatePlayed() {
		if(get(Keys.FIRST_DATE_PLAYED).isPresent()) {
			return get(Keys.FIRST_DATE_PLAYED).get().toEpochMilli();
		}
		return 0;
	}
	
	/*
	 * Air
	 */
	
	public Integer getRemainingAir() {
		return get(Keys.REMAINING_AIR).orElse(0);
	}
	
	public boolean setRemainingAir(final int air){
		return this.offer(Keys.REMAINING_AIR, air).isSuccessful();
	}
	
	/*
	 * Air Max
	 */
	
	public Integer getMaxAir() {
		return get(Keys.MAX_AIR).orElse(0);
	}
	
	public boolean setMaxAir(final int air){
		return this.offer(Keys.MAX_AIR, air).isSuccessful();
	}
}
