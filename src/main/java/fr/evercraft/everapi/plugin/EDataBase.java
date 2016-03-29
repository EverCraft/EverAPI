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
package fr.evercraft.everapi.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.spongepowered.api.service.sql.SqlService;

import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.java.Chronometer;

public abstract class EDataBase<T extends EPlugin> {
	private static final int MAX_TRY = 10;

	protected final T plugin;
	
	private SqlService sql;

	private boolean force;
	
	private boolean enable;
	private String url;
	private String prefix;
	
	public EDataBase(final T plugin) {
		this(plugin, false, "");
	}

	public EDataBase(final T plugin, boolean force) {
		this(plugin, force, plugin.getName());
	}
	
	public EDataBase(final T plugin, boolean force, String prefix) {
		this.plugin = plugin;
		this.force = force;
		
		reload();
	}
	
	public void reload() {
		this.plugin.getLogger().debug("SQL : PreInitialization...");
		
		// SQLService
		if(this.plugin.getGame().getServiceManager().provide(SqlService.class).isPresent()) {
			this.sql = this.plugin.getGame().getServiceManager().provide(SqlService.class).get();
			
			// Config
			this.enable = this.plugin.getConfigs().get("SQL.enable").getBoolean(false);
			this.prefix = this.plugin.getConfigs().get("SQL.prefix").getString(this.plugin.getName().toLowerCase() + "_");
			
			if(this.enable) {			
				this.url = this.plugin.getConfigs().get("SQL.url").getString("");
				
				if(this.url.isEmpty()) {
					this.enable = false;
				} else {
					this.enable = testConnection();
				}
			} else if(this.force) {
				this.plugin.getLogger().debug("SQL : Default");
				this.url = "jdbc:h2:" + this.plugin.getPath().toAbsolutePath() + "/data";
				this.enable = testConnection();
			}
			
			// Resultat
			if(this.enable) {
				this.plugin.getLogger().info("SQL : Load complete");
			} else {
				this.plugin.getLogger().debug("SQL : Error loading");
			}
		} else {
			this.enable = false;
			this.plugin.getLogger().debug("No SqlService");
		}
	}
	
	public Connection getConnection() throws ServerDisableException {
		Connection connection = null;
		int cpt = 1;
		while(cpt <= MAX_TRY && connection == null) {
			Chronometer chronometer = new Chronometer();
			try {
				connection = this.sql.getDataSource(this.url).getConnection();
				this.plugin.getLogger().debug("SQL : Connection in " + chronometer.getMilliseconds() + " ms");
			} catch (SQLException e) {
				this.plugin.getLogger().warn("SQL : Error in connection " + cpt + " : " + e.getMessage());
			}
			cpt++;
		}
		
		if(connection == null) {
			this.enable = false;
			throw new ServerDisableException(this.plugin, "Unable to connect to the database : (plugin='" + this.plugin.getName() + ")");
		}
		
		return connection;
    }
	
    public boolean testConnection() {
    	boolean valide = false;
    	try {
    		valide = init();
		} catch (ServerDisableException e) {
			this.plugin.getLogger().warn("SQL : Error in initialize : " + e.getMessage());
		}
    	return valide;
    }
    
    public boolean initTable(final String table, final String query) throws ServerDisableException {
    	Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query.replaceAll("<table>", table));
			preparedStatement.execute();
			this.plugin.getLogger().debug("SQL : Initialize the table : '" + table + "'");
			return true;
		} catch (SQLException e) {
			this.plugin.getLogger().warn("SQL : Error in initialize the table '" + table + "' : " + e.getMessage());
		} finally {
			try {if (connection != null) connection.close();} catch (SQLException e) {}
	    }
		return false;
    }
    
    public String getPrefix() {
    	return this.prefix;
    }
    
    public boolean isEnable() {
        return this.enable;
    }
    
    public abstract boolean init() throws ServerDisableException;
}
