package fr.evercraft.everapi.sponge;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.plugin.EPlugin;

public class UtilsCause {

	public static Cause command(final EPlugin plugin, final CommandSource source) {
		return Cause.builder().named(plugin.getName(), plugin).suggestNamed(source.getName(), source).build();
	}
}
