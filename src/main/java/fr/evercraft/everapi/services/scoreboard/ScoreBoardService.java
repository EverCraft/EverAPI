package fr.evercraft.everapi.services.scoreboard;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

public interface ScoreBoardService {

	public boolean addObjective(Player player, DisplaySlot display, Objective objective);

	public boolean addObjective(Player player, int priority, DisplaySlot display, Objective objective);

	public boolean removeObjective(Player player, DisplaySlot display, Objective objective);

	public boolean removeObjective(Player player, DisplaySlot display, String identifier);
	
}