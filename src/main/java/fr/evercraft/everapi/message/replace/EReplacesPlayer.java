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

import java.util.Optional;
import java.util.function.BiFunction;

import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.scoreboard.TypeScores;
import fr.evercraft.everapi.server.player.EPlayer;

public enum EReplacesPlayer {
	
	UUID((plugin, player) -> player.getUniqueId().toString()),
	NAME((plugin, player) -> player.getName()),
	DISPLAYNAME((plugin, player) -> player.getDisplayName()),
	WORLD_NAME((plugin, player) -> player.getWorld().getName()),
	SHORT_WORLD_NAME((plugin, player) -> String.valueOf(player.getWorld().getName().toUpperCase().charAt(0))),
	HEALTH((plugin, player) -> String.valueOf(player.getHealth())),
	MAX_HEALTH((plugin, player) -> String.valueOf(player.getMaxHealth())),
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
	TEAM_PREFIX((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getPrefix().toPlain() : "";
	}),
	TEAM_SUFFIX((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getSuffix().toPlain() : "";
	}),
	TEAM_NAME((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getDisplayName().toPlain() : "";
	}),
	BALANCE((plugin, player) -> player.getBalanceRound().toString()),
	DEATHS((plugin, player) -> String.valueOf(player.getDeath())),
	KILLS((plugin, player) -> String.valueOf(player.getKill())),
	RATIO((plugin, player) -> String.valueOf(player.getRatio())),
	KILLSTREAKS((plugin, player) -> String.valueOf(player.getKillStreaks())),
	DEATHS_MONTHLY((plugin, player) -> String.valueOf(player.getDeathMonthly())),
	KILLS_MONTHLY((plugin, player) -> String.valueOf(player.getKillMonthly())),
	RATIO_MONTHLY((plugin, player) -> String.valueOf(player.getRatioMonthly())),
	
	HELMET((plugin, player) -> TypeScores.HELMET.getValue(player).toString()),
	CHESTPLATE((plugin, player) -> TypeScores.CHESTPLATE.getValue(player).toString()),
	LEGGINGS((plugin, player) -> TypeScores.LEGGINGS.getValue(player).toString()),
	BOOTS((plugin, player) -> TypeScores.BOOTS.getValue(player).toString()),
	
	HELMET_MAX((plugin, player) -> TypeScores.HELMET_MAX.getValue(player).toString()),
	CHESTPLATE_MAX((plugin, player) -> TypeScores.CHESTPLATE_MAX.getValue(player).toString()),
	LEGGINGS_MAX((plugin, player) -> TypeScores.LEGGINGS_MAX.getValue(player).toString()),
	HEABOOTS_MAXLTH((plugin, player) -> TypeScores.BOOTS_MAX.getValue(player).toString()),
	
	HELMET_POURCENTAGE((plugin, player) -> TypeScores.HELMET_POURCENTAGE.getValue(player).toString()),
	CHESTPLATE_POURCENTAGE((plugin, player) -> TypeScores.CHESTPLATE_POURCENTAGE.getValue(player).toString()),
	LEGGINGS_POURCENTAGE((plugin, player) -> TypeScores.LEGGINGS_POURCENTAGE.getValue(player).toString()),
	BOOTS_POURCENTAGE((plugin, player) -> TypeScores.BOOTS_POURCENTAGE.getValue(player).toString()),
	
	DISPLAYNAME_FORMAT((plugin, player) -> player.getDisplayNameHover()),
	TEAM_PREFIX_FORMAT((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getPrefix() : Text.EMPTY;
	}),
	TEAM_SUFFIX_FORMAT((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getSuffix() : Text.EMPTY;
	}),
	TEAM_NAME_FORMAT((plugin, player) -> {
		Optional<Team> team = player.getTeam();
		return team.isPresent() ? team.get().getDisplayName() : Text.EMPTY;
	}),
	BALANCE_FORMAT((plugin, player) -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().format(player.getBalance()) : Text.EMPTY;
	}),
	MONEY_SINGULAR_FORMAT((plugin, player) -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getSymbol() : Text.EMPTY;
	}),
	MONEY_PLURAL_FORMAT((plugin, player) -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getDisplayName() : Text.EMPTY;
	}),
	SYMBOL_FORMAT((plugin, player) -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getPluralDisplayName() : Text.EMPTY;
	}),;
	
	private BiFunction<EPlugin<?>, EPlayer, Object> fun;
	
	EReplacesPlayer(BiFunction<EPlugin<?>, EPlayer, Object> fun) {
		this.fun= fun;
	}
	
	public String getName() {
		return "<" + this.name() + ">";
	}
	
	public BiFunction<EPlugin<?>, EPlayer, Object> getValue() {
		return this.fun;
	}
}
