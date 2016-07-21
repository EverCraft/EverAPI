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
package fr.evercraft.everapi.plugin;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.SpongeExecutorService;

import com.google.inject.Inject;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EFile;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.server.EServer;

public abstract class EPlugin {

	@Inject
    private Game game;
	@Inject
    private PluginContainer pluginContainer;
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path path;
	
	private EverAPI everapi;
	private boolean enable;	
	private final CopyOnWriteArraySet<EFile> files;
	private ELogger logger;
	
	protected void onPreEnable() throws PluginDisableException, ServerDisableException{}
	protected void onEnable() throws PluginDisableException, ServerDisableException{}
	protected void onPostEnable() throws PluginDisableException, ServerDisableException{}
	protected abstract void onCompleteEnable() throws PluginDisableException, ServerDisableException;
	protected void onStartServer() throws PluginDisableException, ServerDisableException{}
	protected abstract void onReload() throws PluginDisableException, ServerDisableException;
	protected void onStopServer() throws PluginDisableException, ServerDisableException{}
	protected abstract void onDisable() throws PluginDisableException, ServerDisableException;

	public abstract EConfig getConfigs();
	public abstract EMessage getMessages();
	
	public EPlugin(){
		this.enable = true;
		this.files = new CopyOnWriteArraySet<EFile>();
	}
	
	@Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
		try {
			this.setupEverAPI();
			if(getEverAPI().isEnable() && this.enable){
				this.getLogger().debug("----------------------- Pre-Enable ----------------------");
				this.onPreEnable();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameInitializationEvent(GameInitializationEvent event) {
		try {
			if(getEverAPI().isEnable() && this.enable){
				this.getLogger().debug("------------------------- Enable ------------------------");
				this.onEnable();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGamePostInitializationEvent(GamePostInitializationEvent event) {
		try {
			if(getEverAPI().isEnable() && this.enable){
				this.getLogger().debug("---------------------- Post-Enable ----------------------");
				this.onPostEnable();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameLoadCompleteEvent(GameLoadCompleteEvent event) {
		try {
			this.setupEverAPI();
			if(getEverAPI().isEnable() && this.enable){
				this.getLogger().debug("-------------------- Complete-Enable --------------------");
				this.onCompleteEnable();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameStartingServerEvent(GameStartingServerEvent event) {
		try {
			if(getEverAPI().isEnable() && this.enable){
				this.getLogger().debug("---------------------- Start-Server ---------------------");
				this.onStartServer();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
	public void onReload(GameReloadEvent event){
		this.reload();
	}
	
	public void reload(){
		try {
			if(getEverAPI().isEnable() && this.enable) {
				this.getLogger().debug("------------------------- Reload ------------------------");
				this.onReload();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameStoppingEvent(GameStoppingServerEvent event) throws ServerDisableException {
		try {
			if(getEverAPI().isEnable() && this.enable){
				this.getLogger().debug("----------------------- Stop-Server ---------------------");
				this.onStopServer();
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameStoppingEvent(GameStoppingEvent event) throws ServerDisableException {
		disable();
	}
	
	public void disable() {
		try {
			if(this.enable){
				this.getLogger().debug("------------------------- Disable -----------------------");
				this.onDisable();
				this.enable = false;
				this.getLogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getLogger().info(e.getMessage());
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Inject
	private void setLogger(Logger logger) {
	    this.logger = new ELogger(logger);
	}
	
	/*
	 * Accesseur
	 */
	
	/**
	 * Retourne Game
	 * @return Game
	 */
	public Game getGame(){
		return this.game;
	}
	
	/**
	 * Retourne Path
	 * @return Path
	 */
	public Path getPath(){
		return this.path;
	}
	
	/**
	 * Retourne Logger
	 * @return Logger
	 */
	public ELogger getLogger(){
		return this.logger;
	}
	
	/**
	 * Retourne l'ID du plugin
	 * @return L'ID du plugin
	 */
	public String getId(){
		return this.pluginContainer.getId();
	}
	
	/**
	 * Retourne le nom du plugin
	 * @return Le nom du plugin
	 */
	public String getName(){
		return this.pluginContainer.getName();
	}
	
	/**
	 * Retourne la version du plugin
	 * @return La version du plugin
	 */
	public Optional<String> getVersion(){
		return this.pluginContainer.getVersion();
	}
	
	/**
	 * Retourne la description du plugin
	 * @return La description du plugin
	 */
	public Optional<String> getDescription(){
		return this.pluginContainer.getDescription();
	}
	
	/**
	 * Retourne la description du plugin
	 * @return La description du plugin
	 */
	public Optional<String> getUrl(){
		return this.pluginContainer.getUrl();
	}
	
	/**
	 * Retourne la description du plugin
	 * @return La description du plugin
	 */
	public List<String> getAuthors(){
		return this.pluginContainer.getAuthors();
	}
	
	/**
	 * Retourne True si le plugin est activé
	 * @return True si le plugin est activé
	 */
	public boolean isEnable(){
		return this.enable;
	}
	
	/*
	 * EverAPI
	 */
	
	/**
	 * Retourne EverAPI
	 * @return EverAPI
	 */
	public EverAPI getEverAPI(){
		return this.everapi;
	}
	
	/**
	 * Initialise le plugin EverAPI
	 * @return True si le plugin EverAPI est activé
	 * @throws ServerDisableException 
	 */
	protected void setupEverAPI() throws ServerDisableException {
		Optional<PluginContainer> plugin = this.game.getPluginManager().getPlugin("fr.evercraft.everapi");
		if (plugin.isPresent()) {
			Optional<?> everapi = plugin.get().getInstance();
			if (everapi.isPresent() && everapi.get() instanceof EverAPI) {
				this.everapi = (EverAPI) everapi.get();
			} else {
				throw new ServerDisableException(this, "Le plugin EverAPI n'est pas activé");
			}
		} else {
			throw new ServerDisableException(this, "Le plugin EverAPI n'est pas sur le serveur");
		}
	}
	
	/*
	 * Ajout de fonctionnalité 
	 */
	/**
	 * Retourne EServer
	 * @return EServer
	 */
	public EServer getEServer(){
		return this.getEverAPI().getEServer();
	}

	public EChat getChat() {
		return this.getEverAPI().getChat();
	}
	
	public SpongeExecutorService getThreadAsync() {
		return this.getEverAPI().getThreadAsync();
	}
	
	/*
	 * Configuration
	 */
	
	/**
	 * Ajoute un fichier de configuration à la liste
	 */
	public void registerConfiguration(final EFile file) {
		this.files.add(file);
	}
	
	/**
	 * Supprime un fichier de configuration à la liste
	 */
	public void removeConfiguration(final EFile file) {
		this.files.remove(file);
	}
	
	/**
	 * Recharge tous les fichiers de configuration du plugin
	 */
	public void reloadConfigurations(){
		for(EFile file : this.files){
			file.reload();
        }
	}
}
