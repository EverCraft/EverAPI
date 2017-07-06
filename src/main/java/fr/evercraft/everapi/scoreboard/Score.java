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
package fr.evercraft.everapi.scoreboard;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.spongepowered.api.Sponge;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public abstract class Score {
	
	private EverAPI plugin;
	protected final CopyOnWriteArrayList<IObjective> objectives;
	
	
	public Score() {
		this.objectives = new CopyOnWriteArrayList<IObjective>();
	}
	
	public void addListener(EPlugin<?> plugin, IObjective objective) {
		if (this.plugin == null) {
			this.plugin = plugin.getEverAPI();
		}
		
		if (this.objectives.isEmpty()) {
			this.plugin.getGame().getEventManager().registerListeners(this.plugin, this);
		}
		this.objectives.add(objective);
	}
	
	public void removeListener(IObjective objective) {
		this.objectives.remove(objective);
		if (this.objectives.isEmpty() && this.plugin != null) {
			this.plugin.getGame().getEventManager().unregisterListeners(this);
		}
	}
	
	protected void update(TypeScores type) {
		Sponge.getScheduler().createTaskBuilder().execute(() -> {
			for (IObjective objective : this.objectives) {
				objective.update(type);
			}
		}).submit(this.plugin);
	}
	
	protected void update(UUID uuid, TypeScores type) {
		this.plugin.getEServer().getPlayer(uuid).ifPresent(player -> {
			for (IObjective objective : this.objectives) {
				objective.update(player, type);
			}
		});
	}
	
	public abstract Integer getValue(EPlayer player);
	
	public abstract boolean isUpdate();
}
