package fr.evercraft.everapi.command.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.server.player.EPlayer;

public class EATest extends ESubCommand<EverAPI> {
	public EATest(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "reload");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.TEST.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("");
	}
	
	public List<String> subTabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return new ArrayList<String>();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName())
					.onClick(TextActions.suggestCommand("/" + this.getName()))
					.color(TextColors.RED)
					.build();
	}
	
	public boolean subExecute(final CommandSource source, final List<String> args) {
		if(args.size() == 0) {
			return commandTest(source);
		}
		source.sendMessage(this.help(source));
		return false;
	}

	private boolean commandTest(final CommandSource source) {
		EPlayer player = ((EPlayer) source);
		Optional<List<PotionEffect>> potions = player.get(Keys.POTION_EFFECTS);
		if(potions.isPresent()) {
			player.sendMessage("Present");
			player.sendMessage("Size : " + potions.get().size());
		} else {
			player.sendMessage("No present");
		}
		return true;
	}
}
