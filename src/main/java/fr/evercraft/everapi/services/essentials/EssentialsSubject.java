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
	public Optional<Mail> getMail(int id);
	public boolean hasMail();
	public boolean addMail(String to, String message);
	public Optional<Mail> removeMail(int id);
	public boolean clearMails();
	public Optional<Mail> readMail(int id);
	
	/*
	 * Teleport
	 */
	public boolean addTeleportAsk(UUID uuid, long delay);
	public boolean addTeleportAskHere(UUID uuid, long delay);
	public boolean removeTeleportAsk(UUID uuid);
	public Map<UUID, TeleportRequest> getAllTeleportsAsk();
	public Optional<TeleportRequest> getTeleportAsk(UUID identifier);
	
	public boolean hasTeleport();
	public boolean runTeleport();
	public boolean cancelTeleport();
	public boolean setTeleport(Runnable runnable);
	public boolean setTeleport(long delay, Runnable runnable);
	public Optional<Long> getTeleport();
}
