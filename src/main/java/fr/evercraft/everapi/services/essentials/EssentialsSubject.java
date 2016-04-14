package fr.evercraft.everapi.services.essentials;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public interface EssentialsSubject {
	/*
	 * Vanish
	 */
	public boolean isVanish();
	public boolean setVanish(boolean vanish);
	
	/*
	 * Mute
	 */
	public boolean isMute();
	public boolean setMute(boolean mute);
	public boolean setMute(long time);
	
	/*
	 * Ban
	 */
	public boolean isBan();
	public boolean setBan(boolean ban);
	public boolean setBan(long time);
	
	/*
	 * AFK
	 */
	public boolean isAFK();
	public boolean setAFK(boolean afk);
	
	/*
	 * God
	 */
	public boolean isGod();
	public boolean setGod(boolean god);
	
	/*
	 *  Homes
	 */
	public Map<String, Transform<World>> getHomes();
	public boolean hasHome(String identifier);
	public Optional<Transform<World>> getHome(String identifier);
	public boolean addHome(String identifier, Transform<World> location);
	public boolean removeHome(String identifier);
	public boolean clearHome();
	
	/*
	 *  Back
	 */
	public Optional<Transform<World>> getBack();
	public boolean setBack(Transform<World> location);
	public boolean clearBack();
	
	/*
	 * Ignores
	 */
	public Set<UUID> getIgnores();
	public boolean addIgnore(UUID uuid);
	public boolean removeIgnore(UUID uuid);
	public boolean clearIgnores();
	
	/*
	 * Mails
	 */
	public Set<Mail> getMails();
	public boolean hasMail();
	public boolean sendMail(String to, String message);
	public boolean removeMail(int id);
	public boolean clearMails();
	
	
}
