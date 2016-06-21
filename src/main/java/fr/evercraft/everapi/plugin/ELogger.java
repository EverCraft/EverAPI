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
