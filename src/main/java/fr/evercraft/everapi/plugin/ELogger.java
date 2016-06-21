/*
 * EverAPI
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

import org.slf4j.Logger;


public class ELogger {
	
	private final Logger logger;
	
	private boolean debug;
	
	public ELogger(final Logger logger) {
		this.logger = logger;
		this.debug = true;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public boolean isDebug() {
		return this.debug;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public void info(String msg) {
		this.logger.info(msg);
	}
	
	public void error(String msg) {
		this.logger.error(msg);
	}
	
	public void warn(String msg) {
		this.logger.warn(msg);
	}
	
	public void debug(String msg) {
		if(this.debug) {
			this.logger.info(msg);
		}
	}
	
}
