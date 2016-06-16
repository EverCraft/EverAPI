package fr.evercraft.everapi.services;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

import fr.evercraft.everapi.server.player.EPlayer;

public interface ScoreBoardService {

	public boolean addObjective(EPlayer player, DisplaySlot display, Objective objective);

	public boolean addObjective(EPlayer player, int priority, DisplaySlot display, Objective objective);

	public boolean removeObjective(EPlayer player, DisplaySlot display, Objective objective);

	public boolean removeObjective(EPlayer player, DisplaySlot display, String identifier);
	
}
