package fr.evercraft.everapi.services.bossbar;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.BossBarService;
import fr.evercraft.everapi.services.PriorityService;
import fr.evercraft.everapi.services.bossbar.event.EAddBossBarEvent;
import fr.evercraft.everapi.services.bossbar.event.ERemoveBossBarEvent;
import fr.evercraft.everapi.services.bossbar.event.EReplaceBossBarEvent;

public class EBossBarService implements BossBarService {	
	private final EverAPI plugin;
		
	private final ConcurrentMap<UUID, EBossBar> players;
	
	public EBossBarService(final EverAPI plugin){
		this.plugin = plugin;
		
		this.players = new ConcurrentHashMap<UUID, EBossBar>();
	}
	
	public void reload() {
		Set<Entry<UUID, EBossBar>> bossbars = this.players.entrySet();
		this.players.clear();
		
		for(Entry<UUID, EBossBar> bossbar : bossbars) {
			Optional<EPlayer> player = this.plugin.getEServer().getEPlayer(bossbar.getKey());
			if(player.isPresent()) {
				bossbar.getValue().getServerBossBar().removePlayer(player.get());
				this.plugin.getGame().getEventManager().post(new ERemoveBossBarEvent(player.get(), bossbar.getValue(), Cause.source(this.plugin).build()));
			}
		}
	}
	
	
	
	@Override
	public boolean add(EPlayer player, String identifier, ServerBossBar bossbar) {
		return this.add(player, identifier, this.getPriority(identifier), bossbar);
	}
	
	@Override
	public boolean add(EPlayer player, String identifier, int priority, ServerBossBar bossbar) {
		EBossBar bossbar_player = this.players.get(player.getUniqueId());
		// Vérifie la priorité
		if(bossbar_player == null) {
			// Ajoute
			bossbar_player = new EBossBar(identifier, bossbar);
			this.players.put(player.getUniqueId(), bossbar_player);
			bossbar.addPlayer(player.get());
			this.plugin.getGame().getEventManager().post(new EAddBossBarEvent(player, bossbar_player, Cause.source(this.plugin).build()));
			return true;
		} else if (this.getPriority(bossbar_player.getIdentifier()) <= priority && !bossbar_player.getServerBossBar().equals(bossbar)) {
			// Supprime
			bossbar_player.getServerBossBar().removePlayer(player.get());
			
			// Ajoute
			EBossBar new_bossbar_player = new EBossBar(identifier, bossbar);
			this.players.put(player.getUniqueId(), new_bossbar_player);
			bossbar.addPlayer(player.get());
			this.plugin.getGame().getEventManager().post(new EReplaceBossBarEvent(player, bossbar_player, new_bossbar_player, Cause.source(this.plugin).build()));
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(EPlayer player, String identifier) {
		EBossBar bossbar = this.players.get(player.getUniqueId());
		if(bossbar != null && bossbar.getIdentifier().equalsIgnoreCase(identifier)) {
			// Supprime
			bossbar.getServerBossBar().removePlayer(player.get());
			this.players.remove(player.getUniqueId());
			this.plugin.getGame().getEventManager().post(new ERemoveBossBarEvent(player, bossbar, Cause.source(this.plugin).build()));
			return true;
		}
		return false;
	}
	
	private int getPriority(String identifier) {
		if(this.plugin.getManagerService().getPriority().isPresent()) {
			return this.plugin.getManagerService().getPriority().get().getBossBar(identifier);
		}
		return PriorityService.DEFAULT;
	}

	@Override
	public Optional<ServerBossBar> get(EPlayer player, String identifier) {
		EBossBar bossbar = this.players.get(player.getUniqueId());
		if(bossbar != null && bossbar.getIdentifier().equalsIgnoreCase(identifier)) {
			return Optional.of(bossbar.getServerBossBar());
		}
		return Optional.empty();
	}
}
