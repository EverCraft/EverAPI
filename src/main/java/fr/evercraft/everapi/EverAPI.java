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
package fr.evercraft.everapi;

import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.SpongeExecutorService;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.command.sub.EAPlugins;
import fr.evercraft.everapi.command.sub.EAReload;
import fr.evercraft.everapi.command.sub.EAReplace;
import fr.evercraft.everapi.command.sub.EATest;
import fr.evercraft.everapi.config.EFormatSerializer;
import fr.evercraft.everapi.config.EMessageBuilderSerializer;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.register.ManagerRegister;
import fr.evercraft.everapi.server.EServer;
import fr.evercraft.everapi.services.ManagerService;
import fr.evercraft.everapi.services.bungee.BungeeCord;
import fr.evercraft.everapi.sponge.ManagerUtils;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

@Plugin(id = "everapi", 
		name = "EverAPI", 
		version = EverAPI.VERSION, 
		dependencies = {
			@Dependency(id = "spongeapi", version = EverAPI.SPONGEAPI_VERSION)
		},
		description = "EverAPI is a library",
		url = "http://wiki.evercraft.fr/",
		authors = {"rexbut","lesbleu"})
public class EverAPI extends EPlugin<EverAPI> {
	
	public static final String VERSION = "0.1.5";
	public static final String SPONGEAPI_VERSION = "7.0.0-SNAPSHOT";

	private EChat chat;
	
	private EAConfig configs;
	private EAMessage messages;
	
	private EServer server;

	private ManagerService service;
	private ManagerUtils managerUtils;
	
	private SpongeExecutorService thread;
	
	public EverAPI() {
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(EMessageBuilder.class), new EMessageBuilderSerializer(this));
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(EFormat.class), new EFormatSerializer());
	}
	
	@Override
	protected void onPreEnable() throws PluginDisableException, ServerDisableException {		
		this.thread = this.getGame().getScheduler().createAsyncExecutor(this);
		this.chat = new EChat(this);
		this.configs = new EAConfig(this);
		
		this.messages = new EAMessage(this);
		this.server = new EServer(this);
		this.managerUtils = new ManagerUtils(this);
		this.service = new ManagerService(this);
		new ManagerRegister(this);
	}
	
	@Override
	protected void onCompleteEnable() {
		this.getGame().getEventManager().registerListeners(this, new EAListener(this));
		new BungeeCord(this);
		
		EACommand command = new EACommand(this);
		command.add(new EAPlugins(this, command));
		command.add(new EAReload(this, command));
		command.add(new EATest(this, command));
		command.add(new EAReplace(this, command));
	}

	protected void onReload() throws PluginDisableException, ServerDisableException {
		this.reloadConfigurations();
		this.service.reload();
		this.managerUtils.reload();
	}
	
	protected void onDisable() {
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
	
	@Override
	public EChat getChat(){
		return this.chat;
	}
	
	
	public ManagerService getManagerService() {
		return this.service;
	}
	
	public ManagerUtils getManagerUtils() {
		return this.managerUtils;
	}
	
	@Override
	public SpongeExecutorService getThreadAsync() {
		return this.thread;
	}
}
