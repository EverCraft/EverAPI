package fr.evercraft.everapi.services.essentials;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

public interface Mail {
	public int getID();
	public long getDateTime();
	
	public boolean isRead();
	public void setRead(boolean read);
	
	public String getTo();
	
	/**
	 * Peut prendre du temps : à faire en async
	 */
	public Optional<User> getToPlayer();
	
	public Text getText();
}
