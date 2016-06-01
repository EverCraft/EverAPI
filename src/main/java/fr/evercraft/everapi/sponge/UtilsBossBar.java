package fr.evercraft.everapi.sponge;

import java.util.Optional;

import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.boss.BossBarOverlays;

public class UtilsBossBar {
	
	public static Optional<BossBarColor> getColor(String color) {
		if(color.equalsIgnoreCase("BLUE")) {
			return Optional.of(BossBarColors.BLUE);
		} else if(color.equalsIgnoreCase("GREEN")) {
			return Optional.of(BossBarColors.GREEN);
		} else if(color.equalsIgnoreCase("PINK")) {
			return Optional.of(BossBarColors.PINK);
		} else if(color.equalsIgnoreCase("PURPLE")) {
			return Optional.of(BossBarColors.PURPLE);
		} else if(color.equalsIgnoreCase("RED")) {
			return Optional.of(BossBarColors.RED);
		} else if(color.equalsIgnoreCase("WHITE")) {
			return Optional.of(BossBarColors.WHITE);
		} else if(color.equalsIgnoreCase("YELLOW")) {
			return Optional.of(BossBarColors.YELLOW);
		} else {
			return Optional.empty();
		}
	}
	

	public static Optional<BossBarOverlay> getOverlay(String overlay) {
		if(overlay.equalsIgnoreCase("NOTCHED_6")) {
			return Optional.of(BossBarOverlays.NOTCHED_6);
		} else if(overlay.equalsIgnoreCase("NOTCHED_10")) {
			return Optional.of(BossBarOverlays.NOTCHED_10);
		} else if(overlay.equalsIgnoreCase("NOTCHED_12")) {
			return Optional.of(BossBarOverlays.NOTCHED_12);
		} else if(overlay.equalsIgnoreCase("NOTCHED_20")) {
			return Optional.of(BossBarOverlays.NOTCHED_20);
		} else if(overlay.equalsIgnoreCase("PROGRESS")) {
			return Optional.of(BossBarOverlays.PROGRESS);
		} else {
			return Optional.empty();
		}
	}
}