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

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import fr.evercraft.everapi.plugin.EPlugin;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public abstract class EConfig<T extends EPlugin<T>> extends EFile<T> {
	
	public static final String DEBUG = "DEBUG";
	public static final String LANGUAGE = "LANGUAGE";
	public static final String DISABLE_COMMANDS = "DISABLE-COMMANDS";
	public static final String SQL = "SQL";

    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     */
    public EConfig(final T plugin){
    	this(plugin, "config", true);
    }
    
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     * @param name Le nom du fichier de configuration
     */
    public EConfig(final T plugin, final String name){
    	this(plugin, name, true);
    }
    
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     * @param name Le nom du fichier de configuration
     * @param autoReload Reload automatique
     */
    public EConfig(final T plugin, final String name, final boolean autoReload) {
    	super(plugin, name, autoReload);
    	
    	reload();
    }
    
    /**
     * Charge la configuration
     */
    public void reload(){
    	this.loadFile();
    	this.loadDefault();
    	
    	this.save(false);
    }
    
    public void addDefault(final String paths, final Object value, final String comment){
    	CommentedConfigurationNode node = get(paths);    	
    	if (node.getValue() == null){
    		node.setComment(comment).setValue(value);
    		this.setModified(true);
    	}
    }
    
    public void addDefault(final String paths, final Object value, final String... comments){
    	CommentedConfigurationNode node = get(paths);    	
    	if (node.getValue() == null){
    		node.setComment(String.join("\n", comments)).setValue(value);
    		this.setModified(true);
    	}
    }
    
    public void addComment(final String paths, final String... comments){
    	CommentedConfigurationNode node = get(paths);
    	if (node.getValue() == null){
    		node.setComment(String.join("\n", comments));
    		this.setModified(true);
    	}
    }
    
    public void addDefault(List<String> keys, Object value) {
		keys.stream()
			.filter(key -> this.getContains(key).isVirtual())
			.reduce((k1, k2) -> k1 + ", " + k2)
			.ifPresent(key -> this.addDefault(key, value));
	}
	
	public ConfigurationNode getContains(final String name) {
		for (Entry<Object, ? extends ConfigurationNode> config : this.getNode().getChildrenMap().entrySet()) {
			if (config.getKey().toString().contains(name)) {
				return config.getValue();
			}
		}
		return this.get(name);
	}

    public boolean isDebug() {
    	this.plugin.getELogger().info("Debug : " + this.get("DEBUG").getBoolean(false));
		return this.get("DEBUG").getBoolean(false);
	}

	public String getLanguage() {
		return this.get("LANGUAGE").getString(EMessage.ENGLISH);
	}
	
	/**
     * Définit tous les éléments par défaut
     */
    protected abstract void loadDefault();
    
    public void configDefault() {
		addDefault(DEBUG, false, "Displays plugin performance in the logs");
		addDefault(LANGUAGE, EMessage.FRENCH, "Allows you to choose the language of the messages", "Examples : ", "  FR_fr : French", "  EN_en : English");
		addDefault(DISABLE_COMMANDS, Arrays.asList("command_name"), "List of disabled commands");
    }
    
    public void sqlDefault() {
		addComment(SQL, 				"Save to a database : ",
										"  H2 : \"jdbc:h2:" + this.plugin.getPath().toAbsolutePath() + "/data\"",
										"  SQL : \"jdbc:mysql://[login[:password]@]{host}:{port}/{database}\"",
										"  By default, this is saved in the 'data.mv.db'");
		addDefault(SQL + ".enable", false);
		addDefault(SQL + ".url", "jdbc:mysql://root:password@localhost:3306/minecraft");
		addDefault(SQL + ".prefix", this.plugin.getName() + "_");
    }
}
