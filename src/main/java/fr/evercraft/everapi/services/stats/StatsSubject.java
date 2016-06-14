package fr.evercraft.everapi.services.stats;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.entity.damage.DamageType;

public interface StatsSubject {

	public int getDeath();
	public int getKill();
	public int getRatio();

	public int getDeathMonthly();
	public int getKillMonthly();
	public int getRatioMonthly();
	
	public boolean addDeath(Entity killer, DamageType damage, Long time);
}
