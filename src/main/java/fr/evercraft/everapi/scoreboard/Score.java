package fr.evercraft.everapi.scoreboard;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.server.player.EPlayer;

public abstract class Score {
	
	protected final CopyOnWriteArrayList<IObjective> objectives;
	
	
	public Score() {
		this.objectives = new CopyOnWriteArrayList<IObjective>();
	}
	
	public void addListener(EPlugin plugin, IObjective objective) {
		if(this.objectives.isEmpty()) {
			plugin.getGame().getEventManager().registerListeners(plugin, this);
		}
		this.objectives.add(objective);
	}
	
	public void removeListener(EPlugin plugin, IObjective objective) {
		this.objectives.remove(objective);
		if(this.objectives.isEmpty()) {
			plugin.getGame().getEventManager().unregisterListeners(this);
		}
	}
	
	protected void update(TypeScores type) {
		for(IObjective objective : this.objectives) {
			objective.update(type);
		}
	}
	
	protected void update(UUID uniqueId, TypeScores type) {
		for(IObjective objective : this.objectives) {
			objective.update(type);
		}
	}
	
	public abstract Integer getValue(EPlayer player);
	
	public abstract boolean isUpdate();
}
