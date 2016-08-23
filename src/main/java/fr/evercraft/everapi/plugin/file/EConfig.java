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

import fr.evercraft.everapi.plugin.EPlugin;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public abstract class EConfig extends EFile {

    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     */
    public EConfig(final EPlugin plugin){
    	this(plugin, "config", true);
    }
    
    /**
     * Création d'un fichier de configuration
     * @param plugin Le plugin 
     * @param name Le nom du fichier de configuration
     */
    public EConfig(final EPlugin plugin, final String name){
    	this(plugin, name, true);
    }
    
    public EConfig(final EPlugin plugin, final String name, final boolean save){
    	super(plugin, name, save);
    	
    	reload();
    }
    
    /**
     * Charge la configuration
     */
    public void reload(){
    	this.loadFile();
    	this.loadDefault();
    	
    	this.save();
    }
    
    public void addDefault(final String paths, final Object value, final String comment){
    	CommentedConfigurationNode node = get(paths);    	
    	if (node.getValue() == null){
    		node.setComment(comment).setValue(value);
    	}
    }
    
    public void addDefault(final String paths, final Object value, final String... comments){
    	CommentedConfigurationNode node = get(paths);    	
    	if (node.getValue() == null){
    		node.setComment(String.join("\n", comments)).setValue(value);
    	}
    }
    
    public void addComment(final String paths, final String... comments){
    	CommentedConfigurationNode node = get(paths);    	
    	if (node.getValue() == null){
    		node.setComment(String.join("\n", comments));
    	}
    }

    public boolean isDebug() {
    	this.plugin.getLogger().info("Debug : " + this.get("debug").getBoolean(false));
		return this.get("debug").getBoolean(false);
	}

	public String getLanguage() {
		return this.get("language").getString(EMessage.ENGLISH);
	}
	
	/**
     * Définit tous les éléments par défaut
     */
    protected abstract void loadDefault();
}
