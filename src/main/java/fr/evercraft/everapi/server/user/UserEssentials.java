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
package fr.evercraft.everapi.server.user;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.World;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.essentials.SubjectUserEssentials;
import fr.evercraft.everapi.services.essentials.Mail;
import fr.evercraft.everapi.services.essentials.TeleportDelay;
import fr.evercraft.everapi.services.essentials.TeleportRequest;

public class UserEssentials extends UserCooldown implements SubjectUserEssentials {
	
	private SubjectUserEssentials subject;

	public UserEssentials(final EverAPI plugin, final User user) {
		super(plugin, user);
	}

	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getEssentials().isPresent()) {
			this.subject = this.plugin.getManagerService().getEssentials().get().get(this.user.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}
	
	/*
	 * ReplyTo
	 */
	
	@Override
	public Optional<String> getReplyTo() {
		if (this.isPresent()) {
			return this.subject.getReplyTo();
		}
		return Optional.empty();
	}
	
	@Override
	public boolean setReplyTo(@Nullable String identifier) {
		if (this.isPresent()) {
			return this.subject.setReplyTo(identifier);
		}
		return false;
	}
	
	/*
	 * Vanish
	 */

	@Override
	public boolean isVanish() {
		if (this.isPresent()) {
			return this.subject.isVanish();
		}
		return false;
	}

	@Override
	public boolean setVanish(final boolean vanish) {
		if (this.isPresent()) {
			return this.subject.setVanish(vanish);
		}
		return false;
	}
	
	public boolean canSeePlayer(EPlayer onlinePlayer) {
		if (this.isPresent()) {
			return !onlinePlayer.isVanish() || this.user.hasPermission(this.plugin.getManagerService().getEssentials().get().getPermissionVanishSee());
		}
		return !onlinePlayer.isVanish();
	}
	
	/*
	 * AFK
	 */

	@Override
	public boolean isAfk() {
		if (this.isPresent()) {
			return this.subject.isAfk();
		}
		return false;
	}

	@Override
	public boolean setAfk(final boolean afk) {
		if (this.isPresent()) {
			return this.subject.setAfk(afk);
		}
		return false;
	}
	
	@Override
	public boolean isAfkAutoFake() {
		if (this.isPresent()) {
			return this.subject.isAfkAutoFake();
		}
		return false;
	}

	@Override
	public boolean setAfkAutoFake(final boolean afk) {
		if (this.isPresent()) {
			return this.subject.setAfkAutoFake(afk);
		}
		return false;
	}
	
	@Override
	public boolean isAfkKickFake() {
		if (this.isPresent()) {
			return this.subject.isAfkKickFake();
		}
		return false;
	}

	@Override
	public boolean setAfkKickFake(final boolean afk) {
		if (this.isPresent()) {
			return this.subject.setAfkKickFake(afk);
		}
		return false;
	}
	
	@Override
	public void updateLastActivated() {
		if (this.isPresent()) {
			this.subject.updateLastActivated();
		}
	}

	@Override
	public long getLastActivated() {
		if (this.isPresent()) {
			return this.subject.getLastActivated();
		}
		return 0;
	}
	
	/*
	 * GOD
	 */
	
	@Override
	public boolean isGod() {
		if (this.isPresent()) {
			return this.subject.isGod();
		}
		return false;
	}

	@Override
	public boolean setGod(final boolean god) {
		if (this.isPresent()) {
			return this.subject.setGod(god);
		}
		return false;
	}
	
	/*
	 * TOGGLE
	 */
	
	@Override
	public boolean isToggle() {
		if (this.isPresent()) {
			return this.subject.isToggle();
		}
		return false;
	}

	@Override
	public boolean setToggle(final boolean toggle) {
		if (this.isPresent()) {
			return this.subject.setToggle(toggle);
		}
		return false;
	}
	
	/*
	 * TOGGLE
	 */
	
	@Override
	public boolean isFreeze() {
		if (this.isPresent()) {
			return this.subject.isFreeze();
		}
		return false;
	}

	@Override
	public boolean setFreeze(boolean freeze) {
		if (this.isPresent()) {
			return this.subject.setFreeze(freeze);
		}
		return false;
	}
	
	/*
	 * Total time played
	 */
	
	@Override
	public long getTotalTimePlayed() {
		if (this.isPresent()) {
			return this.subject.getTotalTimePlayed();
		}
		return 0;
	}

	@Override
	public boolean startTotalTimePlayed() {
		if (this.isPresent()) {
			return this.subject.startTotalTimePlayed();
		}
		return false;
	}
	
	@Override
	public boolean stopTotalTimePlayed() {
		if (this.isPresent()) {
			return this.subject.stopTotalTimePlayed();
		}
		return false;
	}

	/*
	 * HOME
	 */
	
	@Override
	public Map<String, Transform<World>> getHomes() {
		if (this.isPresent()) {
			return this.subject.getHomes();
		}
		return ImmutableMap.of();
	}
	
	@Override
	public boolean hasHome(final String identifier) {
		if (this.isPresent()) {
			return this.subject.hasHome(identifier);
		}
		return false;
	}
	
	@Override
	public Optional<Transform<World>> getHome(final String identifier) {
		if (this.isPresent()) {
			return this.subject.getHome(identifier);
		}
		return Optional.empty();
	}
	
	@Override
	public boolean addHome(final String identifier, final Transform<World> location) {
		if (this.isPresent()) {
			return this.subject.addHome(identifier, location);
		}
		return false;
	}
	
	@Override
	public boolean moveHome(final String identifier, final Transform<World> location) {
		if (this.isPresent()) {
			return this.subject.moveHome(identifier, location);
		}
		return false;
	}

	@Override
	public boolean removeHome(final String identifier) {
		if (this.isPresent()) {
			return this.subject.removeHome(identifier);
		}
		return false;
	}

	/*
	 * BACK
	 */
	
	@Override
	public Optional<Transform<World>> getBack() {
		if (this.isPresent()) {
			return this.subject.getBack();
		}
		return Optional.empty();
	}
	
	@Override
	public boolean setBack(final Transform<World> location) {
		if (this.isPresent()) {
			return this.subject.setBack(location);
		}
		return false;
	}

	@Override
	public boolean clearBack() {
		if (this.isPresent()) {
			return this.subject.clearBack();
		}
		return false;
	}
	
	/*
	 * Ignore
	 */

	@Override
	public Set<UUID> getIgnores() {
		if (this.isPresent()) {
			return this.subject.getIgnores();
		}
		return ImmutableSet.of();
	}
	
	@Override
	public boolean ignore(UUID uuid) {
		if (this.isPresent()) {
			return this.subject.ignore(uuid);
		}
		return false;
	}

	@Override
	public boolean addIgnore(final UUID uuid) {
		if (this.isPresent()) {
			return this.subject.addIgnore(uuid);
		}
		return false;
	}

	@Override
	public boolean removeIgnore(final UUID uuid) {
		if (this.isPresent()) {
			return this.subject.removeIgnore(uuid);
		}
		return false;
	}
	
	/*
	 * Mails
	 */

	@Override
	public Set<Mail> getMails() {
		if (this.isPresent()) {
			return this.subject.getMails();
		}
		return ImmutableSet.of();
	}

	@Override
	public boolean hasMail() {
		if (this.isPresent()) {
			return this.subject.hasMail();
		}
		return false;
	}

	@Override
	public boolean addMail(CommandSource to, String message) {
		if (this.isPresent()) {
			return this.subject.addMail(to, message);
		}
		return false;
	}
	
	@Override
	public boolean removeMail(Mail mail) {
		if (this.isPresent()) {
			return this.subject.removeMail(mail);
		}
		return false;
	}

	@Override
	public boolean clearMails() {
		if (this.isPresent()) {
			return this.subject.clearMails();
		}
		return false;
	}

	@Override
	public Optional<Mail> getMail(int id) {
		if (this.isPresent()) {
			return this.subject.getMail(id);
		}
		return Optional.empty();
	}

	@Override
	public boolean readMail(Mail mail) {
		if (this.isPresent()) {
			return this.subject.readMail(mail);
		}
		return false;
	}
	
	/*
	 * Spawn
	 */
	
	public Transform<World> getSpawn() {
		return this.plugin.getEServer().getSpawn(this);
	}
	
	/*
	 * Teleport ASK
	 */

	@Override
	public boolean addTeleportAsk(UUID uuid, long time) {
		if (this.isPresent()) {
			return this.subject.addTeleportAsk(uuid, time);
		}
		return false;
	}
	
	@Override
	public boolean addTeleportAskHere(UUID uuid, long time, @Nullable Transform<World> location) {
		if (this.isPresent()) {
			return this.subject.addTeleportAskHere(uuid, time, location);
		}
		return false;
	}

	@Override
	public boolean removeTeleportAsk(UUID uuid) {
		if (this.isPresent()) {
			return this.subject.removeTeleportAsk(uuid);
		}
		return false;
	}
	
	@Override
	public Map<UUID, TeleportRequest> getAllTeleportsAsk() {
		if (this.isPresent()) {
			return this.subject.getAllTeleportsAsk();
		}
		return ImmutableMap.of();
	}
	
	@Override
	public Optional<TeleportRequest> getTeleportAsk(UUID uuid) {
		if (this.isPresent()) {
			return this.subject.getTeleportAsk(uuid);
		}
		return Optional.empty();
	}
	
	/*
	 * Teleport
	 */
	
	@Override
	public boolean hasTeleportDelay() {
		if (this.isPresent()) {
			return this.subject.hasTeleportDelay();
		}
		return false;
	}

	@Override
	public Optional<TeleportDelay> getTeleportDelay() {
		if (this.isPresent()) {
			return this.subject.getTeleportDelay();
		}
		return Optional.empty();
	}

	@Override
	public boolean setTeleport(Runnable runnable, boolean canMove) {
		if (this.isPresent()) {
			return this.subject.setTeleport(runnable, canMove);
		}
		return false;
	}

	@Override
	public boolean setTeleport(long delay, Runnable runnable, boolean canMove) {
		if (this.isPresent()) {
			return this.subject.setTeleport(delay, runnable, canMove);
		}
		return false;
	}
	
	@Override
	public boolean runTeleportDelay() {
		if (this.isPresent()) {
			return this.subject.runTeleportDelay();
		}
		return false;
	}
	
	@Override
	public boolean cancelTeleportDelay() {
		if (this.isPresent()) {
			return this.subject.cancelTeleportDelay();
		}
		return false;
	}
}
