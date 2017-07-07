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
package fr.evercraft.everapi.message.replace;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.registers.ScoreType.ScoreTypes;
import fr.evercraft.everapi.server.player.EPlayer;

public enum EReplacesPlayer {
	
	UUID((plugin, player) -> player.getUniqueId().toString()),
	NAME((plugin, player) -> player.getName()),
	DISPLAYNAME((plugin, player) -> player.getDisplayNameHover()),
	
	WORLD_NAME((plugin, player) -> player.getWorld().getName()),
	SHORT_WORLD_NAME((plugin, player) -> String.valueOf(player.getWorld().getName().toUpperCase().charAt(0))),
	HEALTH((plugin, player) -> String.valueOf(player.getHealth())),
	MAX_HEALTH((plugin, player) -> String.valueOf(player.getMaxHealth())),
	FEED((plugin, player) -> String.valueOf(player.getFood())),
	ONLINE_PLAYERS_CANSEE((plugin, player) -> player.getOnlinePlayers().size()),
	IP((plugin, player) -> player.getConnection().getAddress().getAddress().getHostAddress().toString()),
	PING((plugin, player) -> String.valueOf(player.getConnection().getLatency())),
	LAST_DATE_PLAYED((plugin, player) -> plugin.getEverAPI().getManagerUtils().getDate().formatDate(player.getLastDatePlayed())),
	FIRST_DATE_PLAYED((plugin, player) -> plugin.getEverAPI().getManagerUtils().getDate().parseDate(player.getFirstDatePlayed())),
	FIRST_DATE_TIME_PLAYED((plugin, player) -> plugin.getEverAPI().getManagerUtils().getDate().parseDateTime(player.getFirstDatePlayed())),
	GROUP((plugin, player) -> {
		Optional<Subject> group = player.getGroup();
		return group.isPresent() ? group.get().getIdentifier() : "";
	}),
	
	// Stats
	DEATHS((plugin, player) -> String.valueOf(player.getDeath())),
	KILLS((plugin, player) -> String.valueOf(player.getKill())),
	RATIO((plugin, player) -> String.valueOf(player.getRatio())),
	KILLSTREAKS((plugin, player) -> String.valueOf(player.getKillStreaks())),
	DEATHS_MONTHLY((plugin, player) -> String.valueOf(player.getDeathMonthly())),
	KILLS_MONTHLY((plugin, player) -> String.valueOf(player.getKillMonthly())),
	RATIO_MONTHLY((plugin, player) -> String.valueOf(player.getRatioMonthly())),
	
	// Armor
	HELMET((plugin, player) -> ScoreTypes.HELMET.getValue(player).toString()),
	CHESTPLATE((plugin, player) -> ScoreTypes.CHESTPLATE.getValue(player).toString()),
	LEGGINGS((plugin, player) -> ScoreTypes.LEGGINGS.getValue(player).toString()),
	BOOTS((plugin, player) -> ScoreTypes.BOOTS.getValue(player).toString()),
	
	HELMET_MAX((plugin, player) -> ScoreTypes.HELMET_MAX.getValue(player).toString()),
	CHESTPLATE_MAX((plugin, player) -> ScoreTypes.CHESTPLATE_MAX.getValue(player).toString()),
	LEGGINGS_MAX((plugin, player) -> ScoreTypes.LEGGINGS_MAX.getValue(player).toString()),
	BOOTS_MAXLTH((plugin, player) -> ScoreTypes.BOOTS_MAX.getValue(player).toString()),
	
	HELMET_PERCENTAGE((plugin, player) -> ScoreTypes.HELMET_PERCENTAGE.getValue(player).toString()),
	CHESTPLATE_PERCENTAGE((plugin, player) -> ScoreTypes.CHESTPLATE_PERCENTAGE.getValue(player).toString()),
	LEGGINGS_PERCENTAGE((plugin, player) -> ScoreTypes.LEGGINGS_PERCENTAGE.getValue(player).toString()),
	BOOTS_PERCENTAGE((plugin, player) -> ScoreTypes.BOOTS_PERCENTAGE.getValue(player).toString()),
	
	// Team
	TEAM_PREFIX((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getPrefix() : Text.EMPTY;
	}),
	TEAM_SUFFIX((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getSuffix() : Text.EMPTY;
	}),
	TEAM_NAME((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getDisplayName() : Text.EMPTY;
	}),
	
	// Economy
	BALANCE((plugin, player) -> { 
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().format(player.getBalance()) : Text.EMPTY;
	}),
	
	OPTION((plugin, player, option) -> player.getOption(option).orElse("")),
	OPTION_VALUE((plugin, player, option) -> Text.of(player.getOption(option).orElse("")));
	
	private Pattern pattern;
	private Optional<BiFunction<EPlugin<?>, EPlayer, Object>> bi;
	private Optional<TriFunction<EPlugin<?>, EPlayer, String, Object>> tri;
	
	EReplacesPlayer(BiFunction<EPlugin<?>, EPlayer, Object> bi) {
		this.bi= Optional.of(bi);
		this.tri = Optional.empty();
		this.pattern = Pattern.compile("<(?i)" + this.name() + ">");
	}
	
	EReplacesPlayer(TriFunction<EPlugin<?>, EPlayer, String, Object> tri) {
		this.tri= Optional.of(tri);
		this.bi = Optional.empty();
		this.pattern = Pattern.compile("<(?i)" + this.name() + "=(.[^>]*)>");
	}
	
	public String getName() {
		return "<" + this.name() + ">";
	}
	
	public Optional<BiFunction<EPlugin<?>, EPlayer, Object>> getBiFunction() {
		return this.bi;
	}
	
	public Optional<TriFunction<EPlugin<?>, EPlayer, String, Object>> getTriFunction() {
		return this.tri;
	}
	
	public Pattern getPattern() {
		return this.pattern;
	}

	@FunctionalInterface
	public interface TriFunction<A,B,C,R> {

	    R apply(A a, B b, C c);

	    default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
	        Objects.requireNonNull(after);
	        return (A a, B b, C c) -> after.apply(apply(a, b, c));
	    }
	}
}
