package fr.evercraft.everapi.server.player;


import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.entity.living.player.Player;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.cooldown.CooldownSubject;

public class PlayerCooldown extends PlayerPermission implements CooldownSubject {
	
	private CooldownSubject subject;

	public PlayerCooldown(EverAPI plugin, Player player) {
		super(plugin, player);
	}

	private boolean isPresent() {
		if(this.subject == null && this.plugin.getManagerService().getCooldown().isPresent()) {
			this.subject = this.plugin.getManagerService().getCooldown().get().get(this.player.getUniqueId());
		}
		return this.subject != null;
	}

	@Override
	public Map<String, Long> getCooldown() {
		if(this.isPresent()) {
			return this.subject.getCooldown();
		}
		return new HashMap<String, Long>();
	}

	@Override
	public boolean addCooldown(String identifier) {
		if(this.isPresent()) {
			return this.subject.addCooldown(identifier);
		}
		return false;
	}

	@Override
	public boolean addCooldownScheduler(String identifier) {
		if(this.isPresent()) {
			return this.subject.addCooldownScheduler(identifier);
		}
		return false;
	}

	@Override
	public boolean removeCooldown(String identifier) {
		if(this.isPresent()) {
			return this.subject.removeCooldown(identifier);
		}
		return false;
	}

	@Override
	public long getCooldownTime(String identifier) {
		if(this.isPresent()) {
			return this.subject.getCooldownTime(identifier);
		}
		return 0;
	}
	
	
}
