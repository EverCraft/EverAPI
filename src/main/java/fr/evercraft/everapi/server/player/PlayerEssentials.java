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
package fr.evercraft.everapi.server.player;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.essentials.EssentialsSubject;
import fr.evercraft.everapi.services.essentials.Mail;

public class PlayerEssentials extends PlayerAccount implements EssentialsSubject {
	
	private EssentialsSubject subject;

	public PlayerEssentials(final EverAPI plugin, final Player player) {
		super(plugin, player);
	}

	private boolean isPresent() {
		if(this.subject == null && this.plugin.getManagerService().getEssentials().isPresent()) {
			this.subject = this.plugin.getManagerService().getEssentials().get().get(this.player.getUniqueId());
		}
		return this.subject != null;
	}

	@Override
	public boolean isVanish() {
		if(this.isPresent()) {
			return this.subject.isVanish();
		}
		return false;
	}

	@Override
	public boolean setVanish(final boolean vanish) {
		if(this.isPresent()) {
			return this.subject.setVanish(vanish);
		}
		return false;
	}
	
	public boolean canSeePlayer(EPlayer onlinePlayer) {
		if(this.isPresent()) {
			return !onlinePlayer.isVanish() || this.player.hasPermission(this.plugin.getManagerService().getEssentials().get().getPermissionVanishSee());
		}
		return !onlinePlayer.isVanish();
	}

	@Override
	public boolean isMute() {
		if(this.isPresent()) {
			return this.subject.isMute();
		}
		return false;
	}

	@Override
	public boolean setMute(final boolean mute) {
		if(this.isPresent()) {
			return this.subject.setMute(mute);
		}
		return false;
	}
	
	@Override
	public boolean setMute(final long time) {
		if(this.isPresent()) {
			return this.subject.setMute(time);
		}
		return false;
	}
	
	@Override
	public boolean isBan() {
		if(this.isPresent()) {
			return this.subject.isBan();
		}
		return false;
	}

	@Override
	public boolean setBan(final boolean mute) {
		if(this.isPresent()) {
			return this.subject.setBan(mute);
		}
		return false;
	}
	
	@Override
	public boolean setBan(final long time) {
		if(this.isPresent()) {
			return this.subject.setBan(time);
		}
		return false;
	}

	@Override
	public boolean isAFK() {
		if(this.isPresent()) {
			return this.subject.isAFK();
		}
		return false;
	}

	@Override
	public boolean setAFK(final boolean afk) {
		if(this.isPresent()) {
			return this.subject.setAFK(afk);
		}
		return false;
	}
	
	@Override
	public boolean isGod() {
		if(this.isPresent()) {
			return this.subject.isGod();
		}
		return false;
	}

	@Override
	public boolean setGod(final boolean god) {
		if(this.isPresent()) {
			return this.subject.setGod(god);
		}
		return false;
	}

	@Override
	public Map<String, Transform<World>> getHomes() {
		if(this.isPresent()) {
			return this.subject.getHomes();
		}
		return ImmutableMap.of();
	}
	
	@Override
	public boolean hasHome(final String identifier) {
		if(this.isPresent()) {
			return this.subject.hasHome(identifier);
		}
		return false;
	}
	
	@Override
	public Optional<Transform<World>> getHome(final String identifier) {
		if(this.isPresent()) {
			return this.subject.getHome(identifier);
		}
		return Optional.empty();
	}

	public boolean addHome(final String identifier) {
		return this.addHome(identifier, this.getTransform());
	}
	
	@Override
	public boolean addHome(final String identifier, final Transform<World> location) {
		if(this.isPresent()) {
			return this.subject.addHome(identifier, location);
		}
		return false;
	}

	@Override
	public boolean removeHome(final String identifier) {
		if(this.isPresent()) {
			return this.subject.removeHome(identifier);
		}
		return false;
	}

	@Override
	public boolean clearHome() {
		if(this.isPresent()) {
			return this.subject.clearHome();
		}
		return false;
	}

	@Override
	public Optional<Transform<World>> getBack() {
		if(this.isPresent()) {
			return this.subject.getBack();
		}
		return Optional.empty();
	}

	public boolean setBack() {
		return this.setBack(this.getTransform());
	}
	
	@Override
	public boolean setBack(final Transform<World> location) {
		if(this.isPresent()) {
			return this.subject.setBack(location);
		}
		return false;
	}

	@Override
	public boolean clearBack() {
		if(this.isPresent()) {
			return this.subject.clearBack();
		}
		return false;
	}

	@Override
	public Set<UUID> getIgnores() {
		if(this.isPresent()) {
			return this.subject.getIgnores();
		}
		return ImmutableSet.of();
	}
	
	public boolean ignore(UUID uuid) {
		if(this.isPresent()) {
			return this.subject.getIgnores().contains(uuid);
		}
		return false;
	}

	@Override
	public boolean addIgnore(final UUID uuid) {
		if(this.isPresent()) {
			return this.subject.addIgnore(uuid);
		}
		return false;
	}

	@Override
	public boolean removeIgnore(final UUID uuid) {
		if(this.isPresent()) {
			return this.subject.removeIgnore(uuid);
		}
		return false;
	}

	@Override
	public boolean clearIgnores() {
		if(this.isPresent()) {
			return this.subject.clearIgnores();
		}
		return false;
	}
	
	/*
	 * Mails
	 */

	@Override
	public Set<Mail> getMails() {
		if(this.isPresent()) {
			return this.subject.getMails();
		}
		return ImmutableSet.of();
	}

	@Override
	public boolean hasMail() {
		if(this.isPresent()) {
			return this.subject.hasMail();
		}
		return false;
	}

	@Override
	public boolean sendMail(String to, String message) {
		if(this.isPresent()) {
			return this.subject.sendMail(to, message);
		}
		return false;
	}
	
	@Override
	public boolean removeMail(int id) {
		if(this.isPresent()) {
			return this.subject.removeMail(id);
		}
		return false;
	}

	@Override
	public boolean clearMails() {
		if(this.isPresent()) {
			return this.subject.clearMails();
		}
		return false;
	}
}
