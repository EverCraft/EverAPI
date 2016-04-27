package fr.evercraft.everapi.servives.scoreboard;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;

public interface ScoreBoardService {

	boolean addObjective(Player player, DisplaySlot display, Objective objective);

	boolean addObjective(Player player, int priority, DisplaySlot display, Objective objective);

}
