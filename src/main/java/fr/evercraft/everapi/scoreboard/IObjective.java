package fr.evercraft.everapi.scoreboard;

import java.util.UUID;

import fr.evercraft.everapi.scoreboard.TypeScores;

public interface IObjective  {
	public void update(TypeScores type);
	public void update(UUID uniqueId, TypeScores type);
}
