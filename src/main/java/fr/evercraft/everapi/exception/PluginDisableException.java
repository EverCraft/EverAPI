package fr.evercraft.everapi.exception;

public class PluginDisableException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PluginDisableException(final String message){
		super(message);
	}
}
