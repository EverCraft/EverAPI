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

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EFile;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.server.EServer;
import fr.evercraft.everapi.stats.Metrics;

public abstract class EPlugin<T extends EPlugin<T>> {

	@Inject
    private Game game;
	@Inject
    private PluginContainer pluginContainer;
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path path;
	
	@Inject
    private Metrics metrics;
	
	private EverAPI everapi;
	private boolean enable;	
	private final CopyOnWriteArraySet<EFile<T>> files;
	private ELogger logger;
	
	protected abstract void onPreEnable() throws PluginDisableException, ServerDisableException;
	protected void onEnable() throws PluginDisableException, ServerDisableException{}
	protected void onPostEnable() throws PluginDisableException, ServerDisableException{}
	protected abstract void onCompleteEnable() throws PluginDisableException, ServerDisableException;
	protected void onStartServer() throws PluginDisableException, ServerDisableException{}
	protected abstract void onReload() throws PluginDisableException, ServerDisableException;
	protected void onStopServer() throws PluginDisableException, ServerDisableException{}
	protected abstract void onDisable() throws PluginDisableException, ServerDisableException;

	public abstract EConfig<T> getConfigs();
	public abstract EMessage<T> getMessages();
	
	public EPlugin(){
		this.enable = true;
		this.files = new CopyOnWriteArraySet<EFile<T>>();
	}
	
	@Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
		try {
			this.setupEverAPI();
			if (this.getEverAPI().isEnable() && this.enable) {
				this.onPreEnable();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameInitializationEvent(GameInitializationEvent event) {
		try {
			if (this.getEverAPI().isEnable() && this.enable){
				this.getELogger().debug("------------------------- Enable ------------------------");
				this.onEnable();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGamePostInitializationEvent(GamePostInitializationEvent event) {
		try {
			if (this.getEverAPI().isEnable() && this.enable){
				this.getELogger().debug("---------------------- Post-Enable ----------------------");
				this.onPostEnable();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameLoadCompleteEvent(GameLoadCompleteEvent event) {
		try {
			this.setupEverAPI();
			if (this.getEverAPI().isEnable() && this.enable){
				this.getELogger().debug("-------------------- Complete-Enable --------------------");
				this.onCompleteEnable();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameStartingServerEvent(GameStartingServerEvent event) {
		try {
			if (this.getEverAPI().isEnable() && this.enable){
				this.getELogger().debug("---------------------- Start-Server ---------------------");
				this.onStartServer();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
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
			if (this.getEverAPI().isEnable() && this.enable) {
				this.getELogger().debug("------------------------- Reload ------------------------");
				this.onReload();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
			this.disable();
		} catch (ServerDisableException e) {
			e.execute();
		}
	}
	
	@Listener
    public void onGameStoppingEvent(GameStoppingServerEvent event) throws ServerDisableException {
		try {
			if (this.getEverAPI().isEnable() && this.enable){
				this.getELogger().debug("----------------------- Stop-Server ---------------------");
				this.onStopServer();
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
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
			if (this.enable){
				this.getELogger().debug("------------------------- Disable -----------------------");
				this.onDisable();
				this.enable = false;
				this.getELogger().debug("---------------------------------------------------------");
			}
		} catch (PluginDisableException e) {
			this.getELogger().info(e.getMessage());
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
	public ELogger getELogger(){
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
		Optional<PluginContainer> plugin = this.game.getPluginManager().getPlugin("everapi");
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
	
	public SpongeExecutorService getThreadSync() {
		return this.getEverAPI().getThreadSync();
	}
	
	public Metrics getMetrics() {
		return this.metrics;
	}
	
	/*
	 * Configuration
	 */
	
	/**
	 * Ajoute un fichier de configuration à la liste
	 */
	public void registerConfiguration(final EFile<T> file) {
		this.files.add(file);
	}
	
	/**
	 * Supprime un fichier de configuration à la liste
	 */
	public void removeConfiguration(final EFile<T> file) {
		this.files.remove(file);
	}
	
	/**
	 * Recharge tous les fichiers de configuration du plugin
	 */
	public void reloadConfigurations(){
		for (EFile<T> file : this.files){
			file.reload();
        }
	}
	
	public PluginContainer getPluginContainer() {
        Optional<PluginContainer> optPlugin = this.getGame().getPluginManager().fromInstance(this);
        Preconditions.checkArgument(optPlugin.isPresent(), "Provided object is not a plugin instance");
        return optPlugin.get();
    }
}
