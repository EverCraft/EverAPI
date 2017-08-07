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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.item.inventory.ItemStack;

import fr.evercraft.everapi.EverAPI;

public class UserKeys extends UserSponge {

	public UserKeys(final EverAPI plugin, final User user){
		super(plugin, user);
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
		return this.user.get(Keys.HEALTH).orElse(0.0);
	}
	
	public boolean setHealth(final double health){
		return this.user.offer(Keys.HEALTH, health).isSuccessful();
	}
	
	/*
	 * HealthScale
	 */
	
	public double getHealthScale(){
		return this.user.get(Keys.HEALTH_SCALE).orElse(0.0);
	}
	
	public boolean setHealthScale(final double health){
		return this.user.offer(Keys.HEALTH_SCALE, health).isSuccessful();
	}
	
	/*
	 * Max Health
	 */
	
	public double getMaxHealth(){
		return this.user.get(Keys.MAX_HEALTH).orElse(1.0);
	}
	
	public boolean setMaxHealth(final double max_health){
		return this.user.offer(Keys.MAX_HEALTH, max_health).isSuccessful();
	}
	
	public boolean resetMaxHealth(){
		return setMaxHealth(MAX_HEATH);
	}
	
	/*
	 * Food
	 */
	
	public int getFood(){
		return this.user.get(Keys.FOOD_LEVEL).orElse(0);
	}
	
	public boolean setFood(final int food){
		return this.user.offer(Keys.FOOD_LEVEL, food).isSuccessful();
	}
	
	/*
	 * Saturation
	 */
	
	public double getSaturation(){
		return this.user.get(Keys.SATURATION).orElse(0.0);
	}
	
	public boolean setSaturation(final double saturation){
		return this.user.offer(Keys.SATURATION, saturation).isSuccessful();
	}
	
	/*
	 * Exhaustion
	 */
	
	public double getExhaustion(){
		return this.user.get(Keys.EXHAUSTION).orElse(0.0);
	}
	
	public boolean setExhaustion(final double Exhaustion){
		return this.user.offer(Keys.EXHAUSTION, Exhaustion).isSuccessful();
	}
	
	/*
	 * Level
	 */
	
	public void addLevel(final int level){
		this.user.offer(Keys.EXPERIENCE_LEVEL, getLevel() + level);	
	}
	
	public boolean setLevel(final int level){
		return this.user.offer(Keys.EXPERIENCE_LEVEL, level).isSuccessful();	
	}
	
	public int getLevel() {
		return this.user.get(Keys.EXPERIENCE_LEVEL).orElse(0);
	}
	
	/*
	 * TotalExperience
	 */
	
	public void addExp(final int experience){
		this.user.offer(Keys.EXPERIENCE_SINCE_LEVEL, getTotalExperience() + experience);
	}
	
	public boolean setExp(final int experience){
		return this.user.offer(Keys.EXPERIENCE_SINCE_LEVEL, experience).isSuccessful();
	}
	
	public int getExp() {
		return this.user.get(Keys.EXPERIENCE_SINCE_LEVEL).orElse(0);
	}
	
	/*
	 * TotalExperience
	 */
	
	public void addTotalExperience(final int experience){
		this.user.offer(Keys.TOTAL_EXPERIENCE, getTotalExperience() + experience);
	}
	
	public boolean setTotalExperience(final int experience){
		return this.user.offer(Keys.TOTAL_EXPERIENCE, experience).isSuccessful();
	}
	
	public int getTotalExperience() {
		return this.user.get(Keys.TOTAL_EXPERIENCE).orElse(0);
	}
	
	/*
	 * GameMode
	 */
	
	public GameMode getGameMode(){
		return this.user.get(Keys.GAME_MODE).orElse(GameModes.SURVIVAL);
	}
	
	public boolean setGameMode(final GameMode gamemode){
		if (gamemode != null){
			return this.user.offer(Keys.GAME_MODE, gamemode).isSuccessful();
		}
		return false;
	}
	
	public boolean isCreative() {
		return this.getGameMode().equals(GameModes.CREATIVE);
	}
	
	public boolean isSurvival() {
		return this.getGameMode().equals(GameModes.SURVIVAL);
	}
	
	public boolean isSpectator() {
		return this.getGameMode().equals(GameModes.SPECTATOR);
	}
	
	/*
	 * Can Fly
	 */
	public boolean getAllowFlight(){
		return this.user.get(Keys.CAN_FLY).orElse(false);
	}
	
	public boolean setAllowFlight(final boolean canfly){
		return this.user.offer(Keys.CAN_FLY, canfly).isSuccessful();
	}
	
	/*
	 * Flying
	 */
	public boolean isFlying(){
		return this.user.get(Keys.IS_FLYING).orElse(false);
	}
	
	public boolean setFlying(final boolean isFlying){
		return this.user.offer(Keys.IS_FLYING, isFlying).isSuccessful();
	}
	
	/*
	 * Sneaking
	 */
	
	public boolean isSneaking() {
        return this.user.get(Keys.IS_SNEAKING).orElse(false);
    }
	
	public boolean setSneaking(final boolean sneak) {
		return this.user.offer(Keys.IS_SNEAKING, sneak).isSuccessful();
    }
	
	/*
	 * Sprinting
	 */
	
	public boolean isSprinting() {
		return this.user.get(Keys.IS_SPRINTING).orElse(false);
    }

    public boolean setSprinting(final boolean sprinting) {
    	return this.user.offer(Keys.IS_SPRINTING, sprinting).isSuccessful();
    }
    
    /*
     * Sleeping
     */
    
    public boolean isSleeping() {
        return this.user.get(Keys.IS_SLEEPING).orElse(false);
    }
    
    /*
	 * FlySpeed
	 */
	
	public double getFlySpeed() {
        return this.user.get(Keys.FLYING_SPEED).orElse(1.0);
    }
	
	public boolean setFlySpeed(final double speed) {
		return this.user.offer(Keys.FLYING_SPEED, speed).isSuccessful();
    }
	
	/*
	 * WalkSpeed
	 */
	
	public double getWalkSpeed() {
        return this.user.get(Keys.WALKING_SPEED).orElse(1.0);
    }
	
	public boolean setWalkSpeed(final double speed) {
		return this.user.offer(Keys.WALKING_SPEED, speed).isSuccessful();
    }
    
	
	/*
	 * StuckArrows
	 */
	
	public int getStuckArrows() {
        return this.user.get(Keys.STUCK_ARROWS).orElse(0);
    }
	
	public boolean setStuckArrows(final int arrows) {
		return this.user.offer(Keys.STUCK_ARROWS, arrows).isSuccessful();
    }
	
	/*
	 * PotionEffects
	 */
	
	public List<PotionEffect> getPotionEffects() {
        return this.user.get(Keys.POTION_EFFECTS).orElse(new ArrayList<PotionEffect>());
    }
	
	public boolean setPotionEffects(final List<PotionEffect> potionEffects) {
		return this.user.offer(Keys.POTION_EFFECTS, potionEffects).isSuccessful();
    }
	
	public boolean clearPotions(){
		this.user.offer(Keys.POTION_EFFECTS, Arrays.asList());
		return true;
	}
	
	public boolean removePotion(PotionEffect effect){
		if (this.user.get(Keys.POTION_EFFECTS).isPresent()){
			List<PotionEffect> effects = this.user.get(Keys.POTION_EFFECTS).get();
			boolean check = false;
			int cpt = 0;
			while(effects.size() > cpt && check == false){
				if (effects.get(cpt).equals(effect)){
					effects.remove(cpt);
					check = true;
				}
				cpt++;
			}
			this.user.offer(Keys.POTION_EFFECTS, effects);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addPotion(PotionEffect potion){
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		if (this.user.get(Keys.POTION_EFFECTS).isPresent()){
			effects = this.user.get(Keys.POTION_EFFECTS).get();
		}
		effects.add(potion);
		this.user.offer(Keys.POTION_EFFECTS, effects);
		return true;
	}
	
	/*
	 * Aflame
	 */
	
	public boolean isAflame() {
		return this.user.get(Keys.IS_AFLAME).orElse(false);
	}
	
	public boolean setAflame(final boolean aflame) {
		return this.user.offer(Keys.IS_AFLAME, aflame).isSuccessful();
	}
	
	/*
	 * Fire ticks
	 */
	
	public Integer getFireTicks() {
		return this.user.get(Keys.FIRE_TICKS).orElse(0);
	}
	
	public boolean setFireTicks(final int ticks){
		return this.user.offer(Keys.FIRE_TICKS, ticks).isSuccessful();
	}
	
	/*
	 * Date
	 */
	
	public long getLastDatePlayed() {
		if (this.user.get(Keys.LAST_DATE_PLAYED).isPresent()) {
			return this.user.get(Keys.LAST_DATE_PLAYED).get().toEpochMilli();
		}
		return System.currentTimeMillis();
	}
	
	public long getFirstDatePlayed() {
		if (this.user.get(Keys.FIRST_DATE_PLAYED).isPresent()) {
			return this.user.get(Keys.FIRST_DATE_PLAYED).get().toEpochMilli();
		}
		return 0;
	}
	
	/*
	 * Air
	 */
	
	public Integer getRemainingAir() {
		return this.user.get(Keys.REMAINING_AIR).orElse(0);
	}
	
	public boolean setRemainingAir(final int air){
		return this.user.offer(Keys.REMAINING_AIR, air).isSuccessful();
	}
	
	/*
	 * Air Max
	 */
	
	public Integer getMaxAir() {
		return this.user.get(Keys.MAX_AIR).orElse(0);
	}
	
	public boolean setMaxAir(final int air){
		return this.user.offer(Keys.MAX_AIR, air).isSuccessful();
	}
	
	/*
	 * Hand
	 */
	
	public Optional<ItemStack> getItemInMainHand() {
		return this.user.getItemInHand(HandTypes.MAIN_HAND);
	}
	
	public Optional<ItemStack> getItemInSecondaryHand() {
		return this.user.getItemInHand(HandTypes.OFF_HAND);
	}
	
	public void setItemInMainHand(ItemStack itemstak) {
		this.user.setItemInHand(HandTypes.MAIN_HAND, itemstak);
	}
	
	public void setItemInSecondaryHand(ItemStack itemstak) {
		this.user.setItemInHand(HandTypes.OFF_HAND, itemstak);
	}
}
