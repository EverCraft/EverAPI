package fr.evercraft.everapi.services.priority;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;

public interface PriorityService {
	public final static int DEFAULT = 0;
	
	public int getActionBar(String identifier);
	public int getTitle(String identifier);
	public int getScoreBoard(DisplaySlot type, String identifier);
}
