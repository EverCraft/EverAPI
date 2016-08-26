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

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;

import fr.evercraft.everapi.EverAPI;

public class EUser extends UserStats {
	
	public EUser(EverAPI plugin, User user) {
		super(plugin, user);
	}
	
	/*
	 * DisplayName
	 */
	
	public String getDisplayName() {
		return getDisplayName(getActiveContexts());
	}
	
	public String getDisplayName(Set<Context> contexts) {
		return getPrefix(contexts).orElse("") + this.getName() + getSuffix(contexts).orElse("");
	}
	public Optional<String> getPrefix() {
		return getPrefix(getActiveContexts());
	}
	
	public Optional<String> getPrefix(Set<Context> contexts) {
		return this.getOption(contexts, "prefix");
	}
	
	public Optional<String> getSuffix() {
		return getSuffix(getActiveContexts());
	}
	
	public Optional<String> getSuffix(Set<Context> contexts) {
		return this.getOption(contexts, "suffix");
	}
	
	/*
	 * Health
	 */
	
	/**
	 * Soigner le joueur
	 * @return True si le joueur a bien été soigné
	 */
	public boolean heal(){
		if (this.getHealth() != 0) {
			this.setHealth(this.getMaxHealth());
			this.setFood(20);
			this.setSaturation(20);
			this.setFireTicks(0);
			this.setRemainingAir(this.getMaxAir());
			this.clearPotions();
			return true;
		}
		return false;
	}
	
	public static boolean heal(User user) {
		if (user.get(Keys.HEALTH).orElse(0.) != 0) {
			return true;
		}
		return false;
	}
}
