package fr.evercraft.everapi.services.signs;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.server.player.EPlayer;

public interface SignSubject {
	
	public static final TextColor COLOR_ENABLE = TextColors.BLUE;
	public static final TextColor COLOR_DISABLE = TextColors.RED;
	
	public String getName();
	
	public boolean create(EPlayer player, Sign sign);
	public boolean useEnable(EPlayer player, Sign sign);
	public boolean useDisable(EPlayer player, Sign sign);
	public boolean remove(EPlayer player, Sign sign);
	
	public boolean valide(Sign sign);
}
