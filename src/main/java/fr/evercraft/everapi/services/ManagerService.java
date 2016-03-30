/**
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
package fr.evercraft.everapi.services;

import java.util.Optional;

import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.user.UserStorageService;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.chat.ChatService;
import fr.evercraft.everapi.services.cooldown.CooldownService;
import fr.evercraft.everapi.services.essentials.EssentialsService;
import fr.evercraft.everapi.services.essentials.SpawnService;
import fr.evercraft.everapi.services.essentials.WarpService;
import fr.evercraft.everapi.services.mail.MailService;
import fr.evercraft.everapi.services.pagination.EPagination;

public class ManagerService {
	private final EverAPI plugin;

	private final EPagination pagination;
	
	public ManagerService(EverAPI plugin){
		this.plugin = plugin;
		
		this.pagination = new EPagination(this.plugin);
	}
	
	public void reload() {
		this.pagination.reload();
	}
	
	/*
	 * Accesseurs service EverAPI
	 */
	public EPagination getEPagination() {
		return this.pagination;
	}
	
	
	/*
	 * Accesseurs service EverCraft
	 */
	
	public Optional<EconomyService> getEconomy() {
		return this.plugin.getGame().getServiceManager().provide(EconomyService.class);
	}
	
	public Optional<PermissionService> getPermission() {
		return this.plugin.getGame().getServiceManager().provide(PermissionService.class);
	}
	
	public Optional<EssentialsService> getEssentials() {
		return this.plugin.getGame().getServiceManager().provide(EssentialsService.class);
	}
	
	public Optional<WarpService> getWarp() {
		return this.plugin.getGame().getServiceManager().provide(WarpService.class);
	}
	
	public Optional<SpawnService> getSpawn() {
		return this.plugin.getGame().getServiceManager().provide(SpawnService.class);
	}
	
	public Optional<MailService> getMail() {
		return this.plugin.getGame().getServiceManager().provide(MailService.class);
	}
	
	public Optional<ChatService> getChat() {
		return this.plugin.getGame().getServiceManager().provide(ChatService.class);
	}
	
	/*
	 * Accesseurs service Sponge
	 */

	public Optional<PaginationService> getPagination() {
		return this.plugin.getGame().getServiceManager().provide(PaginationService.class);
	}
	
	public Optional<UserStorageService> getUserStorage() {
		return this.plugin.getGame().getServiceManager().provide(UserStorageService.class);
	}
	
	public Optional<CooldownService> getCooldown() {
		return this.plugin.getGame().getServiceManager().provide(CooldownService.class);
	}
}
