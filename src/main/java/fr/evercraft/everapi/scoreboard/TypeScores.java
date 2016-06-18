/**
 * This file is part of EverInformations.
 *
 * EverInformations is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverInformations is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverInformations.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.scoreboard;

import java.util.Optional;

import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.critieria.Criterion;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayMode;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public enum TypeScores {
	HEALTH(new ScoreHealth(), Criteria.HEALTH, ObjectiveDisplayModes.HEARTS),
	HEALTH_INTEGER(new ScoreHealth(), Criteria.HEALTH),
	ONLINE_PLAYERS(new ScoreOnlinePlayers()),
	BALANCE(new ScoreBalance()),
	PING(new ScorePing()),
	FOOD(new ScoreFood()),
	LEVEL(new ScoreLevel()),
	XP(new ScoreXp()),
	DEATHS(new ScoreDeath()),
	KILLS(new ScoreKill()),
	RATIO(new ScoreRatio()),
	DEATHS_MONTHLY(new ScoreDeathMonthly()),
	KILLS_MONTHLY(new ScoreKillMonthly()),
	RATIO_MONTHLY(new ScoreRatioMonthly()),
	HELMET(new ScoreHelmet()),
	CHESTPLATE(new ScoreChestplate()),
	LEGGINGS(new ScoreLeggings()),
	BOOTS(new ScoreBoots()),
	HELMET_MAX(new ScoreHelmetMax()),
	CHESTPLATE_MAX(new ScoreChestplateMax()),
	LEGGINGS_MAX(new ScoreLeggingsMax()),
	BOOTS_MAX(new ScoreBootsMax()),
	HELMET_POURCENTAGE(new ScoreHelmetPourcentage()),
	CHESTPLATE_POURCENTAGE(new ScoreChestplatePourcentage()),
	LEGGINGS_POURCENTAGE(new ScoreLeggingsPourcentage()),
	BOOTS_POURCENTAGE(new ScoreBootsPourcentage());
	
	private final Score score;
	private final Optional<Criterion> criterion;
	private final ObjectiveDisplayMode display;
	
	private TypeScores(Score score) {
		this.score = score;
		this.criterion = Optional.empty();
		this.display = ObjectiveDisplayModes.INTEGER;
	}
	
	private TypeScores(Score score, Criterion criterion) {
		this.score = score;
		this.criterion = Optional.ofNullable(criterion);
		this.display = ObjectiveDisplayModes.INTEGER;
	}
	
	private TypeScores(Score score, Criterion criterion, ObjectiveDisplayMode display) {
		this.score = score;
		this.criterion = Optional.ofNullable(criterion);
		this.display = display;
	}
	
	public Integer getValue(EPlayer player) {
		return this.score.getValue(player);
	}
	
	public boolean isUpdate() {
		return this.score.isUpdate();
	}
	
	public void addListener(EPlugin plugin, IObjective objective) {
		this.score.addListener(plugin, objective);
	}
	
	public void removeListener(EPlugin plugin, IObjective objective) {
		this.score.removeListener(plugin, objective);
	}
	
	public Optional<Criterion> getCriterion() {
		return this.criterion;
	}
	
	public ObjectiveDisplayMode getObjectiveDisplayMode() {
		return this.display;
	}
}