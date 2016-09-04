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
package fr.evercraft.everapi.services.essentials;

import java.net.InetAddress;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.World;

public interface SubjectUserEssentials extends SubjectEssentials {
	
	/*
	 * Vanish
	 */
	public boolean isVanish();
	public boolean setVanish(boolean vanish);
	
	/*
	 * AFK
	 */
	public boolean isAfk();
	public boolean setAfk(boolean afk);
	
	public boolean isAfkAutoFake();
	public boolean setAfkAutoFake(boolean afk);
	public boolean isAfkKickFake();
	public boolean setAfkKickFake(boolean afk);
	
	public void updateLastActivated();
	public long getLastActivated();
	
	/*
	 * God
	 */
	public boolean isGod();
	public boolean setGod(boolean god);
	
	/*
	 * Toggle
	 */
	public boolean isToggle();
	public boolean setToggle(final boolean toggle);
	
	/*
	 * Toggle
	 */
	public boolean isFreeze();
	public boolean setFreeze(final boolean freeze);
	
	/*
	 * Total time played
	 */
	public long getTotalTimePlayed();
	
	public boolean startTotalTimePlayed();
	public boolean stopTotalTimePlayed();
	
	/*
	 * Last ip
	 */
	public Optional<InetAddress> getLastIp();
	public boolean setLastIp(InetAddress address);
	
	/*
	 *  Homes
	 */
	public Map<String, Transform<World>> getHomes();
	public boolean hasHome(String identifier);
	public Optional<Transform<World>> getHome(String identifier);
	public boolean addHome(String identifier, Transform<World> location);
	public boolean moveHome(String identifier, Transform<World> location);
	public boolean removeHome(String identifier);
	
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
	public boolean ignore(User user);
	public boolean ignore(UUID uuid);
	public boolean addIgnore(UUID uuid);
	public boolean removeIgnore(UUID uuid);
	
	/*
	 * Mails
	 */
	public Set<Mail> getMails();
	public Optional<Mail> getMail(int id);
	public boolean hasMail();
	public boolean addMail(CommandSource to, String message);
	public boolean removeMail(Mail mail);
	public boolean clearMails();
	public boolean readMail(Mail mail);
	
	/*
	 * Teleport
	 */
	public boolean addTeleportAsk(UUID uuid, long delay);
	public boolean addTeleportAskHere(UUID uuid, long delay, Transform<World> location);
	public boolean removeTeleportAsk(UUID uuid);
	public Map<UUID, TeleportRequest> getAllTeleportsAsk();
	public Optional<TeleportRequest> getTeleportAsk(UUID identifier);
	
	public boolean hasTeleportDelay();
	public Optional<TeleportDelay> getTeleportDelay();
	public boolean setTeleport(Runnable runnable, boolean canMove);
	public boolean setTeleport(long delay, Runnable runnable, boolean canMove);
	public boolean runTeleportDelay();
	public boolean cancelTeleportDelay();
}
