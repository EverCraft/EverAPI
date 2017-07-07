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
package fr.evercraft.everapi.services.score;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.registers.ScoreType;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.sponge.UtilsItemStack;

public class ScoreHelmetPercentage extends ScoreType {
	private final int DEFAULT = 0;

	public ScoreHelmetPercentage(String name, EverAPI plugin) {
		super(name, plugin);
	}
	
	@Override
	public Integer getValue(EPlayer player) {
		if (player.getHelmet().isPresent()) {
			return (player.getHelmet().get().get(Keys.ITEM_DURABILITY).orElse(DEFAULT)/UtilsItemStack.getMaxDurability(player.getHelmet().get())) * 100;
		}
		return DEFAULT;
	}
	
	@Listener
    public void event(ChangeEntityEquipmentEvent.TargetPlayer event) {
		this.update(event.getTargetEntity().getUniqueId(), ScoreTypes.HELMET_PERCENTAGE);
	}
	
	@Listener
    public void event(DamageEntityEvent event) {
		if (event.getTargetEntity() instanceof Player) {
			this.update(event.getTargetEntity().getUniqueId(), ScoreTypes.HELMET_PERCENTAGE);
		}
	}
	
	@Override
	public boolean isUpdate() {
		return true;
	}
}