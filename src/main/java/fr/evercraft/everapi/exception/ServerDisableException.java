package fr.evercraft.everapi.exception;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EPlugin;

public class ServerDisableException extends Exception {
	private static final long serialVersionUID = 2L;
	private final EPlugin plugin;
	
	public ServerDisableException(final EPlugin plugin, final String message){
		super(message);
		this.plugin = plugin;
	}
	
	public void execute() {
		this.plugin.getLogger().warn(this.getMessage());
		if(this.plugin.getEverAPI() != null && this.plugin.getEverAPI().getMessages() != null) {
			if(this.plugin.getEverAPI().getManagerService() != null && this.plugin.getEverAPI().getManagerService().getMail().isPresent()) {
				this.plugin.getEverAPI().getManagerService().getMail().get().alert(
						EAMessages.MAIL_SERVER_DISABLE_OBJECT.get()
							.replaceAll("<server>", this.plugin.getEServer().getName())
							.replaceAll("<reason>", this.getMessage()), 
						EAMessages.MAIL_SERVER_DISABLE_MESSAGE.get()
							.replaceAll("<server>", this.plugin.getEServer().getName())
							.replaceAll("<reason>", this.getMessage()));
			}
			this.plugin.getEverAPI().disable();
			this.plugin.getGame().getServer().shutdown(EAMessages.SERVER_ERROR.getText());
		} else {
			this.plugin.getGame().getServer().shutdown();
		}
	}
}
