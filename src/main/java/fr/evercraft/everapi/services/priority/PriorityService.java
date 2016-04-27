package fr.evercraft.everapi.services.priority;

import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;

public interface PriorityService {
	public final static int DEFAULT = 0;
	
	public int getActionBar(String id);
	public int getTitle(String id);
	public int getScoreBoard(DisplaySlot type, String id);
}
