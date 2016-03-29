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
package fr.evercraft.everapi;

import org.spongepowered.api.plugin.Plugin;

import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.plugin.EPermission;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.EServer;
import fr.evercraft.everapi.services.ManagerService;
import fr.evercraft.everapi.services.bungee.BungeeCord;
import fr.evercraft.everapi.sponge.ManagerUtils;
import fr.evercraft.everapi.sponge.UtilsChat;

@Plugin(id = "fr.evercraft.everapi", 
		name = "EverAPI", 
		version = "1.0", 
		description = "L'API d'EverCraft",
		url = "http://evercraft.fr/",
		authors = {"rexbut","lesbleu"})
public class EverAPI extends EPlugin {

	private UtilsChat utilsChat;
	
	private EAConfig configs;
	private EAMessage messages;
	private EAPermission permissions;
	
	private EServer server;

	private ManagerService service;
	private ManagerUtils managerUtils;
	
	@Override
	protected void onPreEnable() throws PluginDisableException, ServerDisableException {	
		this.utilsChat = new UtilsChat(this);
		this.permissions = new EAPermission(this);
		this.configs = new EAConfig(this);
		
		this.messages = new EAMessage(this);
		this.server = new EServer(this);
		this.managerUtils = new ManagerUtils(this);
		this.service = new ManagerService(this);
	}
	
	@Override
	protected void onPostEnable() throws PluginDisableException {
	}
	
	@Override
	protected void onCompleteEnable() {
		this.getGame().getEventManager().registerListeners(this, new EAListener(this));
		new EACommand(this);
		new BungeeCord(this);
	}

	protected void onReload() throws PluginDisableException, ServerDisableException {
		this.reloadConfigurations();
		this.service.reload();
		this.managerUtils.reload();
	}
	
	protected void onDisable() {
	}

	/*
	 * Accesseurs
	 */
	public EPermission getPermissions() {
		return this.permissions;
	}
	
	public EAMessage getMessages(){
		return this.messages;
	}
	
	public EAConfig getConfigs() {
		return this.configs;
	}	
	
	@Override
	public EServer getEServer() {
		return this.server;
	}
	
	public UtilsChat getUtilsChat(){
		return this.utilsChat;
	}
	
	
	public ManagerService getManagerService() {
		return this.service;
	}
	
	public ManagerUtils getManagerUtils() {
		return this.managerUtils;
	}
}
