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
package fr.evercraft.everapi.plugin.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.plugin.EPlugin;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class EFile<T extends EPlugin<T>> {
	protected final T plugin;
	
	protected final String name;
	
	private File file = null;
	private HoconConfigurationLoader manager = null;
	private CommentedConfigurationNode config = null;
	
	// MultiThreading
 	private final ReadWriteLock lock;
 	protected final Lock write_lock;
 	protected final Lock read_lock;
	
	private boolean modified;
	private boolean newDirs;

	/**
	 * Création d'un fichier de configuration
	 * @param plugin Le plugin 
	 */
	public EFile(final T plugin){
		this(plugin, "config", true);
	}
	
	/**
	 * Création d'un fichier de configuration
	 * @param plugin Le plugin 
	 * @param name Le nom du fichier de configuration
	 */
	public EFile(final T plugin, final String name){
		this(plugin, name, true);
	}
	
	/**
	 * Création d'un fichier de configuration
	 * @param plugin Le plugin 
	 * @param name Le nom du fichier de configuration
	 * @param save Sauvegarde automatique
	 */
	public EFile(final T plugin, final String name, final boolean autoReload){
		this.plugin = plugin;
		this.name = name;
		this.modified = false;
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
		
		if (autoReload) {
			this.plugin.registerConfiguration(this);	
		}
	}
	
	/**
	 * Charge la configuration
	 */
	public abstract void reload(); 
	
	public boolean isNewDirs() {
		return this.newDirs;
	}
	
	public List<String> getHeader() {
		return Arrays.asList();
	}
	
	/**
	 * Rechargement de la configuration
	 */
	public void loadFile() {
		this.read_lock.lock();
		try {
			this.newDirs = false;
			if (this.file == null) {
				this.file = this.plugin.getPath().resolve(this.name + ".conf").toFile();
				if (!this.file.getParentFile().exists()){
					this.file.getParentFile().mkdirs();
					this.newDirs = true;
				}
			}
			this.manager = HoconConfigurationLoader.builder().setFile(this.file).build();
			String header = String.join("\n", this.getHeader());
			try {
				if (!header.isEmpty()) {
					this.config = this.manager.load(ConfigurationOptions.defaults().setHeader(header));
				} else {
					this.config = this.manager.load();
				}
				this.plugin.getELogger().info("Chargement du fichier : " + this.name + ".conf");
			} catch (IOException e) {
				this.file.renameTo(this.plugin.getPath().resolve(this.name + ".error").toFile());
				if (!header.isEmpty()) {
					this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults().setHeader(header));
				} else {
					this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());
				}
				this.plugin.getELogger().warn("Impossible de charger le fichier : " + this.name + ".conf : " + this.file.getAbsolutePath());
			}
		} finally {
			this.read_lock.unlock();
		}
	}

	/**
	 * Sauvegarde du fichier de configuration
	 */
	public boolean save(boolean force) {
		this.write_lock.lock();
		try {
			if (this.manager != null && this.config != null && (force || this.modified)) {
				try {
					this.manager.save(config);
					this.modified = false;
				} catch (IOException e) {
					this.plugin.getELogger().warn("Impossible de sauvegarder le fichier : " + this.name + ".conf : " + this.file.getAbsolutePath());
					return false;
				}
			}
		} finally {
			this.write_lock.unlock();
		}
		return true;
	}
	
	/**
	 * Retourne la configuration
	 * @return La configuration
	 */
	public CommentedConfigurationNode getNode() {
		return this.config;
	}
	
	protected void setModified(boolean modified) {
		this.modified = true;
	}

	/**
	 * Ajoute une valeur par défaut
	 * @param paths Le path
	 * @param value La valeur
	 */
	protected void addDefault(final String paths, final Object value){
		ConfigurationNode node = this.get(paths);		
		if (node.isVirtual()){
			node.setValue(value);
			this.setModified(true);
		}
	}
	
	/**
	 * Retourne un node
	 * @param paths Le path
	 * @return Le node
	 */
	public CommentedConfigurationNode get(final String paths){
		return this.config.getNode((Object[]) paths.split(Pattern.quote(".")));   
	}
	
	public List<String> getListString(String paths){
		ConfigurationNode resultat = get(paths);
		if (resultat != null){
			try {
				return resultat.getList(TypeToken.of(String.class));
			} catch (ObjectMappingException e) {}
		}
		return new ArrayList<String>();
	}
}
