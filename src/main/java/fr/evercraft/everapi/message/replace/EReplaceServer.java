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

import org.spongepowered.api.Platform.Component;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import com.google.common.base.Function;

import fr.evercraft.everapi.plugin.EPlugin;

public enum EReplaceServer {
	
	ONLINE_PLAYERS(plugin -> String.valueOf(plugin.getEServer().playerNotVanish())),
	MAX_PLAYERS(plugin -> String.valueOf(plugin.getGame().getServer().getMaxPlayers())),
	SERVER_NAME(plugin -> plugin.getEServer().getName()),
	VERSION(plugin -> plugin.getGame().getPlatform().getContainer(Component.API).getVersion().orElse("")),
	DATE(plugin -> plugin.getEverAPI().getManagerUtils().getDate().parseDate()),
	TIME(plugin -> plugin.getEverAPI().getManagerUtils().getDate().parseTime()),
	DATETIME(plugin -> plugin.getEverAPI().getManagerUtils().getDate().parseDateTime()),
	MONEY_SINGULAR(plugin -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getDisplayName().toPlain() : Text.EMPTY;
	}),
	MONEY_PLURAL(plugin -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getPluralDisplayName().toPlain() : Text.EMPTY;
	}),
	SYMBOL(plugin -> {
		Optional<EconomyService> economy = plugin.getEverAPI().getManagerService().getEconomy();
		return economy.isPresent() ? economy.get().getDefaultCurrency().getSymbol().toPlain() : Text.EMPTY;
	});
	
	private Function<EPlugin<?>, Object> fun;
	
	EReplaceServer(Function<EPlugin<?>, Object> fun) {
		this.fun= fun;
	}
	
	public String getKey() {
		return "<" + this.name() + ">";
	}
	
	public Function<EPlugin<?>, Object> getValue() {
		return this.fun;
	}
}
