package fr.evercraft.everapi.services.pagination;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public interface CommandPagination {
	
	public String getName();
	
	public Text help(CommandSource source);
	
	public Text description(CommandSource source);
}
