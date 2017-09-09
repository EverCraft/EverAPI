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
package fr.evercraft.everapi.registers;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.critieria.Criterion;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayMode;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.java.UtilsField;
import fr.evercraft.everapi.register.ECatalogType;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.score.*;

public abstract class ScoreType extends ECatalogType {
	
	private final EverAPI plugin;
	protected final CopyOnWriteArrayList<IObjective> objectives;
	
	private final Optional<Criterion> criterion;
	private final ObjectiveDisplayMode display;
	
	public ScoreType(String name, EverAPI plugin) {
		this(name, plugin, null, ObjectiveDisplayModes.INTEGER);
	}
	
	public ScoreType(String name, EverAPI plugin, Criterion criterion, ObjectiveDisplayMode display) {
		super(name);
		this.plugin = plugin;
		this.objectives = new CopyOnWriteArrayList<IObjective>();
		
		this.criterion = Optional.ofNullable(criterion);
		this.display = display;
		
		this.plugin.getGame().getEventManager().registerListeners(this.plugin, this);
	}
	
	public abstract Integer getValue(EPlayer player);
	public abstract boolean isUpdate();
	
	public void addListener(IObjective objective) {
		this.objectives.add(objective);
	}
	
	public void removeListener(IObjective objective) {
		this.objectives.remove(objective);
	}
	
	protected void update(ScoreType type) {
		if(this.objectives.isEmpty()) return;
		
		Sponge.getScheduler().createTaskBuilder().execute(() -> {
			for (IObjective objective : this.objectives) {
				objective.update(type);
			}
		}).submit(this.plugin);
	}
	
	protected void update(UUID uuid, ScoreType type) {
		if(this.objectives.isEmpty()) return;
		
		this.plugin.getEServer().getPlayer(uuid).ifPresent(player -> {
			for (IObjective objective : this.objectives) {
				objective.update(player, type);
			}
		});
	}
	
	public Optional<Criterion> getCriterion() {
		return this.criterion;
	}
	
	public ObjectiveDisplayMode getObjectiveDisplayMode() {
		return this.display;
	}
	
	public static class ScoreTypes {
		public static final ScoreType HEALTH_HEARTS = null;
		public static final ScoreType HEALTH = null;
		public static final ScoreType ONLINE_PLAYERS_CANSEE = null;
		public static final ScoreType BALANCE = null;
		public static final ScoreType PING = null;
		public static final ScoreType FEED = null;
		public static final ScoreType LEVEL = null;
		public static final ScoreType XP = null;
		public static final ScoreType DEATHS = null;
		public static final ScoreType KILLS = null;
		public static final ScoreType RATIO = null;
		public static final ScoreType DEATHS_MONTHLY = null;
		public static final ScoreType KILLS_MONTHLY = null;
		public static final ScoreType RATIO_MONTHLY = null;
		public static final ScoreType HELMET = null;
		public static final ScoreType CHESTPLATE = null;
		public static final ScoreType LEGGINGS = null;
		public static final ScoreType BOOTS = null;
		public static final ScoreType HELMET_MAX = null;
		public static final ScoreType CHESTPLATE_MAX = null;
		public static final ScoreType LEGGINGS_MAX = null;
		public static final ScoreType BOOTS_MAX = null;
		public static final ScoreType HELMET_PERCENT = null;
		public static final ScoreType CHESTPLATE_PERCENT = null;
		public static final ScoreType LEGGINGS_PERCENT = null;
		public static final ScoreType BOOTS_PERCENT = null;
		
		public ScoreTypes(EverAPI plugin) {
			register(new ScoreHealth("HEALTH_HEARTS", plugin, Criteria.HEALTH, ObjectiveDisplayModes.HEARTS));
			register(new ScoreHealth("HEALTH", plugin, Criteria.HEALTH));
			register(new ScoreOnlinePlayersCanSee("ONLINE_PLAYERS_CANSEE", plugin));
			register(new ScoreBalance("BALANCE", plugin));
			register(new ScorePing("PING", plugin));
			register(new ScoreFeed("FEED", plugin));
			register(new ScoreLevel("LEVEL", plugin));
			register(new ScoreXp("XP", plugin));
			register(new ScoreDeath("DEATHS", plugin));
			register(new ScoreKill("KILLS", plugin));
			register(new ScoreRatio("RATIO", plugin));
			register(new ScoreDeathMonthly("DEATHS_MONTHLY", plugin));
			register(new ScoreKillMonthly("KILLS_MONTHLY", plugin));
			register(new ScoreRatioMonthly("RATIO_MONTHLY", plugin));
			register(new ScoreHelmet("HELMET", plugin));
			register(new ScoreChestplate("CHESTPLATE", plugin));
			register(new ScoreLeggings("LEGGINGS", plugin));
			register(new ScoreBoots("BOOTS", plugin));
			register(new ScoreHelmetMax("HELMET_MAX", plugin));
			register(new ScoreChestplateMax("CHESTPLATE_MAX", plugin));
			register(new ScoreLeggingsMax("LEGGINGS_MAX", plugin));
			register(new ScoreBootsMax("BOOTS_MAX", plugin));
			register(new ScoreHelmetPercent("HELMET_PERCENT", plugin));
			register(new ScoreChestplatePercent("CHESTPLATE_PERCENT", plugin));
			register(new ScoreLeggingsPercent("LEGGINGS_PERCENT", plugin));
			register(new ScoreBootsPercent("BOOTS_PERCENT", plugin));
		}

		private void register(ScoreType score) {
			try {
				Field field = ScoreTypes.class.getField(score.getName());
				if (field != null) {
					UtilsField.setFinalStatic(field, score);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
