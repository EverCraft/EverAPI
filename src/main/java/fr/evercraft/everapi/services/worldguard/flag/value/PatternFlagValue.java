package fr.evercraft.everapi.services.worldguard.flag.value;

import org.spongepowered.api.entity.living.player.Player;

public interface PatternFlagValue<T1> {
	public boolean contains(T1 entity);
	public boolean contains(T1 entity, Player player);
}
