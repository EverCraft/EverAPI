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

import java.math.BigDecimal;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;

public class UserAccount extends UserKeys {
	
	private UniqueAccount subject;
	
	/**
	 * Constructeur d'un EPlayer
	 * @param plugin EverAPI
	 * @param player Le joueur
	 */
	public UserAccount(final EverAPI plugin, final User user){
		super(plugin, user);
	}
	
	private boolean isPresent() {
		if (this.subject == null && this.plugin.getManagerService().getEconomy().isPresent()) {
			this.subject = this.plugin.getManagerService().getEconomy().get().getOrCreateAccount(this.user.getUniqueId()).orElse(null);
		}
		return this.subject != null;
	}
	
	/**
	 * Retourne l'argent du joueur
	 * @return La monnaie
	 */
	public BigDecimal getBalance() {
		if (this.isPresent()) {
			return this.subject.getBalance(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency());
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * Retourne l'argent du joueur
	 * @return La monnaie
	 */
	public BigDecimal getBalanceRound() {
		if (this.isPresent()) {
			return this.getBalance().setScale(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency().getDefaultFractionDigits(), BigDecimal.ROUND_HALF_UP);
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * Retourne True si il le joueur a assez d'argent
	 * @param balance Le nombre d'argent
	 * @return True si il assez d'argent
	 */
	public boolean hasBalance(final BigDecimal amount) {
		if (this.isPresent()) {
			return this.subject.getBalance(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency()).compareTo(amount) >= 0;
		}
		return false;
	}

	/**
	 * Retourne True si l'argent a bien été déposé sur le compte du joueur
	 * @param balance Le nombre d'argent a déposé sur le compte du joueur
	 * @return  True si l'argent a bien été déposé sur le compte du joueur
	 */
	public boolean deposit(final BigDecimal amount, final Cause cause) {
		if (this.isPresent()) {
			return this.subject.deposit(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency(), amount, cause).getResult().equals(ResultType.SUCCESS);
		}
		return false;
	}
	
	/**
	 * Retourne True si l'argent a bien été retiré du compte du joueur
	 * @param balance Le nombre d'argent a retiré du compte du joueur
	 * @return  True si l'argent a bien été retiré du compte du joueur
	 */
	public boolean withdraw(final BigDecimal amount, final Cause cause) {
		if (this.isPresent()) {
			return this.subject.withdraw(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency(), amount, cause).getResult().equals(ResultType.SUCCESS);
		}
		return false;
	}
	
	/**
	 * Sauvegarde l'argent du joueur dans la base de données
	 * @param balance L'argent du joueur
	 * @return True si l'argent du joueur a bien été sauvegardé dans la base de données
	 */
	public boolean setBalance(final BigDecimal amount, final Cause cause) {
		if (this.isPresent()) {
			return this.subject.setBalance(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency(), amount, cause).getResult().equals(ResultType.SUCCESS);
		}
		return false;
	}
	
	/**
	 * Transférer de l'argent sur un autre compte
	 * @param playerto Le compte qui va recevoir l'argent
	 * @param amount La quantité
	 * @param cause La cause
	 * @return True si l'argent a bien été transféré
	 */
	public boolean transfer(final EPlayer playerto, final BigDecimal amount, final Cause cause) {
		Optional<UniqueAccount> accountTo = playerto.getAccount();
		if (this.isPresent() && accountTo.isPresent()) {
			return this.subject.transfer(accountTo.get(), this.plugin.getManagerService().getEconomy().get().getDefaultCurrency(), amount, cause).getResult().equals(ResultType.SUCCESS);
		}
		return false;
	}
	
	/**
	 * Reset le compte
	 * @param cause La cause
	 * @return True si le compte a bien été reset
	 */
	public boolean resetBalance(final Cause cause) {
		Optional<UniqueAccount> account = getAccount();
		if (account.isPresent()) {
			return account.get().resetBalance(this.plugin.getManagerService().getEconomy().get().getDefaultCurrency(), cause).getResult().equals(ResultType.SUCCESS);
		}
		return false;
	}
	
	/**
	 * Retourne le compte du joueur
	 * @return Le compte du joueur
	 */
	public Optional<UniqueAccount> getAccount() {
		this.isPresent();
		return Optional.ofNullable(this.subject);
	}
}
