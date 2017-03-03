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
import java.util.List;
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
    
    private boolean modified;

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
    public EFile(final T plugin, final String name, final boolean save){
    	this.plugin = plugin;
    	this.name = name;
    	this.modified = false;
    	
    	if (save) {
    		this.plugin.registerConfiguration(this);    
    	}
    }
    
    /**
     * Charge la configuration
     */
    public abstract void reload(); 
	
    /**
     * Rechargement de la configuration
     */
    public void loadFile() {
        if (this.file == null) {
        	this.file = this.plugin.getPath().resolve(this.name + ".conf").toFile();
        	if (!this.file.getParentFile().exists()){
        		this.file.getParentFile().mkdirs();
        	}
        }
        this.manager = HoconConfigurationLoader.builder().setFile(this.file).build();
        try {
			this.config = this.manager.load();
			this.plugin.getLogger().info("Chargement du fichier : " + this.name + ".conf");
		} catch (IOException e) {
			this.file.renameTo(this.plugin.getPath().resolve(this.name + ".error").toFile());
			this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());
			this.plugin.getLogger().warn("Impossible de charger le fichier : " + this.name + ".conf : " + this.file.getAbsolutePath());
		}
        
    }
    
    /**
     * Sauvegarde du fichier de configuration
     */
    public void save(boolean force) {
        if (this.manager != null && this.config != null && (force || this.modified)) {
	        try {
	        	this.manager.save(config);
	        	this.modified = false;
	        } catch (IOException ex) {
	            this.plugin.getLogger().warn("Impossible de sauvegarder le fichier : " + this.name + ".conf : " + this.file.getAbsolutePath());
	        }
        }
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
    	ConfigurationNode node = get(paths);    	
    	if (node.getValue() == null){
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
