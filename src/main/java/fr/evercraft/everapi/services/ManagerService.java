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
import fr.evercraft.everapi.services.economy.TopEconomyService;
import fr.evercraft.everapi.services.essentials.EssentialsService;
import fr.evercraft.everapi.services.essentials.SpawnService;
import fr.evercraft.everapi.services.essentials.WarpService;
import fr.evercraft.everapi.services.mail.MailService;
import fr.evercraft.everapi.services.pagination.EPagination;
import fr.evercraft.everapi.services.priority.EPriorityService;
import fr.evercraft.everapi.services.priority.PriorityService;
import fr.evercraft.everapi.servives.actionbar.ActionBarService;
import fr.evercraft.everapi.servives.actionbar.EActionBarService;
import fr.evercraft.everapi.servives.scoreboard.EScoreBoardService;
import fr.evercraft.everapi.servives.scoreboard.ScoreBoardService;
import fr.evercraft.everapi.servives.title.ETitleService;
import fr.evercraft.everapi.servives.title.TitleService;

public class ManagerService {
	private final EverAPI plugin;

	private final EPagination pagination;
	
	private final EPriorityService priority;
	private final EActionBarService actionbar;
	private final ETitleService title;
	private final EScoreBoardService scoreboard;
	
	public ManagerService(EverAPI plugin){
		this.plugin = plugin;
		
		this.pagination = new EPagination(this.plugin);
		
		this.priority = new EPriorityService(this.plugin);
		this.actionbar = new EActionBarService(this.plugin);
		this.title = new ETitleService(this.plugin);
		this.scoreboard = new EScoreBoardService(this.plugin);
		
		this.register();
	}
	
	public void register() {
		this.plugin.getGame().getServiceManager().setProvider(this.plugin, PriorityService.class, this.priority);
		this.plugin.getGame().getServiceManager().setProvider(this.plugin, ActionBarService.class, this.actionbar);
		this.plugin.getGame().getServiceManager().setProvider(this.plugin, TitleService.class, this.title);
		this.plugin.getGame().getServiceManager().setProvider(this.plugin, ScoreBoardService.class, this.scoreboard);
	}
	
	public void reload() {
		this.pagination.reload();
		
		this.priority.reload();
		this.actionbar.reload();
		this.title.reload();
		this.scoreboard.reload();
	}
	
	/*
	 * Accesseurs service EverAPI
	 */
	public EPagination getEPagination() {
		return this.pagination;
	}
	
	public EScoreBoardService getEScoreBoard() {
		return this.scoreboard;
	}
	
	
	/*
	 * Accesseurs service EverCraft
	 */
	
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
	
	public Optional<PriorityService> getPriority() {
		return this.plugin.getGame().getServiceManager().provide(PriorityService.class);
	}
	
	public Optional<ActionBarService> getActionBar() {
		return this.plugin.getGame().getServiceManager().provide(ActionBarService.class);
	}
	
	public Optional<TitleService> getTitle() {
		return this.plugin.getGame().getServiceManager().provide(TitleService.class);
	}
	
	public Optional<ScoreBoardService> getScoreBoard() {
		return this.plugin.getGame().getServiceManager().provide(ScoreBoardService.class);
	}
	
	public Optional<CooldownService> getCooldown() {
		return this.plugin.getGame().getServiceManager().provide(CooldownService.class);
	}
	
	public Optional<TopEconomyService> getTopEconomy() {
		return this.plugin.getGame().getServiceManager().provide(TopEconomyService.class);
	}
	
	/*
	 * Accesseurs service Sponge
	 */
	
	public Optional<EconomyService> getEconomy() {
		return this.plugin.getGame().getServiceManager().provide(EconomyService.class);
	}
	
	public Optional<PermissionService> getPermission() {
		return this.plugin.getGame().getServiceManager().provide(PermissionService.class);
	}

	public Optional<PaginationService> getPagination() {
		return this.plugin.getGame().getServiceManager().provide(PaginationService.class);
	}
	
	public Optional<UserStorageService> getUserStorage() {
		return this.plugin.getGame().getServiceManager().provide(UserStorageService.class);
	}
}
