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

import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import com.google.common.base.Function;

import fr.evercraft.everapi.plugin.EPlugin;

public enum EReplacesServer {
	
	ONLINE_PLAYERS(plugin -> String.valueOf(plugin.getEServer().playerNotVanish())),
	MAX_PLAYERS(plugin -> String.valueOf(plugin.getGame().getServer().getMaxPlayers())),
	SERVER_NAME(plugin -> plugin.getEServer().getName()),
	VERSION(plugin -> plugin.getGame().getPlatform().getMinecraftVersion().getName()),
	DATE(plugin -> plugin.getEverAPI().getManagerUtils().getDate().parseDate()),
	TIME(plugin -> plugin.getEverAPI().getManagerUtils().getDate().parseTime()),
	DATETIME(plugin -> plugin.getEverAPI().getManagerUtils().getDate().parseDateTime()),
	MOTD(plugin -> plugin.getEServer().getMotd()),
	RAM_USED(plugin -> {
		Runtime runtime = Runtime.getRuntime();
		return String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
	}),
	RAM_FREE(plugin -> String.valueOf(Runtime.getRuntime().freeMemory() / (1024 * 1024))),
	RAM_TOTAL(plugin -> String.valueOf(Runtime.getRuntime().totalMemory() / (1024 * 1024))),
	RAM_MAX(plugin -> String.valueOf(Runtime.getRuntime().maxMemory() / (1024 * 1024))),
	CORES(plugin -> String.valueOf(Runtime.getRuntime().availableProcessors())),
	MONEY_SINGULAR(plugin -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getDisplayName().toPlain() : Text.EMPTY;
	}),
	MONEY_PLURAL(plugin -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getPluralDisplayName().toPlain() : Text.EMPTY;
	}),
	MONEY_SYMBOL(plugin -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getSymbol().toPlain() : Text.EMPTY;
	});
	
	private Optional<Function<EPlugin<?>, Object>> fun;
	private Optional<BiFunction<EPlugin<?>, String, Object>> bi;
	
	EReplacesServer(Function<EPlugin<?>, Object> fun) {
		this.fun = Optional.ofNullable(fun);
		this.bi = Optional.empty();
	}
	
	EReplacesServer(BiFunction<EPlugin<?>, String, Object> bi) {
		this.bi = Optional.ofNullable(bi);
		this.fun = Optional.empty();
	}
	
	public String getName() {
		return "<" + this.name() + ">";
	}
	
	public Optional<Function<EPlugin<?>, Object>> getValueFunction() {
		return this.fun;
	}
	
	public Optional<BiFunction<EPlugin<?>, String, Object>> getValueBiFunction() {
		return this.bi;
	}
}
